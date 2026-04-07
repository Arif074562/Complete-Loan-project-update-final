import { Component, Input, Output, EventEmitter } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';

interface NavItem { label: string; icon: string; route: string; roles: string[]; }

const ALL   = ['ADMIN', 'MANAGER', 'OFFICER', 'UNDERWRITER', 'OPERATIONS', 'COLLECTIONS', 'CUSTOMER'];
const STAFF = ['ADMIN', 'MANAGER', 'OFFICER', 'UNDERWRITER', 'OPERATIONS', 'COLLECTIONS'];

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css'
})
export class SidebarComponent {
  @Input() collapsed = false;
  @Output() toggleSidebar = new EventEmitter<void>();

  navItems: NavItem[] = [
    { label: 'Dashboard',         icon: 'fa-gauge-high',           route: '/dashboard',         roles: ALL },
    { label: 'Customers',         icon: 'fa-users',                route: '/customers',         roles: ALL },
    { label: 'Loan Applications', icon: 'fa-file-invoice',         route: '/loan-applications', roles: ALL },
    { label: 'Products',          icon: 'fa-box-open',             route: '/products',          roles: ALL },
    { label: 'Underwriting',      icon: 'fa-shield-halved',        route: '/underwriting',      roles: ['ADMIN', 'MANAGER', 'OFFICER', 'UNDERWRITER'] },
    { label: 'Disbursement',      icon: 'fa-money-bill-transfer',  route: '/disbursement',      roles: ['ADMIN', 'MANAGER', 'OFFICER', 'OPERATIONS'] },
    { label: 'Servicing',         icon: 'fa-rotate',               route: '/servicing',         roles: ['ADMIN', 'MANAGER', 'OFFICER', 'OPERATIONS'] },
    { label: 'Collections',       icon: 'fa-triangle-exclamation', route: '/collections',       roles: ['ADMIN', 'MANAGER', 'OFFICER', 'COLLECTIONS'] },
    { label: 'Users',             icon: 'fa-user-gear',            route: '/users',             roles: ['ADMIN'] },
  ];

  constructor(public auth: AuthService, private router: Router) {}

  logout() { this.auth.logout(); }

  get visibleItems() {
    const role = this.auth.getCurrentUser()?.role ?? '';
    return this.navItems.filter(i => i.roles.includes(role));
  }
}
