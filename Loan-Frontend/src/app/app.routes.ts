import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';
import { LayoutComponent } from './shared/layout/layout';
import { AuthComponent } from './features/auth/auth';
import { HomeComponent } from './features/home/home';
import { DashboardComponent } from './features/dashboard/dashboard';

const STAFF_ROLES = ['ADMIN', 'MANAGER', 'OFFICER', 'UNDERWRITER', 'OPERATIONS', 'COLLECTIONS'];
const UNDERWRITING_ROLES = ['ADMIN', 'MANAGER', 'OFFICER', 'UNDERWRITER'];
const OPERATIONS_ROLES   = ['ADMIN', 'MANAGER', 'OFFICER', 'OPERATIONS'];
const COLLECTIONS_ROLES  = ['ADMIN', 'MANAGER', 'OFFICER', 'COLLECTIONS'];

export const routes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' },
  { path: 'login', component: AuthComponent },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard',        loadComponent: () => import('./features/dashboard/dashboard').then(m => m.DashboardComponent) },
      { path: 'customers',        loadComponent: () => import('./features/customers/customers').then(m => m.CustomersComponent) },
      { path: 'loan-applications',loadComponent: () => import('./features/loan-applications/loan-applications').then(m => m.LoanApplicationsComponent) },
      { path: 'products',         loadComponent: () => import('./features/products/products').then(m => m.ProductsComponent) },
      {
        path: 'underwriting',
        canActivate: [roleGuard(UNDERWRITING_ROLES)],
        loadComponent: () => import('./features/underwriting/underwriting').then(m => m.UnderwritingComponent)
      },
      {
            path: 'approvals', component: DashboardComponent
      },
      {
        path: 'disbursement',
        canActivate: [roleGuard(OPERATIONS_ROLES)],
        loadComponent: () => import('./features/disbursement/disbursement').then(m => m.DisbursementComponent)
      },
      {
        path: 'servicing',
        canActivate: [roleGuard(OPERATIONS_ROLES)],
        loadComponent: () => import('./features/servicing/servicing').then(m => m.ServicingComponent)
      },
      {
        path: 'collections',
        canActivate: [roleGuard(COLLECTIONS_ROLES)],
        loadComponent: () => import('./features/collections/collections').then(m => m.CollectionsComponent)
      },
      {
        path: 'users',
        canActivate: [roleGuard(['ADMIN'])],
        loadComponent: () => import('./features/users/users').then(m => m.UsersComponent)
      },
    ]
  },
  { path: '**', redirectTo: '' }
];
