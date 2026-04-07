import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, DelinquencyRequest, DelinquencyResponse, CollectionActionRequest, CollectionActionResponse } from '../models/models';

@Injectable({ providedIn: 'root' })
export class CollectionsService {
  private delBase = 'http://localhost:8069/api/delinquency';
  private actBase = 'http://localhost:8069/api/collection-actions';
  constructor(private http: HttpClient) {}

  createDelinquency(req: DelinquencyRequest): Observable<ApiResponse<DelinquencyResponse>> {
    return this.http.post<ApiResponse<DelinquencyResponse>>(this.delBase, req);
  }
  getAllDelinquencies(): Observable<ApiResponse<DelinquencyResponse[]>> {
    return this.http.get<ApiResponse<DelinquencyResponse[]>>(this.delBase);
  }
  getDelinquencyById(id: number): Observable<ApiResponse<DelinquencyResponse>> {
    return this.http.get<ApiResponse<DelinquencyResponse>>(`${this.delBase}/${id}`);
  }
  getByAccountId(id: number): Observable<ApiResponse<DelinquencyResponse[]>> {
    return this.http.get<ApiResponse<DelinquencyResponse[]>>(`${this.delBase}/account/${id}`);
  }
  resolveDelinquency(id: number): Observable<ApiResponse<DelinquencyResponse>> {
    return this.http.patch<ApiResponse<DelinquencyResponse>>(`${this.delBase}/${id}/resolve`, {});
  }
  createAction(req: CollectionActionRequest): Observable<ApiResponse<CollectionActionResponse>> {
    return this.http.post<ApiResponse<CollectionActionResponse>>(this.actBase, req);
  }
  getActionsByDelinquency(id: number): Observable<ApiResponse<CollectionActionResponse[]>> {
    return this.http.get<ApiResponse<CollectionActionResponse[]>>(`${this.actBase}/account/${id}`);
  }
}
