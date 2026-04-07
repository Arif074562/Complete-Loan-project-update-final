import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './auth.html',
  styleUrl: './auth.css'
})
export class AuthComponent {
  activeTab: 'login' | 'signup' = 'login';

  // Login — backend uses email + password
  loginEmail = '';
  loginPassword = '';
  showLoginPassword = false;

  // Register — backend UserRequestDTO: name, email, password, role, phone
  regName = '';
  regEmail = '';
  regPassword = '';
  regConfirmPassword = '';
  regPhone = '';
  regRole = 'OFFICER';
  showRegPassword = false;

  loading = false;
  error = '';
  success = '';

  // Must match backend UserRole enum exactly
  roles = [
    { value: 'OFFICER',     label: 'Loan Officer' },
    { value: 'UNDERWRITER', label: 'Underwriter' },
    { value: 'OPERATIONS',  label: 'Operations' },
    { value: 'COLLECTIONS', label: 'Collections Agent' },
    { value: 'CUSTOMER',    label: 'Customer' },
  ];

  features = [
    { icon: 'fa-users',               title: 'Customer Management',  desc: 'KYC, profiles, and segmentation' },
    { icon: 'fa-shield-halved',       title: 'Underwriting & Risk',  desc: 'Credit scoring and policy decisions' },
    { icon: 'fa-money-bill-transfer', title: 'Disbursement',         desc: 'Fund transfers and repayment schedules' },
    { icon: 'fa-rotate',              title: 'Loan Servicing',       desc: 'Repayments, restructures, and lifecycle' },
  ];

  constructor(private auth: AuthService, private router: Router, private route: ActivatedRoute) {
    this.route.queryParams.subscribe(p => {
      if (p['tab'] === 'signup') this.activeTab = 'signup';
    });
  }

  switchTab(tab: 'login' | 'signup') {
    this.activeTab = tab;
    this.error = '';
    this.success = '';
  }

  login() {
    if (!this.loginEmail || !this.loginPassword) {
      this.error = 'Please enter your email and password.';
      return;
    }
    this.loading = true;
    this.error = '';
    this.auth.login({ email: this.loginEmail, password: this.loginPassword }).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: (e) => {
        this.error = e.error?.message || 'Invalid email or password.';
        this.loading = false;
      }
    });
  }

  register() {
    if (!this.regName || !this.regEmail || !this.regPassword || !this.regConfirmPassword) {
      this.error = 'Please fill all required fields.';
      return;
    }
    if (this.regPassword !== this.regConfirmPassword) {
      this.error = 'Passwords do not match.';
      return;
    }
    this.loading = true;
    this.error = '';
    this.auth.register({
      name: this.regName,
      email: this.regEmail,
      password: this.regPassword,
      role: this.regRole,
      phone: this.regPhone || undefined
    }).subscribe({
      next: () => {
        this.loading = false;
        this.success = 'Account created successfully! You can now sign in.';
        this.regName = ''; this.regEmail = ''; this.regPassword = '';
        this.regConfirmPassword = ''; this.regPhone = '';
        setTimeout(() => { this.success = ''; this.switchTab('login'); }, 2500);
      },
      error: (e) => {
        this.error = e.error?.message || 'Registration failed. Please try again.';
        this.loading = false;
      }
    });
  }
}
