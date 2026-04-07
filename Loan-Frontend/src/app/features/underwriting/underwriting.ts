import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { UnderwritingService } from '../../core/services/underwriting.service';
import { LoanApplicationService } from '../../core/services/loan-application.service';
import { NotificationService } from '../../core/services/notification.service';
import { UnderwritingDecisionRequest, UnderwritingDecisionResponse, CreditScoreRequest, CreditScoreResponse, LoanApplicationResponse } from '../../core/models/models';

@Component({
  selector: 'app-underwriting',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './underwriting.html',
  styleUrl: './underwriting.css'
})
export class UnderwritingComponent implements OnInit {
  // Pending applications awaiting underwriting
  pendingApps: LoanApplicationResponse[] = [];
  decisions: UnderwritingDecisionResponse[] = [];
  creditScores: CreditScoreResponse[] = [];

  activeTab = 'pending';
  showDecisionModal = false;
  showScoreModal = false;
  saving = false; error = ''; success = '';
  searchAppId = '';
  selectedApp: LoanApplicationResponse | null = null;

  decisionForm: UnderwritingDecisionRequest = this.emptyDecision();
  scoreForm: CreditScoreRequest = this.emptyScore();
  decisionTypes = ['APPROVE', 'REJECT', 'CONDITIONAL'];

  constructor(
    private svc: UnderwritingService,
    private loanAppSvc: LoanApplicationService,
    private notif: NotificationService
  ) {}

  ngOnInit() { this.loadPendingApps(); }

  loadPendingApps() {
    this.loanAppSvc.list(0, 100).subscribe({
      next: r => {
        this.pendingApps = (r.data?.content ?? []).filter(
          a => a.status === 'APPROVED' || a.status === 'UNDER_REVIEW'
        );
      },
      error: () => {}
    });
  }

  openDecision(app: LoanApplicationResponse) {
    this.selectedApp = app;
    this.decisionForm = { applicationId: app.applicationId, decision: 'APPROVE', remarks: '' };
    this.scoreForm = { applicationId: app.applicationId, scoreValue: 0, reportRef: '' };
    this.showDecisionModal = true;
    this.error = '';
    // Load existing decisions for this app
    this.svc.getDecisionsByApplication(app.applicationId).subscribe({
      next: r => { this.decisions = r.data ?? []; },
      error: () => {}
    });
  }

  saveDecision() {
    this.saving = true; this.error = '';
    this.svc.recordDecision(this.decisionForm).subscribe({
      next: () => {
        // Sync loan application status based on decision
        const newStatus = this.decisionForm.decision === 'APPROVE' ? 'APPROVED'
          : this.decisionForm.decision === 'REJECT' ? 'REJECTED'
          : 'UNDER_REVIEW';
        this.loanAppSvc.updateStatus(this.decisionForm.applicationId, newStatus).subscribe({
          next: () => {
            this.showDecisionModal = false;
            this.saving = false;
            this.success = `Decision recorded & application status updated to ${newStatus}!`;
            this.notif.push(
              `Underwriting: Application #${this.decisionForm.applicationId} → ${newStatus}`,
              'fa-gavel', this.decisionForm.decision === 'APPROVE' ? '#10b981' : this.decisionForm.decision === 'REJECT' ? '#ef4444' : '#f59e0b'
            );
            // Notify customer
            this.notif.push(
              `Your loan application #${this.decisionForm.applicationId} has been ${newStatus} by underwriting.`,
              'fa-gavel', this.decisionForm.decision === 'APPROVE' ? '#10b981' : '#ef4444',
              'CUSTOMER'
            );
            this.loadPendingApps();
            setTimeout(() => this.success = '', 4000);
          },
          error: () => { this.saving = false; }
        });
      },
      error: e => { this.error = e.error?.message || 'Failed.'; this.saving = false; }
    });
  }

  saveScore() {
    this.saving = true; this.error = '';
    this.svc.createCreditScore(this.scoreForm).subscribe({
      next: () => {
        this.showScoreModal = false;
        this.saving = false;
        this.success = 'Credit score saved!';
        this.notif.push(`Credit score ${this.scoreForm.scoreValue} recorded for App #${this.scoreForm.applicationId}`, 'fa-star', '#6366f1');
        setTimeout(() => this.success = '', 3000);
      },
      error: e => { this.error = e.error?.message || 'Failed.'; this.saving = false; }
    });
  }

  searchDecisions() {
    if (!this.searchAppId) return;
    forkJoin({
      decisions: this.svc.getDecisionsByApplication(+this.searchAppId).pipe(catchError(() => of(null))),
      scores: this.svc.getCreditScoreByCustomer(+this.searchAppId).pipe(catchError(() => of(null)))
    }).subscribe(({ decisions, scores }) => {
      this.decisions = decisions?.data ?? [];
      this.creditScores = scores?.data ?? [];
    });
  }

  getDecisionClass(t: string) {
    const m: Record<string, string> = { APPROVE: 'badge-success', REJECT: 'badge-danger', CONDITIONAL: 'badge-warning' };
    return m[t] ?? 'badge-secondary';
  }
  getAppStatusClass(s: string) {
    const m: Record<string, string> = { APPROVED: 'badge-success', REJECTED: 'badge-danger', PENDING: 'badge-warning', UNDER_REVIEW: 'badge-info', DISBURSED: 'badge-secondary' };
    return m[s] ?? 'badge-secondary';
  }
  getScoreClass(v: number) { return v >= 700 ? 'score-good' : v >= 600 ? 'score-fair' : 'score-poor'; }

  emptyDecision(): UnderwritingDecisionRequest { return { applicationId: 0, decision: 'APPROVE', remarks: '' }; }
  emptyScore(): CreditScoreRequest { return { applicationId: 0, scoreValue: 0, reportRef: '' }; }
}
