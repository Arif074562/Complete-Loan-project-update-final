import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CustomerService } from '../../core/services/customer.service';
import { LoanApplicationService } from '../../core/services/loan-application.service';
import { ProductService } from '../../core/services/product.service';
import { ServicingService } from '../../core/services/servicing.service';
import { AuthService } from '../../core/services/auth.service';
import { HttpClient } from '@angular/common/http';
import { ApiResponse, UserResponse } from '../../core/models/models';
import { NotificationService, AppNotification } from '../../core/services/notification.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardComponent implements OnInit {
  stats = { customers: 0, applications: 0, products: 0, accounts: 0 };
  recentApplications: any[] = [];
  loading = false;
  today = new Date();
  customerMap: Record<number, string> = {};
  myAccountBalance = 0;
  myAccountId: number | null = null;

  get isCustomer() { return this.auth.getCurrentUser()?.role === 'CUSTOMER'; }
  get isAdmin() { return this.auth.getCurrentUser()?.role === 'ADMIN'; }
  get paymentReminders(): AppNotification[] { return this.notif.getPaymentReminders(); }
  get upcomingPayment(): AppNotification | null {
    const sorted = this.paymentReminders
      .filter(r => r.dueDate && new Date(r.dueDate) >= new Date())
      .sort((a, b) => new Date(a.dueDate!).getTime() - new Date(b.dueDate!).getTime());
    return sorted[0] ?? null;
  }

  quickLinks = [
    { label: 'New Customer',     icon: 'fa-user-plus',            route: '/customers',         color: '#6366f1' },
    { label: 'Apply for Loan',   icon: 'fa-file-invoice',         route: '/loan-applications', color: '#0ea5e9' },
    { label: 'Manage Products',  icon: 'fa-box-open',             route: '/products',          color: '#10b981' },
    { label: 'View Collections', icon: 'fa-triangle-exclamation', route: '/collections',       color: '#f59e0b' },
  ];

  constructor(
    private customerSvc: CustomerService,
    private loanAppSvc: LoanApplicationService,
    private productSvc: ProductService,
    private servicingSvc: ServicingService,
    public auth: AuthService,
    private http: HttpClient,
    public notif: NotificationService
  ) {}

  ngOnInit() {
    this.loadDashboard();
  }

  loadDashboard() {
    this.loading = true;
    const customerCount$ = this.http.get<any>('http://localhost:8069/api/users/all').pipe(
      catchError(() => of(null))
    );
    forkJoin({
      usersResp:    customerCount$,
      applications: this.loanAppSvc.list(0, 100).pipe(catchError(() => of(null))),
      products:     this.productSvc.list(0, 1).pipe(catchError(() => of(null))),
      accounts:     this.servicingSvc.listAccounts(0, 100).pipe(catchError(() => of(null))),
      profiles:     this.customerSvc.list(0, 1000).pipe(catchError(() => of(null))),
    }).subscribe({
      next: r => {
        const allUsers = r.usersResp?.data ?? [];
        const allApps: any[] = r.applications?.data?.content ?? [];
        const allAccounts: any[] = r.accounts?.data?.content ?? [];

        // Build customer name map
        (r.profiles?.data?.content ?? []).forEach((c: any) => {
          const name = `${c.firstName || ''} ${c.lastName || ''}`.trim();
          this.customerMap[c.customerId] = name ? `${name} (#${c.customerId})` : `#${c.customerId}`;
        });
        allUsers.filter((u: any) => u.role === 'CUSTOMER').forEach((u: any) => {
          if (!this.customerMap[u.userId]) this.customerMap[u.userId] = `${u.name} (#${u.userId})`;
        });

        if (this.isCustomer) {
          const me = this.auth.getCurrentUser();
          const myEmail = me?.email?.toLowerCase();
          const profile = (r.profiles?.data?.content ?? []).find((c: any) => c.email?.toLowerCase() === myEmail);
          // Use profile customerId, or fall back to userId, or match by name
          const myCustomerId = profile?.customerId
            ?? allUsers.find((u: any) => u.email?.toLowerCase() === myEmail)?.userId
            ?? me?.userId;

          const myApps = allApps.filter(a => a.customerId === myCustomerId);
          this.recentApplications = myApps.slice(0, 5);
          this.stats.applications = myApps.length;

          const disbursedApp = myApps.find(a => a.status === 'DISBURSED');
          if (disbursedApp) {
            const myAccount = allAccounts.find(a => a.applicationId === disbursedApp.applicationId);
            this.myAccountBalance = myAccount?.outstandingBalance ?? 0;
            this.myAccountId = myAccount?.id ?? null;
          }

          this.stats.customers = 0;
          this.stats.products = r.products?.data?.totalElements ?? 0;
          this.stats.accounts = myApps.filter(a => a.status === 'DISBURSED').length;
        } else {
          this.stats.customers    = allUsers.filter((u: any) => u.role === 'CUSTOMER').length;
          this.stats.applications = r.applications?.data?.totalElements ?? 0;
          this.stats.products     = r.products?.data?.totalElements ?? 0;
          this.stats.accounts     = r.accounts?.data?.totalElements ?? 0;
          this.recentApplications = allApps.slice(0, 5);
        }
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }

  customerName(id: number): string { return this.customerMap[id] || `#${id}`; }

  getStatusClass(status: string): string {
    if (!status) return 'badge-secondary';
    const map: Record<string, string> = {
      APPROVED: 'badge-success',
      REJECTED: 'badge-danger',
      PENDING: 'badge-warning',
      UNDER_REVIEW: 'badge-info',
      DISBURSED: 'badge-secondary'
    };
    return map[status] ?? 'badge-secondary';
  }

  isOverdue(dueDate: string): boolean {
    return new Date(dueDate) < this.today;
  }
}
