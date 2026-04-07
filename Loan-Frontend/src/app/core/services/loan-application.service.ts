import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, PageResponse, LoanApplicationRequest, LoanApplicationResponse } from '../models/models';

@Injectable({ providedIn: 'root' })
export class LoanApplicationService {
  private base = 'http://localhost:8069/api/loan-applications';
  constructor(private http: HttpClient) {}

  submit(req: LoanApplicationRequest): Observable<ApiResponse<LoanApplicationResponse>> {
    return this.http.post<ApiResponse<LoanApplicationResponse>>(this.base, req);
  }
  list(page = 0, size = 10): Observable<ApiResponse<PageResponse<LoanApplicationResponse>>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('_t', Date.now().toString());
    const headers = { 'Cache-Control': 'no-cache', 'Pragma': 'no-cache' };
    return this.http.get<ApiResponse<PageResponse<LoanApplicationResponse>>>(this.base, { params, headers });
  }
  getById(id: number): Observable<ApiResponse<LoanApplicationResponse>> {
    return this.http.get<ApiResponse<LoanApplicationResponse>>(`${this.base}/${id}`);
  }
  update(id: number, req: LoanApplicationRequest): Observable<ApiResponse<LoanApplicationResponse>> {
    return this.http.put<ApiResponse<LoanApplicationResponse>>(`${this.base}/${id}`, req);
  }
  updateStatus(id: number, status: string): Observable<ApiResponse<LoanApplicationResponse>> {
    return this.http.patch<ApiResponse<LoanApplicationResponse>>(
      `${this.base}/${id}/status`,
      JSON.stringify(status),
      { headers: { 'Content-Type': 'application/json' } }
    );
  }
  getByCustomerId(cid: number): Observable<ApiResponse<LoanApplicationResponse[]>> {
    return this.http.get<ApiResponse<LoanApplicationResponse[]>>(`${this.base}/customer/${cid}`);
  }
}
