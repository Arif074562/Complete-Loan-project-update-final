export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

// Auth  — matches backend UserRequestDTO / LoginRequestDTO exactly
export interface LoginRequest { email: string; password: string; }
export interface LoginResponse { token: string; userId: number; name: string; email: string; role: string; expiresIn: number; }
export interface UserRequest { name: string; email: string; password: string; role: string; phone?: string; }
export interface UserResponse { userId: number; name: string; email: string; role: string; phone: string; isActive: boolean; createdAt: string; }

// Customer
export interface CustomerRequest {
  firstName: string; lastName: string; email: string; phone: string;
  dateOfBirth: string; address: string; city: string; state: string;
  pinCode: string; panNumber: string; aadharNumber: string; segment: string;
}
export interface CustomerResponse {
  customerId: number; firstName: string; lastName: string; email: string; phone: string;
  dateOfBirth: string; address: string; city: string; state: string;
  pinCode: string; panNumber: string; aadharNumber: string;
  segment: string; status: string; kycStatus: string; createdAt: string;
}

// Loan Application
export interface LoanApplicationRequest {
  customerId: number; productId: number; requestedAmount: number; tenureMonths: number;
}
export interface LoanApplicationResponse {
  applicationId: number; customerId: number; productId: number; requestedAmount: number;
  tenureMonths: number; applicationDate: string; status: string; createdAt: string;
}

// Product
export interface LoanProductRequest {
  name: string; interestRate: number;
  minAmount: number; maxAmount: number;
  minTenure: number; maxTenure: number;
}
export interface LoanProductResponse {
  productId: number; name: string; interestRate: number;
  minAmount: number; maxAmount: number;
  minTenure: number; maxTenure: number;
  status: string;
}

// Underwriting
export interface UnderwritingDecisionRequest {
  applicationId: number; decision: string; remarks: string;
}
export interface UnderwritingDecisionResponse {
  decisionId: number; applicationId: number; underwriterId: number;
  decision: string; remarks: string; decisionDate: string;
}
export interface CreditScoreRequest { applicationId: number; scoreValue: number; reportRef: string; }
export interface CreditScoreResponse { scoreId: number; applicationId: number; scoreValue: number; reportRef: string; generatedDate: string; }

// Disbursement
export interface DisbursementRequest {
  applicationId: number; amount: number; accountNumber: string;
  ifscCode: string; bankName: string; disbursementDate: string;
}
export interface DisbursementResponse {
  id: number; applicationId: number; amount: number; accountNumber: string;
  ifscCode: string; bankName: string; disbursementDate: string; status: string; createdAt: string;
}
export interface RepaymentScheduleResponse {
  id: number; disbursementId: number; installmentNumber: number;
  dueDate: string; principalAmount: number; interestAmount: number;
  totalAmount: number; status: string;
}

// Loan Account (Servicing)
export interface LoanAccountRequest {
  applicationId: number; disbursementId: number; outstandingBalance: number;
  nextDueDate: string; emiAmount: number;
}
export interface LoanAccountResponse {
  id: number; applicationId: number; disbursementId: number; outstandingBalance: number;
  nextDueDate: string; emiAmount: number; status: string; createdAt: string;
}
export interface RepaymentRequest {
  loanAccountId: number; amount: number; paymentDate: string; mode: string; referenceNumber: string;
}
export interface RepaymentResponse {
  id: number; loanAccountId: number; amount: number; paymentDate: string;
  mode: string; referenceNumber: string; status: string; createdAt: string;
}

// Collections
export interface DelinquencyRequest {
  loanAccountId: number; daysPastDue: number; bucket: string;
}
export interface DelinquencyResponse {
  delinquencyId: number; loanAccountId: number; daysPastDue: number;
  bucket: string; status: string; recordedDate: string;
}
export interface CollectionActionRequest {
  loanAccountId: number; actionType: string; notes: string; actionDate: string; performedBy: string;
}
export interface CollectionActionResponse {
  actionId: number; loanAccountId: number; actionType: string; notes: string; actionDate: string; performedBy: string;
}
