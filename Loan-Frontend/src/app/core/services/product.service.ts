import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, PageResponse, LoanProductRequest, LoanProductResponse } from '../models/models';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private base = 'http://localhost:8069/api/products';
  constructor(private http: HttpClient) {}

  create(req: LoanProductRequest): Observable<ApiResponse<LoanProductResponse>> {
    return this.http.post<ApiResponse<LoanProductResponse>>(this.base, req);
  }
  list(page = 0, size = 10): Observable<ApiResponse<PageResponse<LoanProductResponse>>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<ApiResponse<PageResponse<LoanProductResponse>>>(this.base, { params });
  }
  getById(id: number): Observable<ApiResponse<LoanProductResponse>> {
    return this.http.get<ApiResponse<LoanProductResponse>>(`${this.base}/${id}`);
  }
  update(id: number, req: LoanProductRequest): Observable<ApiResponse<LoanProductResponse>> {
    return this.http.put<ApiResponse<LoanProductResponse>>(`${this.base}/${id}`, req);
  }
  deactivate(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.base}/${id}`);
  }
}
