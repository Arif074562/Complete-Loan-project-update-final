import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CollectionsService } from '../../core/services/collections.service';
import { NotificationService } from '../../core/services/notification.service';
import { DelinquencyRequest, DelinquencyResponse, CollectionActionRequest, CollectionActionResponse } from '../../core/models/models';

@Component({
  selector: 'app-collections',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './collections.html',
  styleUrl: './collections.css'
})
export class CollectionsComponent implements OnInit {
  delinquencies: DelinquencyResponse[] = [];
  actions: CollectionActionResponse[] = [];
  loading = false; showDelModal = false; showActionModal = false;
  saving = false; error = ''; success = '';
  selectedDelId: number | null = null;

  delForm: DelinquencyRequest = this.emptyDel();
  actionForm: CollectionActionRequest = this.emptyAction();
  buckets = ['BUCKET_0_30', 'BUCKET_31_60', 'BUCKET_61_90', 'BUCKET_90_PLUS'];
  actionTypes = ['CALL', 'VISIT', 'EMAIL'];

  constructor(private svc: CollectionsService, private notif: NotificationService) {}
  ngOnInit() { this.load(); }

  load() {
    this.loading = true;
    this.svc.getAllDelinquencies().subscribe({
      next: r => { this.delinquencies = r.data ?? []; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  openDelModal() {
    this.delForm = this.emptyDel();
    this.showDelModal = true;
    this.error = '';
  }

  saveDel() {
    this.saving = true; this.error = '';
    this.svc.createDelinquency(this.delForm).subscribe({
      next: () => { this.showDelModal = false; this.saving = false; this.success = 'Delinquency record created!'; this.load(); setTimeout(() => this.success = '', 3000); },
      error: e => { this.error = e.error?.message || 'Failed.'; this.saving = false; }
    });
  }

  resolve(id: number) {
    if (!confirm('Resolve this delinquency?')) return;
    this.svc.resolveDelinquency(id).subscribe({
      next: () => { this.success = 'Delinquency resolved!'; this.load(); setTimeout(() => this.success = '', 3000); },
      error: e => { this.error = e.error?.message || 'Failed to resolve.'; }
    });
  }

  openAction(loanAccountId: number) {
    this.selectedDelId = loanAccountId;
    this.actionForm = this.emptyAction();
    this.actionForm.loanAccountId = loanAccountId;
    this.showActionModal = true; this.error = '';
  }

  saveAction() {
    this.saving = true; this.error = '';
    this.svc.createAction(this.actionForm).subscribe({
      next: () => {
        this.showActionModal = false; this.saving = false;
        this.success = 'Action recorded!';
        this.notif.push(`Collection action (${this.actionForm.actionType}) recorded for Account #${this.actionForm.loanAccountId}`, 'fa-phone', '#6366f1');
        this.notif.push(`A collection agent has contacted you regarding your overdue loan account #${this.actionForm.loanAccountId}. Please clear dues immediately.`, 'fa-phone', '#ef4444', 'CUSTOMER');
        setTimeout(() => this.success = '', 3000);
      },
      error: e => { this.error = e.error?.message || 'Failed.'; this.saving = false; }
    });
  }

  viewActions(loanAccountId: number) {
    this.selectedDelId = loanAccountId;
    this.svc.getActionsByDelinquency(loanAccountId).subscribe({
      next: r => { this.actions = r.data ?? []; },
      error: () => {}
    });
  }

  getBucketClass(b: string) {
    const m: Record<string, string> = { BUCKET_0_30: 'badge-success', BUCKET_31_60: 'badge-info', BUCKET_61_90: 'badge-warning', BUCKET_90_PLUS: 'badge-danger' };
    return m[b] ?? 'badge-secondary';
  }
  getStatusClass(s: string) { return s === 'RESOLVED' ? 'badge-success' : s === 'ACTIVE' ? 'badge-danger' : 'badge-warning'; }

  emptyDel(): DelinquencyRequest { return { loanAccountId: 0, daysPastDue: 0, bucket: 'BUCKET_0_30' }; }
  emptyAction(): CollectionActionRequest { return { loanAccountId: 0, actionType: 'CALL', notes: '', actionDate: '', performedBy: '' }; }
}
