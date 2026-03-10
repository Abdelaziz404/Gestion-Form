import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent {

  constructor(private router: Router) { }

  scrollToLogin(event: Event): void {
    event.preventDefault();
    // Navigate to /login and update URL hash
    this.router.navigate(['/'], { fragment: 'login' }).then(() => {
      window.location.hash = 'login'; // optional, ensures hash appears
    });
  }
}