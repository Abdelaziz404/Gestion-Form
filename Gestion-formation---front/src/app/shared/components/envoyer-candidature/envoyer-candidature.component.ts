import { Component } from '@angular/core';

@Component({
  selector: 'app-envoyer-candidature',
  templateUrl: './envoyer-candidature.component.html',
  styleUrls: ['./envoyer-candidature.component.scss']
})
export class EnvoyerCandidatureComponent {

  currentStep: number = 1;

  // Step 1: Personal info
  nom: string = '';
  prenom: string = '';
  email: string = '';
  password: string = '';

  // Step 2: Professional info
  anneesExperience: number | null = null;
  specialites: string = '';
  selectedFiles: File[] = [];

  errorMessage: string | null = null;

  onPersonalInfo(data: { nom: string; prenom: string; email: string; password: string }) {
    this.nom = data.nom;
    this.prenom = data.prenom;
    this.email = data.email;
    this.password = data.password;

    this.currentStep = 2; // Move to next step
  }

  onFileSelected(event: any) {
    if (event.target.files) {
      this.selectedFiles = Array.from(event.target.files);
    }
  }

  onSubmit(event: Event) {
    event.preventDefault();

    if (!this.anneesExperience || !this.specialites) {
      this.errorMessage = 'Veuillez remplir toutes les informations professionnelles.';
      return;
    }

    if (this.selectedFiles.length === 0) {
      this.errorMessage = 'Veuillez ajouter au moins un fichier PDF.';
      return;
    }

    this.errorMessage = null;

    // TODO: send data to backend (personal info + professional info + files)
    console.log({
      nom: this.nom,
      prenom: this.prenom,
      email: this.email,
      password: this.password,
      anneesExperience: this.anneesExperience,
      specialites: this.specialites,
      files: this.selectedFiles
    });
  }
}