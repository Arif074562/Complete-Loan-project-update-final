import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, PageResponse, LoanAccountRequest, LoanAccountResponse, RepaymentRequest, RepaymentResponse } from '../models/models';

@Injectable({ providedIn: 'root' })
export class ServicingService {
  private accBase = 'http://localhost:8069/api/loan-accounts';
  private repBase = 'http://localhost:8069/api/repayments';
  constructor(private http: HttpClient) {}

  createAccount(req: LoanAccountRequest): Observable<ApiResponse<LoanAccountResponse>> {
    return this.http.post<ApiResponse<LoanAccountResponse>>(this.accBase, req);
  }
  listAccounts(page = 0, size = 10): Observable<ApiResponse<PageResponse<LoanAccountResponse>>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<ApiResponse<PageResponse<LoanAccountResponse>>>(this.accBase, { params });
  }
  getAccountById(id: number): Observable<ApiResponse<LoanAccountResponse>> {
    return this.http.get<ApiResponse<LoanAccountResponse>>(`${this.accBase}/${id}`);
  }
  updateAccountStatus(id: number, status: string): Observable<ApiResponse<LoanAccountResponse>> {
    return this.http.patch<ApiResponse<LoanAccountResponse>>(`${this.accBase}/${id}/status`, status);
  }
  recordRepayment(req: RepaymentRequest): Observable<ApiResponse<RepaymentResponse>> {
    return this.http.post<ApiResponse<RepaymentResponse>>(this.repBase, req);
  }
  getRepaymentsByAccount(id: number): Observable<ApiResponse<RepaymentResponse[]>> {
    return this.http.get<ApiResponse<RepaymentResponse[]>>(`${this.repBase}/account/${id}`);
  }
}
