import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent {

  constructor(private router: Router) { }

  navigateToLogin() {
    this.router.navigate(['/login']);
  }

  navigateToRegister() {
    this.router.navigate(['/register']);
  }

  scrollToLogin(event: Event) {
    event.preventDefault();
    const element = document.querySelector('#login');
    if (element) element.scrollIntoView({ behavior: 'smooth' });
  }

  navigateToEnvoyerCandidature() {
    this.router.navigate(['/envoyer-candidature']);
  }
}

