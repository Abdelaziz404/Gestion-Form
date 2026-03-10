import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  nom: string = '';
  prenom: string = '';
  email: string = '';
  password: string = '';
  errorMessage: string | null = null;

  @Output() nextStep = new EventEmitter<{
    nom: string;
    prenom: string;
    email: string;
    password: string;
  }>();

  onNextStep(event: Event) {
    event.preventDefault();

    if (!this.nom || !this.prenom || !this.email || !this.password) {
      this.errorMessage = 'Veuillez remplir tous les champs.';
      return;
    }

    this.errorMessage = null;

    this.nextStep.emit({
      nom: this.nom,
      prenom: this.prenom,
      email: this.email,
      password: this.password
    });
  }
}