import { Component } from '@angular/core';
import { CandidatureController } from 'src/app/controller/CandidatureController';

@Component({
  selector: 'app-envoyer-candidature',
  templateUrl: './envoyer-candidature.component.html',
  styleUrls: ['./envoyer-candidature.component.scss']
})
export class EnvoyerCandidatureComponent {
  currentStep = 1;

  nom = '';
  prenom = '';
  email = '';
  anneesExperience: number | null = null;
  specialites = '';
  selectedFiles: File[] = [];

  errorMessage: string | null = null;

  constructor(private controller: CandidatureController) {}

  onNextStep(event: Event) {
    event.preventDefault();
    this.errorMessage = null;
    this.currentStep = 2;
  }

  prevStep() { this.currentStep = 1; }

  onFileSelected(event: any) {
    if (event.target.files) {
      this.selectedFiles.push(...Array.from(event.target.files) as File[]);
    }
  }

  removeFile(index: number) { this.selectedFiles.splice(index, 1); }

  onSubmit(event: Event) {
    event.preventDefault();

const request = {
  nom: this.nom,
  prenom: this.prenom,
  email: this.email,
  anneesExperience: this.anneesExperience!, // le ! dit à TS que ce n'est pas null
  specialites: this.specialites,
  files: this.selectedFiles
};
    this.controller.ajouterCandidature(
      request,
      () => alert("Candidature envoyée avec succès !"),
      msg => this.errorMessage = msg
    );
  }
}