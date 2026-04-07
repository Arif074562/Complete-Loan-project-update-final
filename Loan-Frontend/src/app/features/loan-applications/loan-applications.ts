import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { LoanApplicationService } from '../../core/services/loan-application.service';
import { LoanApplicationRequest, LoanApplicationResponse } from '../../core/models/models';
import { NotificationService } from '../../core/services/notification.service';
import { AuthService } from '../../core/services/auth.service';
import { CustomerService } from '../../core/services/customer.service';
import { ProductService } from '../../core/services/product.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-loan-applications',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './loan-applications.html',
  styleUrl: './loan-applications.css'
})
export class LoanApplicationsComponent implements OnInit {
  applications: LoanApplicationResponse[] = [];
  totalElements = 0; totalPages = 0; currentPage = 0; pageSize = 10;
  loading = false; showModal = false; editMode = false; saving = false;
  selectedId: number | null = null;
  error = ''; success = '';

  form: LoanApplicationRequest = this.emptyForm();
  statuses = ['PENDING', 'UNDER_REVIEW', 'APPROVED', 'REJECTED', 'DISBURSED'];

  // lookup maps for display
  customerMap: Record<number, string> = {};
  productMap: Record<number, string> = {};
  customerId: number | null = null;

  get isAdmin() { return this.auth.getCurrentUser()?.role === 'ADMIN'; }
  get isCustomer() { return this.auth.getCurrentUser()?.role === 'CUSTOMER'; }

  constructor(
    private svc: LoanApplicationService,
    private notif: NotificationService,
    public auth: AuthService,
    private customerSvc: CustomerService,
    private productSvc: ProductService,
    private http: HttpClient
  ) {}

  ngOnInit() {
    forkJoin({
      customers: this.customerSvc.list(0, 1000).pipe(catchError(() => of(null))),
      products: this.productSvc.list(0, 1000).pipe(catchError(() => of(null))),
      users: this.http.get<any>('http://localhost:8069/api/users/all').pipe(catchError(() => of(null)))
    }).subscribe(({ customers, products, users }) => {
      // Map from customer-service profiles
      (customers?.data?.content ?? []).forEach((c: any) => {
        const name = `${c.firstName || ''} ${c.lastName || ''}`.trim();
        this.customerMap[c.customerId] = name ? `${name} (#${c.customerId})` : `#${c.customerId}`;
      });
      // Also map auth users with CUSTOMER role by userId (fallback IDs)
      (users?.data ?? []).filter((u: any) => u.role === 'CUSTOMER').forEach((u: any) => {
        if (!this.customerMap[u.userId]) {
          this.customerMap[u.userId] = `${u.name} (#${u.userId})`;
        }
      });
      (products?.data?.content ?? []).forEach((p: any) => {
        this.productMap[p.productId] = p.name;
      });
      // resolve own customerId for CUSTOMER role
      if (this.isCustomer) {
        const user = this.auth.getCurrentUser();
        const myEmail = user?.email?.toLowerCase();
        const match = (customers?.data?.content ?? []).find((c: any) => c.email?.toLowerCase() === myEmail);
        this.customerId = match?.customerId ?? user?.userId ?? null;
      }
      this.load();
    });
  }

  load() {
    this.loading = true;
    this.svc.list(this.currentPage, this.pageSize).subscribe({
      next: r => {
        let apps: LoanApplicationResponse[] = r.data?.content ?? [];
        console.log('Loaded applications:', apps);
        if (apps.length > 0) {
          console.log('First application:', apps[0]);
          console.log('First application ID:', apps[0].applicationId);
        }
        if (this.isCustomer && this.customerId) {
          apps = apps.filter(a => a.customerId === this.customerId);
        }
        this.applications = apps;
        this.totalElements = this.isCustomer ? apps.length : (r.data?.totalElements ?? 0);
        this.totalPages = r.data?.totalPages ?? 0;
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }

  openCreate() { this.form = this.emptyForm(); this.editMode = false; this.selectedId = null; this.showModal = true; this.error = ''; }

  openEdit(a: LoanApplicationResponse) {
    this.form = { customerId: a.customerId, productId: a.productId, requestedAmount: a.requestedAmount, tenureMonths: a.tenureMonths };
    this.editMode = true; this.selectedId = a.applicationId; this.showModal = true; this.error = '';
  }

  save() {
    this.saving = true; this.error = '';
    const obs = this.editMode ? this.svc.update(this.selectedId!, this.form) : this.svc.submit(this.form);
    obs.subscribe({
      next: () => {
        const msg = this.editMode ? 'Application updated!' : 'Loan application submitted!';
        this.showModal = false; this.saving = false;
        this.success = msg;
        this.notif.push(msg, 'fa-file-invoice', '#0ea5e9');
        this.load(); setTimeout(() => this.success = '', 3000);
      },
      error: e => { this.error = e.error?.message || 'Operation failed.'; this.saving = false; }
    });
  }

  approve(id: number) {
    this.svc.updateStatus(id, 'APPROVED').subscribe({
      next: () => {
        this.notif.push('Loan application approved!', 'fa-circle-check', '#10b981');
        this.load();
      },
      error: () => {}
    });
  }

  reject(id: number) {
    this.svc.updateStatus(id, 'REJECTED').subscribe({
      next: () => {
        this.notif.push('Loan application rejected.', 'fa-circle-xmark', '#ef4444');
        this.load();
      },
      error: () => {}
    });
  }

  handleStatusChange(event: Event, applicationId?: number) {
    const selectElement = event.target as HTMLSelectElement;
    const newStatus = selectElement.value;
    const dataIndex = selectElement.getAttribute('data-index');
    
    console.log('=== STATUS CHANGE DEBUG ===');
    console.log('New status:', newStatus);
    console.log('Row index:', dataIndex);
    
    if (!newStatus) {
      return;
    }
    
    // Get application from array using index
    if (dataIndex !== null) {
      const index = parseInt(dataIndex);
      const app = this.applications[index];
      console.log('Application from array:', app);
      
      if (app) {
        applicationId = app.applicationId;
        console.log('Using applicationId from array:', applicationId);
      }
    }
    
    if (!applicationId) {
      console.error('Application ID is still null or undefined!');
      console.error('Applications array:', this.applications);
      this.notif.push('Error: Application ID is missing', 'fa-circle-xmark', '#ef4444');
      return;
    }
    
    this.loading = true;
    this.svc.updateStatus(applicationId, newStatus).subscribe({
      next: (response) => {
        this.notif.push(`Status updated to ${newStatus}`, 'fa-circle-check', '#10b981');
        this.load();
      },
      error: (err) => {
        console.error('Error details:', err);
        this.loading = false;
        const errorMsg = err.error?.message || err.statusText || 'Failed to update status';
        this.notif.push(errorMsg, 'fa-circle-xmark', '#ef4444');
      }
    });
  }

  customerName(id: number) { return this.customerMap[id] || `#${id}`; }
  productName(id: number) { return this.productMap[id] || `#${id}`; }

  goPage(p: number) { this.currentPage = p; this.load(); }
  get pages() { return Array.from({ length: this.totalPages }, (_, i) => i); }

  getStatusClass(s: string) {
    const m: Record<string, string> = { APPROVED: 'badge-success', REJECTED: 'badge-danger', PENDING: 'badge-warning', UNDER_REVIEW: 'badge-info', DISBURSED: 'badge-secondary' };
    return m[s] ?? 'badge-secondary';
  }

  private emptyForm(): LoanApplicationRequest {
    return { customerId: 0, productId: 0, requestedAmount: 0, tenureMonths: 12 };
  }
}
