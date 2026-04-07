import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse, DisbursementRequest, DisbursementResponse, RepaymentScheduleResponse } from '../models/models';

export interface GenerateScheduleRequest { startDate: string; }

@Injectable({ providedIn: 'root' })
export class DisbursementService {
  private base = 'http://localhost:8069/api/disbursements';
  constructor(private http: HttpClient) {}

  create(req: DisbursementRequest): Observable<ApiResponse<DisbursementResponse>> {
    return this.http.post<ApiResponse<DisbursementResponse>>(this.base, req);
  }
  getById(id: number): Observable<ApiResponse<DisbursementResponse>> {
    return this.http.get<ApiResponse<DisbursementResponse>>(`${this.base}/${id}`);
  }
  getByApplicationId(id: number): Observable<ApiResponse<DisbursementResponse>> {
    return this.http.get<ApiResponse<DisbursementResponse>>(`${this.base}/application/${id}`);
  }
  updateStatus(id: number, status: string): Observable<ApiResponse<DisbursementResponse>> {
    return this.http.patch<ApiResponse<DisbursementResponse>>(`${this.base}/${id}/status`, status);
  }
  generateSchedule(id: number, req: GenerateScheduleRequest): Observable<ApiResponse<RepaymentScheduleResponse[]>> {
    return this.http.post<ApiResponse<RepaymentScheduleResponse[]>>(`${this.base}/${id}/generate-schedule`, req);
  }
}
