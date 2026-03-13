import { Component, OnInit } from '@angular/core';
import { CandidatureController } from 'src/app/controller/CandidatureController';
import { CandidatureResponse } from 'src/app/core/dto/Candidature/CandidatureResponse';
import { StatutInscription } from 'src/app/shared/utils/Enum/StatusInscription';

@Component({
  selector: 'app-candidatures',
  templateUrl: './candidatures.component.html',
  styleUrls: ['./candidatures.component.scss']
})
export class CandidaturesComponent implements OnInit {

  candidatures: CandidatureResponse[] = [];
  selectedCandidature?: CandidatureResponse;
  loading = false;
  errorMessage = '';
  successMessage = '';

  StatutInscription = StatutInscription;

  constructor(private candidatureController: CandidatureController) {}

  ngOnInit(): void {
    this.loadCandidatures();
  }

  loadCandidatures() {

    this.loading = true;

    this.candidatureController.getCandidatures(
      (data) => {
        this.candidatures = data;
        this.loading = false;
      },
      (err) => {
        this.errorMessage = err;
        this.loading = false;
      }
    );
  }

selectCandidature(c: CandidatureResponse) {

  this.selectedCandidature = c;

  if (c.files && c.files.length > 0) {
    return;
  }

  this.candidatureController.getFiles(
    c.id,
    (files) => {
      this.selectedCandidature!.files = files;
    },
    (err) => {
      this.errorMessage = err;
    }
  );

}
  updateStatus(c: CandidatureResponse, status: StatutInscription) {

    this.candidatureController.changerStatut(
      c.id,
      status,
      () => {
        c.status = status;
        this.errorMessage = '';
        this.successMessage = "Statut mis à jour avec succès.";
        setTimeout(() => this.successMessage = '', 5000);
      },
      (err) => {
        this.successMessage = '';
        this.errorMessage = err;
      }
    );

  }

  isPdf(url: string): boolean {
    if (!url) return false;
    return url.toLowerCase().includes('.pdf');
  }

  isImage(url: string): boolean {
    if (!url) return false;
    const lowerUrl = url.toLowerCase();
    return lowerUrl.includes('.jpg') || lowerUrl.includes('.jpeg') || lowerUrl.includes('.png') || lowerUrl.includes('.webp');
  }

}