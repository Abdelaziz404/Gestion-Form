import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/authService/auth.service';

@Injectable({
  providedIn: 'root'
})
export class LogoutController {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  logout(): void {
    // Clear tokens and role
    this.authService.logout();

    // Redirect to login page
    this.router.navigate(['/login']);
  }
}