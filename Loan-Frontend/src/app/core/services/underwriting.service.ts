import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, UnderwritingDecisionRequest, UnderwritingDecisionResponse, CreditScoreRequest, CreditScoreResponse } from '../models/models';

@Injectable({ providedIn: 'root' })
export class UnderwritingService {
  private decBase = 'http://localhost:8069/api/underwriting/decisions';
  private csBase = 'http://localhost:8069/api/credit-scores';
  constructor(private http: HttpClient) {}

  recordDecision(req: UnderwritingDecisionRequest): Observable<ApiResponse<UnderwritingDecisionResponse>> {
    return this.http.post<ApiResponse<UnderwritingDecisionResponse>>(this.decBase, req);
  }
  getDecisionById(id: number): Observable<ApiResponse<UnderwritingDecisionResponse>> {
    return this.http.get<ApiResponse<UnderwritingDecisionResponse>>(`${this.decBase}/${id}`);
  }
  getDecisionsByApplication(id: number): Observable<ApiResponse<UnderwritingDecisionResponse[]>> {
    return this.http.get<ApiResponse<UnderwritingDecisionResponse[]>>(`${this.decBase}/application/${id}`);
  }
  updateDecision(id: number, req: UnderwritingDecisionRequest): Observable<ApiResponse<UnderwritingDecisionResponse>> {
    return this.http.put<ApiResponse<UnderwritingDecisionResponse>>(`${this.decBase}/${id}`, req);
  }
  createCreditScore(req: CreditScoreRequest): Observable<ApiResponse<CreditScoreResponse>> {
    return this.http.post<ApiResponse<CreditScoreResponse>>(this.csBase, req);
  }
  getCreditScoreByCustomer(cid: number): Observable<ApiResponse<CreditScoreResponse[]>> {
    return this.http.get<ApiResponse<CreditScoreResponse[]>>(`${this.csBase}/customer/${cid}`);
  }
}
