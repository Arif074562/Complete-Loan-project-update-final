import { Injectable } from '@angular/core';

export interface AppNotification {
  id: number;
  icon: string;
  color: string;
  text: string;
  time: string;
  targetRole?: string; // 'CUSTOMER' | 'ADMIN' | undefined = all
  dueDate?: string;    // ISO date string for payment reminders
  amount?: number;
}

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private counter = 0;
  private _notifications: AppNotification[] = [];

  get notifications(): AppNotification[] { return this._notifications; }
  get count(): number { return this._notifications.length; }

  push(text: string, icon = 'fa-circle-check', color = '#10b981', targetRole?: string, dueDate?: string, amount?: number) {
    this._notifications.unshift({
      id: ++this.counter,
      icon, color, text,
      time: 'Just now',
      targetRole, dueDate, amount
    });
  }

  // Get notifications for a specific role
  forRole(role: string): AppNotification[] {
    return this._notifications.filter(n => !n.targetRole || n.targetRole === role);
  }

  // Get only payment reminders for customers
  getPaymentReminders(): AppNotification[] {
    return this._notifications.filter(n => n.targetRole === 'CUSTOMER' && !!n.dueDate);
  }

  countForRole(role: string): number {
    return this.forRole(role).length;
  }

  dismiss(id: number) {
    this._notifications = this._notifications.filter(n => n.id !== id);
  }

  markAllRead() {
    this._notifications = [];
  }
}
