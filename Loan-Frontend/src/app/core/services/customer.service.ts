import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, PageResponse, CustomerRequest, CustomerResponse } from '../models/models';

@Injectable({ providedIn: 'root' })
export class CustomerService {
  private base = 'http://localhost:8069/api/customers';
  constructor(private http: HttpClient) {}

  create(req: CustomerRequest): Observable<ApiResponse<CustomerResponse>> {
    return this.http.post<ApiResponse<CustomerResponse>>(this.base, req);
  }
  list(page = 0, size = 10): Observable<ApiResponse<PageResponse<CustomerResponse>>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<ApiResponse<PageResponse<CustomerResponse>>>(this.base, { params });
  }
  getById(id: number): Observable<ApiResponse<CustomerResponse>> {
    return this.http.get<ApiResponse<CustomerResponse>>(`${this.base}/${id}`);
  }
  update(id: number, req: CustomerRequest): Observable<ApiResponse<CustomerResponse>> {
    return this.http.put<ApiResponse<CustomerResponse>>(`${this.base}/${id}`, req);
  }
  updateKycStatus(id: number, status: string): Observable<ApiResponse<CustomerResponse>> {
    return this.http.patch<ApiResponse<CustomerResponse>>(`${this.base}/${id}/kyc-status`, JSON.stringify(status), { headers: { 'Content-Type': 'application/json' } });
  }
  delete(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.base}/${id}`);
  }
}
