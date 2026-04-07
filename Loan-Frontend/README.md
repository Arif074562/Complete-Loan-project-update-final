# LoanSys360 — Loan Management Frontend

A full-featured **Angular 21** single-page application for end-to-end loan lifecycle management. Built with standalone components, modern control flow syntax (`@if`, `@for`), Zone.js change detection, and JWT-based role authentication.

---

## Table of Contents

1. [Tech Stack](#tech-stack)
2. [Project Structure](#project-structure)
3. [How to Run](#how-to-run)
4. [Application Flow](#application-flow)
5. [Authentication & Authorization](#authentication--authorization)
6. [Role-Based Access Control](#role-based-access-control)
7. [Pages & Features](#pages--features)
8. [Core Services](#core-services)
9. [Shared Components](#shared-components)
10. [Notification System](#notification-system)
11. [Dark Mode](#dark-mode)
12. [API Integration](#api-integration)
13. [Key Angular Concepts Used](#key-angular-concepts-used)

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Angular | 21.2 | Frontend framework |
| TypeScript | 5.9 | Language |
| RxJS | 7.8 | Reactive HTTP & state |
| Zone.js | 0.16 | Change detection |
| Bootstrap | 5.3 | Base CSS utilities |
| Font Awesome | 6.5 | Icons |
| Angular Router | 21.2 | Client-side routing |
| Angular Forms | 21.2 | Template-driven forms |

---

## Project Structure

```
src/app/
├── core/
│   ├── guards/
│   │   ├── auth.guard.ts          # Blocks unauthenticated users
│   │   └── role.guard.ts          # Blocks users without required role
│   ├── interceptors/
│   │   └── auth.interceptor.ts    # Attaches JWT token to every HTTP request
│   ├── models/
│   │   └── models.ts              # All TypeScript interfaces matching backend DTOs
│   └── services/
│       ├── auth.service.ts        # Login, register, logout, token management
│       ├── customer.service.ts    # Customer CRUD API calls
│       ├── loan-application.service.ts
│       ├── product.service.ts
│       ├── underwriting.service.ts
│       ├── disbursement.service.ts
│       ├── servicing.service.ts
│       ├── collections.service.ts
│       └── notification.service.ts  # In-memory notification store
│
├── features/
│   ├── home/          # Public landing page (JGPay homepage)
│   ├── auth/          # Login + Register page
│   ├── dashboard/     # Stats overview + recent applications
│   ├── customers/     # Customer list, create, edit, delete
│   ├── loan-applications/  # Loan application CRUD
│   ├── products/      # Loan product management
│   ├── underwriting/  # Decisions + credit scores
│   ├── disbursement/  # Disbursement records
│   ├── servicing/     # Loan accounts + repayments
│   ├── collections/   # Delinquency + collection actions
│   └── users/         # System user list (ADMIN only)
│
├── shared/
│   ├── layout/        # App shell (sidebar + navbar + router-outlet)
│   ├── navbar/        # Top bar with notifications, theme toggle, user menu
│   └── sidebar/       # Navigation menu filtered by role
│
├── app.routes.ts      # All route definitions with guards
├── app.config.ts      # provideRouter, provideHttpClient, provideZoneChangeDetection
└── main.ts            # Bootstrap entry point (imports zone.js first)
```

---

## How to Run

```bash
# Install dependencies
npm install

# Start development server
ng serve

# App runs at http://localhost:4200
```

> **Backend must be running** at `http://localhost:8069` (API Gateway).

---

## Application Flow

```
User visits /
    │
    ▼
Homepage (JGPay Landing Page)
    │
    ├── Click "Sign In"     → /login?tab=login
    └── Click "Get Started" → /login?tab=signup
            │
            ▼
    Auth Page (Login / Register)
            │
            ├── Login  → POST /api/auth/login
            │           → JWT token saved to localStorage
            │           → Navigate to /dashboard
            │
            └── Register → POST /api/auth/register
                          → Success → auto-switch to login tab
```

Once logged in, every HTTP request automatically includes the JWT token via the **auth interceptor**.

---

## Authentication & Authorization

### JWT Flow

1. User logs in → backend returns `{ token, userId, name, role, expiresIn }`
2. Token stored in `localStorage` as `token`
3. User object stored in `localStorage` as `user`
4. `AuthInterceptor` clones every outgoing HTTP request and adds:
   ```
   Authorization: Bearer <token>
   ```
5. On logout → both keys removed → navigate to `/login`

### AuthService Key Methods

| Method | Description |
|---|---|
| `login(req)` | POST to `/api/auth/login`, stores token + user |
| `register(req)` | POST to `/api/auth/register` |
| `logout()` | Clears localStorage, navigates to `/login` |
| `isLoggedIn()` | Returns `true` if token exists |
| `getCurrentUser()` | Returns parsed user object from localStorage |
| `isAdmin()` | Returns `true` if role === `'ADMIN'` |
| `hasRole(...roles)` | Returns `true` if user's role is in the list |

---

## Role-Based Access Control

### Roles

| Role | Access Level |
|---|---|
| `ADMIN` | Full access — all pages including Users, can delete customers/products |
| `MANAGER` | Staff access — all pages except Users |
| `OFFICER` | Staff access — all pages except Users |
| `CUSTOMER` | Limited — Dashboard, Customers (register only), Loan Applications, Products |

### Two Layers of Protection

**Layer 1 — Route Guards**

```
authGuard   → checks if user is logged in (all protected routes)
roleGuard   → checks if user has required role (specific routes)
```

Routes protected by `roleGuard(STAFF_ROLES)`:
- `/underwriting`
- `/disbursement`
- `/servicing`
- `/collections`

Route protected by `roleGuard(['ADMIN'])`:
- `/users`

If a CUSTOMER tries to navigate to `/underwriting` directly in the URL bar → redirected to `/dashboard`.

**Layer 2 — Sidebar Filtering**

The sidebar `visibleItems` getter filters nav items based on the logged-in user's role. CUSTOMER only sees: Dashboard, Customers, Loan Applications, Products.

### Customer-Specific UI Changes

On the Customers page, when role is `CUSTOMER`:
- Button label changes from **"Add Customer"** → **"Register Yourself"**
- Modal title changes to **"Register as Customer"**
- KYC dropdown is hidden (shows read-only badge instead)
- Edit button is hidden
- Segment dropdown is hidden (defaults to RETAIL)

---

## Pages & Features

### 🏠 Homepage (`/`)
- Public landing page branded as **JGPay**
- Fixed navbar with Sign In + Get Started buttons
- Hero section with gradient headline and floating stat cards
- Stats bar (10K+ loans, 99.9% uptime, etc.)
- 6-card features grid
- CTA section with dark background
- Footer
- Fully responsive

### 🔐 Auth Page (`/login`)
- Tab switcher: **Sign In** / **Sign Up**
- Reads `?tab=signup` query param to auto-open register tab
- Login form: email + password with show/hide toggle
- Register form: name, phone, email, role (no ADMIN option), password + confirm
- Left panel with feature highlights

### 📊 Dashboard (`/dashboard`)
- 4 stat cards: Total Customers, Loan Applications, Loan Products, Active Accounts
- Uses `forkJoin` with `catchError` — all 4 API calls run in parallel
- If any service is down, others still load (graceful degradation)
- Recent Applications table
- Quick Actions panel

### 👥 Customers (`/customers`)
- Paginated table with search
- Create / Edit customer modal with full form (firstName, lastName, email, phone, DOB, address, city, state, pinCode, panNumber, aadharNumber, segment)
- KYC status update (PENDING / VERIFIED / REJECTED)
- Delete customer (ADMIN only) — also deletes related loan applications to avoid FK constraint
- Toast notification on create/update/delete
- Role-aware UI for CUSTOMER role

### 📋 Loan Applications (`/loan-applications`)
- Submit new loan application (customerId, productId, requestedAmount, tenureMonths)
- Status update dropdown (PENDING → UNDER_REVIEW → APPROVED → REJECTED → DISBURSED)
- Paginated table

### 📦 Products (`/products`)
- Card-based grid layout
- Create / Edit product (name, interestRate, minAmount, maxAmount, minTenure, maxTenure)
- Delete product (ADMIN only)
- Toast notification on actions

### ⚖️ Underwriting (`/underwriting`) — Staff only
- Two tabs: Decisions | Credit Scores
- Record underwriting decision (applicationId, decision: APPROVE/REJECT/CONDITIONAL, remarks)
- Add credit score (applicationId, scoreValue 0-900, reportRef)
- Search by Application ID

### 💸 Disbursement (`/disbursement`) — Staff only
- Record disbursements with account details
- View repayment schedules

### 🔄 Servicing (`/servicing`) — Staff only
- Manage loan accounts
- Record repayments

### ⚠️ Collections (`/collections`) — Staff only
- Create delinquency records (loanAccountId, daysPastDue, bucket: BUCKET_0_30 / BUCKET_31_60 / BUCKET_61_90 / BUCKET_90_PLUS)
- Resolve delinquency
- Record collection actions (CALL / VISIT / EMAIL)
- View actions per account

### 👤 Users (`/users`) — ADMIN only
- List all system users
- Shows userId, name, email, phone, role, active status, created date

---

## Core Services

All services are `providedIn: 'root'` singletons. They use Angular's `HttpClient` and return `Observable<ApiResponse<T>>`.

### API Response Wrapper

Every backend response follows:
```typescript
interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}
```

### Paginated Responses

```typescript
interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
```

### Service → Endpoint Mapping

| Service | Base URL |
|---|---|
| AuthService | `/api/auth` |
| CustomerService | `/api/customers` |
| LoanApplicationService | `/api/loan-applications` |
| ProductService | `/api/products` |
| UnderwritingService | `/api/underwriting/decisions`, `/api/credit-scores` |
| DisbursementService | `/api/disbursements` |
| ServicingService | `/api/loan-accounts`, `/api/repayments` |
| CollectionsService | `/api/delinquency`, `/api/collection-actions` |

All routed through **API Gateway at port 8069**.

---

## Shared Components

### Layout (`app-layout`)
- App shell: sidebar (fixed left) + main area (navbar + content)
- Contains the `₹` watermark (fixed, `opacity: 0.06`, rotated -25°)
- Handles sidebar collapse toggle

### Navbar (`app-navbar`)
- Page title auto-updates based on current route
- **Theme toggle** (moon/sun icon) — switches dark/light mode, persists in localStorage
- **Notification bell** — shows numbered badge, dropdown with dismiss per item + mark all read
- **User chip** — shows avatar initial + name + role, click opens dropdown with Logout button
- Clicking outside closes all dropdowns via `@HostListener('document:click')`

### Sidebar (`app-sidebar`)
- Filters nav items by role using `visibleItems` getter
- Highlights active route with `routerLinkActive="active"`
- Collapsible (icon-only mode)

---

## Notification System

A singleton `NotificationService` acts as an in-memory store:

```typescript
push(text, icon, color)  // Add notification
dismiss(id)              // Remove single notification
markAllRead()            // Clear all
```

Components push notifications on success:
- Customer created/updated → purple user icon
- Product created/updated → green box icon
- Loan application submitted → blue invoice icon
- Customer/Product deleted → red trash icon

The navbar reads from the service reactively — badge count updates instantly.

---

## Dark Mode

- Toggle button in navbar (moon → sun icon)
- Sets `data-theme="dark"` attribute on `<html>` element
- CSS variables in `styles.css` override under `[data-theme="dark"]`:
  - `--card-bg: #1e293b`
  - `--body-bg: #0f172a`
  - `--text-primary: #f1f5f9`
  - `--border: #334155`
- Preference saved to `localStorage` and restored on page load

---

## API Integration

### Auth Interceptor

```typescript
// Automatically adds JWT to every request
req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
```

### Error Handling Pattern

Every component subscription follows:
```typescript
obs.subscribe({
  next: r  => { /* handle success */ },
  error: e => { this.error = e.error?.message || 'Operation failed.'; }
});
```

### Date Format

`dateOfBirth` sent as `yyyy-MM-dd` string. Backend uses `@JsonFormat(pattern = "yyyy-MM-dd")` on `LocalDate` fields.

---

## Key Angular Concepts Used

| Concept | Where Used |
|---|---|
| Standalone Components | Every component — no NgModules |
| `provideZoneChangeDetection` | `app.config.ts` — enables Zone.js for HTTP change detection |
| Lazy Loading | All feature routes use `loadComponent()` |
| Functional Guards | `authGuard`, `roleGuard` as `CanActivateFn` |
| HTTP Interceptors | `authInterceptor` as functional interceptor |
| `forkJoin` + `catchError` | Dashboard — parallel API calls with graceful failure |
| Template-driven Forms | All modals use `#f="ngForm"` with `[(ngModel)]` |
| `@if` / `@for` / `@empty` | Modern Angular 17+ control flow (no `*ngIf`/`*ngFor`) |
| `@HostListener` | Navbar — close dropdowns on outside click |
| CSS Custom Properties | Full theming system via `--variable` tokens |
| `localStorage` | JWT token, user object, theme preference |
| `ActivatedRoute` | Auth page reads `?tab=` query param |
| `RouterLink` + `RouterLinkActive` | Sidebar navigation |

---

## Environment

- **Frontend**: `http://localhost:4200`
- **API Gateway**: `http://localhost:8069`
- **Service Registry (Eureka)**: `http://localhost:8761`

All backend microservices register with Eureka and are load-balanced through the gateway using `lb://service-name` URIs.
