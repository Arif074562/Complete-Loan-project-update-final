import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DisbursementService } from '../../core/services/disbursement.service';
import { ServicingService } from '../../core/services/servicing.service';
import { LoanApplicationService } from '../../core/services/loan-application.service';
import { NotificationService } from '../../core/services/notification.service';
import { DisbursementRequest, DisbursementResponse, RepaymentScheduleResponse } from '../../core/models/models';

@Component({
  selector: 'app-disbursement',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './disbursement.html',
  styleUrl: './disbursement.css'
})
export class DisbursementComponent {
  disbursement: DisbursementResponse | null = null;
  schedule: RepaymentScheduleResponse[] = [];
  searchAppId = '';
  showCreateModal = false; showScheduleModal = false;
  saving = false; error = ''; success = '';
  scheduleStartDate = '';

  statuses = ['PENDING', 'PROCESSING', 'COMPLETED', 'FAILED', 'REVERSED'];
  form: DisbursementRequest = this.emptyForm();

  constructor(
    private svc: DisbursementService,
    private servicingSvc: ServicingService,
    private loanAppSvc: LoanApplicationService,
    private notif: NotificationService
  ) {}

  search() {
    if (!this.searchAppId) return;
    this.svc.getByApplicationId(+this.searchAppId).subscribe({
      next: r => { this.disbursement = r.data; this.schedule = []; },
      error: () => { this.disbursement = null; }
    });
  }

  create() {
    this.saving = true; this.error = '';
    this.svc.create(this.form).subscribe({
      next: r => {
        this.disbursement = r.data;
        this.showCreateModal = false;
        this.saving = false;
        this.success = 'Disbursement created! Loan marked as DISBURSED.';

        // 1. Mark loan application as DISBURSED
        this.loanAppSvc.updateStatus(r.data.applicationId, 'DISBURSED').subscribe({
          next: () => {},
          error: () => {}
        });

        // 2. Auto-create loan account in servicing
        this.servicingSvc.createAccount({
          applicationId: r.data.applicationId,
          disbursementId: r.data.disbursementId,
          outstandingBalance: r.data.amount,
          nextDueDate: this.form.disbursementDate,
          emiAmount: 0
        }).subscribe({ next: () => {}, error: () => {} });

        // 3. Admin notification
        this.notif.push(
          `Disbursement #${r.data.disbursementId} — ₹${r.data.amount} disbursed for App #${r.data.applicationId}`,
          'fa-money-bill-transfer', '#10b981'
        );

        // 4. Customer notification with account details
        this.notif.push(
          `Your loan of ₹${r.data.amount} has been disbursed to account ${r.data.accountNumber} (${r.data.bankName}). Loan is now ACTIVE.`,
          'fa-money-bill-transfer', '#10b981', 'CUSTOMER'
        );

        setTimeout(() => this.success = '', 4000);
      },
      error: e => { this.error = e.error?.message || 'Failed.'; this.saving = false; }
    });
  }

  updateStatus(status: string) {
    if (!this.disbursement) return;
    this.svc.updateStatus(this.disbursement.disbursementId, status).subscribe({
      next: r => { this.disbursement = r.data; this.success = 'Status updated!'; setTimeout(() => this.success = '', 3000); },
      error: () => {}
    });
  }

  generateSchedule() {
    if (!this.disbursement || !this.scheduleStartDate) return;
    this.saving = true;
    this.svc.generateSchedule(this.disbursement.disbursementId, { startDate: this.scheduleStartDate }).subscribe({
      next: r => {
        this.schedule = r.data ?? [];
        this.showScheduleModal = false;
        this.saving = false;
        this.success = 'Schedule generated!';
        setTimeout(() => this.success = '', 3000);
        this.notif.push(
          `Repayment schedule generated for Disbursement #${this.disbursement!.disbursementId} — ${this.schedule.length} installments`,
          'fa-calendar-days', '#6366f1'
        );
        this.schedule.forEach(s => {
          this.notif.push(
            `EMI Due: ₹${s.totalAmount.toFixed(2)} on ${new Date(s.dueDate).toLocaleDateString('en-IN', { day: '2-digit', month: 'short', year: 'numeric' })} (Installment #${s.installmentNumber})`,
            'fa-calendar-check', '#f59e0b', 'CUSTOMER', s.dueDate, s.totalAmount
          );
        });
      },
      error: e => { this.error = e.error?.message || 'Failed.'; this.saving = false; }
    });
  }

  openCreate() { this.form = this.emptyForm(); this.showCreateModal = true; this.error = ''; }

  getStatusClass(s: string) {
    const m: Record<string, string> = { COMPLETED: 'badge-success', FAILED: 'badge-danger', PENDING: 'badge-warning', PROCESSING: 'badge-info', REVERSED: 'badge-secondary' };
    return m[s] ?? 'badge-secondary';
  }
  getInstallmentClass(s: string) { return s === 'PAID' ? 'badge-success' : s === 'OVERDUE' ? 'badge-danger' : 'badge-warning'; }

  emptyForm(): DisbursementRequest {
    return { applicationId: 0, amount: 0, accountNumber: '', ifscCode: '', bankName: '', disbursementDate: '' };
  }
}
