import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../core/services/product.service';
import { LoanProductRequest, LoanProductResponse, LoanApplicationRequest } from '../../core/models/models';
import { NotificationService } from '../../core/services/notification.service';
import { AuthService } from '../../core/services/auth.service';
import { LoanApplicationService } from '../../core/services/loan-application.service';
import { CustomerService } from '../../core/services/customer.service';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './products.html',
  styleUrl: './products.css'
})
export class ProductsComponent implements OnInit {
  products: LoanProductResponse[] = [];
  totalElements = 0; totalPages = 0; currentPage = 0; pageSize = 10;
  loading = false; showModal = false; editMode = false; saving = false;
  selectedId: number | null = null;
  error = ''; success = '';
  form: LoanProductRequest = this.emptyForm();

  // Apply modal
  showApplyModal = false; applyProduct: LoanProductResponse | null = null;
  applyForm = { amount: 0, tenure: 12 };
  applyError = ''; applying = false;
  customerId: number | null = null;

  get isAdmin() { return this.auth.getCurrentUser()?.role === 'ADMIN'; }
  get isCustomer() { return this.auth.getCurrentUser()?.role === 'CUSTOMER'; }

  constructor(
    private svc: ProductService,
    private notif: NotificationService,
    public auth: AuthService,
    private loanAppSvc: LoanApplicationService,
    private customerSvc: CustomerService
  ) {}

  ngOnInit() {
    this.load();
    if (this.isCustomer) { this.resolveCustomerId(); }
  }

  resolveCustomerId() {
    const user = this.auth.getCurrentUser();
    // First try to find a matching customer profile by email
    this.customerSvc.list(0, 1000).subscribe({
      next: r => {
        const myEmail = user?.email?.toLowerCase();
        const match = (r.data?.content ?? []).find(c => c.email?.toLowerCase() === myEmail);
        // Use profile customerId if found, otherwise fall back to auth userId
        this.customerId = match?.customerId ?? user?.userId ?? null;
      },
      error: () => {
        // If customer-service fails, fall back to auth userId
        this.customerId = user?.userId ?? null;
      }
    });
  }

  load() {
    this.loading = true;
    this.svc.list(this.currentPage, this.pageSize).subscribe({
      next: r => { this.products = r.data?.content ?? []; this.totalElements = r.data?.totalElements ?? 0; this.totalPages = r.data?.totalPages ?? 0; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  openCreate() { this.form = this.emptyForm(); this.editMode = false; this.selectedId = null; this.showModal = true; this.error = ''; }

  openEdit(p: LoanProductResponse) {
    this.form = { name: p.name, interestRate: p.interestRate, minAmount: p.minAmount, maxAmount: p.maxAmount, minTenure: p.minTenure, maxTenure: p.maxTenure };
    this.editMode = true; this.selectedId = p.productId; this.showModal = true; this.error = '';
  }

  save() {
    this.saving = true; this.error = '';
    const obs = this.editMode ? this.svc.update(this.selectedId!, this.form) : this.svc.create(this.form);
    obs.subscribe({
      next: () => {
        const msg = this.editMode ? 'Product updated!' : 'Product created!';
        this.showModal = false; this.saving = false;
        this.success = msg;
        this.notif.push(msg, 'fa-box-open', '#10b981');
        this.load(); setTimeout(() => this.success = '', 3000);
      },
      error: e => { this.error = e.error?.message || 'Operation failed.'; this.saving = false; }
    });
  }

  deactivate(id: number) {
    if (!confirm('Delete this product?')) return;
    this.svc.deactivate(id).subscribe({
      next: () => {
        this.notif.push('Product deleted successfully', 'fa-trash', '#ef4444');
        this.load();
      },
      error: () => {}
    });
  }

  openApply(p: LoanProductResponse) {
    this.applyProduct = p;
    this.applyForm = { amount: p.minAmount, tenure: p.minTenure };
    this.applyError = ''; this.showApplyModal = true;
  }

  submitApply() {
    const cid = this.customerId ?? this.auth.getCurrentUser()?.userId;
    if (!cid) { this.applyError = 'Unable to identify your account. Please log in again.'; return; }
    this.applying = true; this.applyError = '';
    const req: LoanApplicationRequest = {
      customerId: cid,
      productId: this.applyProduct!.productId,
      requestedAmount: this.applyForm.amount,
      tenureMonths: this.applyForm.tenure
    };
    this.loanAppSvc.submit(req).subscribe({
      next: () => {
        this.applying = false; this.showApplyModal = false;
        this.success = 'Loan application submitted! Admin will review it shortly.';
        this.notif.push(`New loan application for ${this.applyProduct!.name} — ₹${this.applyForm.amount}`, 'fa-file-invoice', '#6366f1');
        setTimeout(() => this.success = '', 4000);
      },
      error: e => { this.applyError = e.error?.message || 'Application failed.'; this.applying = false; }
    });
  }

  goPage(p: number) { this.currentPage = p; this.load(); }
  get pages() { return Array.from({ length: this.totalPages }, (_, i) => i); }
  getStatusClass(s: string) { return s === 'ACTIVE' ? 'badge-success' : 'badge-danger'; }

  private emptyForm(): LoanProductRequest {
    return { name: '', interestRate: 0, minAmount: 0, maxAmount: 0, minTenure: 6, maxTenure: 60 };
  }
}
