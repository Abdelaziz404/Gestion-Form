import { Component } from '@angular/core';
import { AuthController } from '../../../controller/authController';
import { AuthRequest } from '../../../core/dto/Auth/AuthRequest';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  email: string = '';
  password: string = '';
  loginError: string | null = null;

  constructor(private authController: AuthController) {}

  onLogin(event: Event): void {
    event.preventDefault();

    // Only check for empty fields
    if (!this.email || !this.password) {
      this.loginError = 'Please fill in both email and password.';
      return;
    }

    // Clear the error before sending request
    this.loginError = null;

    const request: AuthRequest = {
      email: this.email,
      password: this.password
    };

    // Since login returns Observable<boolean>, we subscribe
    this.authController.login(request).subscribe((isAuthValid) => {
      if (!isAuthValid) {
        this.loginError = 'Login failed. Please check your email and password.';
      } else {
        this.loginError = null; // login successful
      }
    });
  }

}