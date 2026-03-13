import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { AuthRequest } from '../../dto/Auth/AuthRequest';
import { AuthResponse } from '../../dto/Auth/AuthResponse';
import { Role } from '../../../shared/utils/Enum/role';
import { API_BASE_URL } from 'src/app/shared/utils/constants';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private API = `${API_BASE_URL}/api/v1/auth`;

  private roleSubject = new BehaviorSubject<Role | null>(null);
  role$ = this.roleSubject.asObservable();

  constructor(private http: HttpClient) {
    const savedRole = localStorage.getItem('user_role');
    if (savedRole) {
      this.roleSubject.next(savedRole as Role);
    }
  }

  login(data: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(
      `${this.API}/login`,
      data,
      { withCredentials: true } // store JWT in HttpOnly cookie
    );
  }

  setToken(): void {
    // token is HttpOnly → just mark session active
  }

  storeUserInfo(response: AuthResponse): void {
    // Save role
    const role = response.role as Role;
    localStorage.setItem('user_role', role);
    this.roleSubject.next(role);

    // Save permissions (default 0)
    const permissions = 0; // response.permissions ?? 0;
    localStorage.setItem('permissions', permissions.toString());

    // Save other profile info
    localStorage.setItem('user_id', response.id.toString());
    localStorage.setItem('user_email', response.email);
    localStorage.setItem('user_firstName', response.firstName);
    localStorage.setItem('user_lastName', response.lastName);
  }

  getRole(): Role | null {
    return this.roleSubject.value;
  }

  getPermissions(): number {
    const p = localStorage.getItem('permissions');
    return p ? parseInt(p, 10) : 0;
  }

  getUserId(): number | null {
    const id = localStorage.getItem('user_id');
    return id ? parseInt(id, 10) : null;
  }

  getUserEmail(): string | null {
    return localStorage.getItem('user_email');
  }

  getUserFirstName(): string | null {
    return localStorage.getItem('user_firstName');
  }

  getUserLastName(): string | null {
    return localStorage.getItem('user_lastName');
  }

  isFormateur(): boolean {
    return this.roleSubject.value === Role.FORMATEUR;
  }

  isAdmin(): boolean {
    return this.roleSubject.value === Role.ADMIN;
  }

  logout(): void {
    this.http.post(`${this.API}/logout`, {}, { withCredentials: true }).subscribe();
    // Clear all localStorage info
    localStorage.removeItem('user_role');
    localStorage.removeItem('permissions');
    localStorage.removeItem('user_id');
    localStorage.removeItem('user_email');
    localStorage.removeItem('user_firstName');
    localStorage.removeItem('user_lastName');
    this.roleSubject.next(null);
  }
}