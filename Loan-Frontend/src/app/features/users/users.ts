import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { ApiResponse, UserResponse } from '../../core/models/models';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './users.html',
  styleUrl: './users.css'
})
export class UsersComponent implements OnInit {
  users: UserResponse[] = [];
  loading = false;
  error = '';

  constructor(private http: HttpClient) {}

  ngOnInit() { this.load(); }

  load() {
    this.loading = true;
    this.http.get<ApiResponse<UserResponse[]>>('http://localhost:8069/api/users/all').subscribe({
      next: r => { this.users = r.data ?? []; this.loading = false; },
      error: () => { this.error = 'Failed to load users.'; this.loading = false; }
    });
  }

  getRoleClass(role: string) {
    const m: Record<string, string> = { ADMIN: 'badge-danger', MANAGER: 'badge-warning', OFFICER: 'badge-info', VIEWER: 'badge-secondary' };
    return m[role] ?? 'badge-secondary';
  }
}
