import { Component, Input, Output, EventEmitter, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd, RouterModule } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from '../../core/services/auth.service';
import { NotificationService } from '../../core/services/notification.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class NavbarComponent {
  @Input() sidebarCollapsed = false;
  @Output() toggleSidebar = new EventEmitter<void>();

  pageTitle = 'Dashboard';

  private routeTitles: Record<string, string> = {
    '/dashboard': 'Dashboard',
    '/customers': 'Customers',
    '/loan-applications': 'Loan Applications',
    '/products': 'Loan Products',
    '/underwriting': 'Underwriting',
    '/disbursement': 'Disbursement',
    '/servicing': 'Loan Servicing',
    '/collections': 'Collections',
    '/users': 'User Management',
  };

  showUserMenu = false;
  showNotifMenu = false;
  isDark = false;

  get notifications() {
    const role = this.auth.getCurrentUser()?.role ?? '';
    return this.notifSvc.forRole(role);
  }
  get unreadCount() {
    const role = this.auth.getCurrentUser()?.role ?? '';
    return this.notifSvc.countForRole(role);
  }

  dismiss(id: number) { this.notifSvc.dismiss(id); }
  markAllRead()       { this.notifSvc.markAllRead(); this.showNotifMenu = false; }

  toggleTheme() {
    this.isDark = !this.isDark;
    document.documentElement.setAttribute('data-theme', this.isDark ? 'dark' : 'light');
    localStorage.setItem('theme', this.isDark ? 'dark' : 'light');
  }

  @HostListener('document:click') onDocClick() {
    this.showUserMenu = false;
    this.showNotifMenu = false;
  }

  constructor(public auth: AuthService, public notifSvc: NotificationService, private router: Router) {
    const saved = localStorage.getItem('theme');
    if (saved === 'dark') {
      this.isDark = true;
      document.documentElement.setAttribute('data-theme', 'dark');
    }
    this.router.events.pipe(filter(e => e instanceof NavigationEnd)).subscribe((e: any) => {
      const base = '/' + e.urlAfterRedirects.split('/')[1];
      this.pageTitle = this.routeTitles[base] ?? 'LoanSys360';
    });
  }
}
