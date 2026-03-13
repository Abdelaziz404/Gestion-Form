import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../core/services/authService/auth.service';
import { AuthRequest } from 'src/app/core/dto/Auth/AuthRequest';
import { Role } from '../shared/utils/Enum/role';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthController {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login(request: AuthRequest): Observable<boolean> {
    if (!request.email || !request.password) {
      return of(false);
    }

    return this.authService.login(request).pipe(
      map((response) => {
        // mark session active (token is in HttpOnly cookie)
        // this.authService.setToken(); // not needed

        // store all user info in localStorage
        this.authService.storeUserInfo(response);

        // redirect based on role
        const role = response.role as Role;
        this.handleRole(role);

        return true;
      }),
      catchError(() => of(false)) // login failed
    );
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']); // redirect to homepage
  }

  private handleRole(role: Role): void {
    switch (role) {
      case Role.FORMATEUR:
        this.router.navigate(['/dashboard_formateur']);
        break;
      case Role.ADMIN:
        this.router.navigate(['/admin-dashboard']);
        break;
      default:
        this.router.navigate(['/']);
        break;
    }
  }
}