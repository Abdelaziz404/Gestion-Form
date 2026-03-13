import { Injectable } from '@angular/core';
import { CandidatureService } from 'src/app/core/services/candidatureService/candidature.service';
import { CandidatureRequest } from '../core/dto/Candidature/CandidatureRequest';
import { NameValidator } from '../shared/utils/Validators/NameValidator';
import { EmailValidator } from '../shared/utils/Validators/EmailValidator';
import { PhoneValidator } from '../shared/utils/Validators/PhoneValidator';
import { PasswordValidator } from '../shared/utils/Validators/PasswordValidator';
import { StatutInscription } from '../shared/utils/Enum/StatusInscription';

@Injectable({
  providedIn: 'root'
})
export class CandidatureController {

  constructor(private readonly candidatureService: CandidatureService) {}

  // ----------------- AJOUTER CANDIDATURE -----------------
  ajouterCandidature(
    request: CandidatureRequest & { telephone?: string; password?: string; files?: File[] },
    onSuccess: () => void,
    onError: (message: string) => void
  ): void {

    // --------- VALIDATIONS ----------
    if (!NameValidator.validateName(request.nom)) return onError("Nom invalide");
    if (!NameValidator.validateName(request.prenom)) return onError("Prénom invalide");
    if (!EmailValidator.validateEmail(request.email)) return onError("Email invalide");
    if (request.telephone && !PhoneValidator.validatePhone(request.telephone)) return onError("Téléphone invalide (8 chiffres requis)");
    if (request.password && !PasswordValidator.validatePassword(request.password)) return onError("Mot de passe invalide (8 caractères, majuscule, minuscule, chiffre, caractère spécial)");
    if (!request.anneesExperience || request.anneesExperience < 0) return onError("Années d'expérience obligatoires et positives");
    if (!request.specialites || request.specialites.trim().length === 0) return onError("Spécialités obligatoires");
    if (!request.files || request.files.length === 0) return onError("Veuillez ajouter au moins un fichier PDF");

    // --------- VERIFIER SI EMAIL EXISTE ----------
    this.candidatureService.verifierEmail(request.email).subscribe({
      next: (res: { exists: boolean }) => {
        if (res.exists) {
          onError("Cet email est déjà utilisé !");
          return;
        }

        // --------- ENVoyer la candidature ----------
        const formData = new FormData();
        const { files, ...candidatureData } = request;

        // Ajouter le DTO JSON
        formData.append('candidature', new Blob([JSON.stringify(candidatureData)], { type: 'application/json' }));

        // Ajouter les fichiers
        files?.forEach(file => formData.append('files', file, file.name));

        this.candidatureService.ajouterCandidature(formData).subscribe({
          next: () => onSuccess(),
          error: (err) => {
            console.error("Erreur backend détaillée:", err);
            let errorMsg = "Erreur lors de l'envoi de la candidature";
            
            if (err?.name === 'HttpErrorResponse' && err.status === 0) {
                errorMsg = "Erreur Réseau / CORS : Le serveur Spring Boot est-il en cours d'exécution ?";
            } else if (err?.error) {
              if (typeof err.error === 'string') {
                errorMsg = err.error;
              } else if (err.error.message) {
                errorMsg = err.error.message;
              } else if (typeof err.error === 'object') {
                const fields = Object.values(err.error).filter(val => typeof val === 'string');
                if (fields.length > 0) {
                  errorMsg = fields.join(' | ');
                } else {
                  errorMsg = "Erreur de validation: " + JSON.stringify(err.error);
                }
              }
            }
            onError(errorMsg);
          }
        });
      },
      error: (err) => onError(err?.error?.message || "Impossible de vérifier l'email")
    });
  }

  // ----------------- FETCH CANDIDATURES ET FICHIERS -----------------
  getCandidatures(onSuccess: (data: any) => void, onError: (msg: string) => void) {
    this.candidatureService.getCandidatures().subscribe({
      next: onSuccess,
      error: err => onError(err?.error?.message || "Erreur chargement candidatures")
    });
  }

  getFiles(id: number, onSuccess: (files: string[]) => void, onError: (msg: string) => void) {
    this.candidatureService.getCandidatureFiles(id).subscribe({
      next: onSuccess,
      error: err => onError(err?.error?.message || "Erreur chargement fichiers")
    });
  }

  // ----------------- CHANGER STATUT -----------------
  changerStatut(
    id: number,
    status: StatutInscription,
    onSuccess: () => void,
    onError: (message: string) => void
  ): void {
    if (!id) return onError("ID de candidature requis");

    if (status === StatutInscription.ACCEPTER) {
      this.candidatureService.accepterCandidature(id).subscribe({
        next: onSuccess,
        error: err => onError(typeof err.error === 'string' ? err.error : (err?.error?.message || "Erreur lors de l'acceptation"))
      });
    } else {
      this.candidatureService.changerStatus(id, status).subscribe({
        next: onSuccess,
        error: err => onError(typeof err.error === 'string' ? err.error : (err?.error?.message || "Erreur lors du changement de statut"))
      });
    }
  }

  // ----------------- SUPPRIMER CANDIDATURE -----------------
  supprimerCandidature(
    id: number,
    onSuccess: () => void,
    onError: (message: string) => void
  ): void {
    if (!id) return onError("ID de candidature requis");

    this.candidatureService.supprimerCandidature(id).subscribe({
      next: () => onSuccess(),
      error: (err) => onError(err?.error?.message || "Erreur lors de la suppression")
    });
  }
}