import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class HomeComponent {
  features = [
    { icon: 'fa-users',                color: '#6366f1', bg: '#ede9fe', title: 'Customer Management',   desc: 'Manage customer profiles, KYC verification and segmentation with ease.' },
    { icon: 'fa-file-invoice',         color: '#0ea5e9', bg: '#dbeafe', title: 'Loan Applications',     desc: 'Submit and track loan applications through every stage of the lifecycle.' },
    { icon: 'fa-shield-halved',        color: '#10b981', bg: '#d1fae5', title: 'Underwriting & Risk',   desc: 'Automated credit scoring, risk assessment and policy-based decisions.' },
    { icon: 'fa-money-bill-transfer',  color: '#f59e0b', bg: '#fef3c7', title: 'Disbursement',          desc: 'Fast and secure fund transfers with automated repayment schedules.' },
    { icon: 'fa-rotate',               color: '#8b5cf6', bg: '#ede9fe', title: 'Loan Servicing',        desc: 'Track repayments, manage restructures and monitor loan health.' },
    { icon: 'fa-triangle-exclamation', color: '#ef4444', bg: '#fee2e2', title: 'Collections',           desc: 'Proactive delinquency management and collection action tracking.' },
  ];

  stats = [
    { value: '10K+', label: 'Loans Processed' },
    { value: '99.9%', label: 'Uptime' },
    { value: '500+', label: 'Customers Served' },
    { value: '₹50Cr+', label: 'Disbursed' },
  ];

  constructor(private router: Router) {}

  goLogin()    { this.router.navigate(['/login'], { queryParams: { tab: 'login' } }); }
  goRegister() { this.router.navigate(['/login'], { queryParams: { tab: 'signup' } }); }
}
