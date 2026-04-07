import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CustomerService } from '../../core/services/customer.service';
import { CustomerRequest, CustomerResponse, UserResponse } from '../../core/models/models';
import { NotificationService } from '../../core/services/notification.service';
import { AuthService } from '../../core/services/auth.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './customers.html',
  styleUrl: './customers.css'
})
export class CustomersComponent implements OnInit {
  customers: CustomerResponse[] = [];
  totalElements = 0; totalPages = 0; currentPage = 0; pageSize = 10;
  loading = false; showModal = false; editMode = false; saving = false;
  selectedId: number | null = null;
  error = ''; loadError = ''; success = ''; toast = '';

  form: CustomerRequest = this.emptyForm();

  constructor(private svc: CustomerService, private notif: NotificationService, public auth: AuthService, private http: HttpClient) {}

  get isAdmin() { return this.auth.getCurrentUser()?.role === 'ADMIN'; }
  get isCustomer() { return this.auth.getCurrentUser()?.role === 'CUSTOMER'; }
  get currentUserEmail() { return this.auth.getCurrentUser()?.email?.toLowerCase(); }

  ngOnInit() { this.load(); }

  load() {
    this.loading = true; this.loadError = '';
    forkJoin({
      profiles: this.svc.list(0, 1000).pipe(catchError(() => of(null))),
      users: this.http.get<any>('http://localhost:8069/api/users/all').pipe(catchError(() => of(null)))
    }).subscribe({
      next: ({ profiles, users }) => {
        const profileList: CustomerResponse[] = profiles?.data?.content ?? [];
        const profileEmails = new Set(profileList.map((c: CustomerResponse) => c.email?.toLowerCase()));
        const authCustomers: UserResponse[] = (users?.data ?? []).filter((u: UserResponse) => u.role === 'CUSTOMER');
        const unsynced: CustomerResponse[] = authCustomers
          .filter((u: UserResponse) => !profileEmails.has(u.email?.toLowerCase()))
          .map((u: UserResponse) => ({
            customerId: -(u.userId),
            firstName: u.name?.split(' ')[0] ?? u.name,
            lastName: u.name?.split(' ').slice(1).join(' ') ?? '',
            email: u.email,
            phone: u.phone,
            dateOfBirth: '', address: '', city: '', state: '',
            pinCode: '', panNumber: '', aadharNumber: '',
            segment: 'RETAIL', status: u.isActive ? 'ACTIVE' : 'INACTIVE', kycStatus: 'PENDING', createdAt: u.createdAt
          }));
        this.customers = [...profileList, ...unsynced];
        // CUSTOMER role: only show their own record
        if (this.isCustomer) {
          const myEmail = this.auth.getCurrentUser()?.email?.toLowerCase();
          this.customers = this.customers.filter(c => c.email?.toLowerCase() === myEmail);
        }
        this.totalElements = this.customers.length;
        this.totalPages = Math.ceil(this.totalElements / this.pageSize);
        this.loading = false;
      },
      error: (e) => { this.loadError = e.error?.message || 'Failed to load customers.'; this.loading = false; }
    });
  }

  openCreate() { this.form = this.emptyForm(); this.editMode = false; this.selectedId = null; this.showModal = true; this.error = ''; }

  openEdit(c: CustomerResponse) {
    this.form = { firstName: c.firstName, lastName: c.lastName, email: c.email, phone: c.phone, dateOfBirth: c.dateOfBirth, address: c.address, city: c.city, state: c.state, pinCode: c.pinCode, panNumber: c.panNumber, aadharNumber: c.aadharNumber, segment: c.segment };
    this.editMode = true; this.selectedId = c.customerId; this.showModal = true; this.error = '';
  }

  save() {
    this.saving = true; this.error = '';
    const msg = this.editMode ? 'Customer updated successfully!' : 'Customer created successfully!';
    const obs = this.editMode ? this.svc.update(this.selectedId!, this.form) : this.svc.create(this.form);
    obs.subscribe({
      next: () => {
        this.saving = false;
        this.showModal = false;
        this.toast = msg;
        this.notif.push(msg, 'fa-user', '#6366f1');
        this.load();
        setTimeout(() => { this.toast = ''; }, 3000);
      },
      error: e => { this.error = e.error?.message || 'Operation failed.'; this.saving = false; }
    });
  }

  deleteCustomer(id: number) {
    if (!confirm('Delete this customer? This cannot be undone.')) return;
    this.svc.delete(id).subscribe({
      next: () => {
        this.notif.push('Customer deleted successfully', 'fa-trash', '#ef4444');
        this.load();
      },
      error: e => {
        this.toast = e.error?.message || 'Delete failed.';
        setTimeout(() => this.toast = '', 3000);
      }
    });
  }

  updateKyc(id: number, status: string) {
    this.svc.updateKycStatus(id, status).subscribe({ next: () => this.load(), error: e => { this.toast = e.error?.message || 'KYC update failed.'; setTimeout(() => this.toast = '', 3000); } });
  }

  goPage(p: number) { this.currentPage = p; this.load(); }

  getKycClass(s: string) { return s === 'VERIFIED' ? 'badge-success' : s === 'REJECTED' ? 'badge-danger' : 'badge-warning'; }
  getStatusClass(s: string) { return s === 'ACTIVE' ? 'badge-success' : 'badge-danger'; }
  get pages() { return Array.from({ length: this.totalPages }, (_, i) => i); }

  private emptyForm(): CustomerRequest {
    return { firstName: '', lastName: '', email: '', phone: '', dateOfBirth: '', address: '', city: '', state: '', pinCode: '', panNumber: '', aadharNumber: '', segment: 'RETAIL' };
  }
}
