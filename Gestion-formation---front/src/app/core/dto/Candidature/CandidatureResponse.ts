import { StatutInscription } from "src/app/shared/utils/Enum/StatusInscription";

export interface CandidatureResponse {

  id: number;
  nom: string;
  prenom: string;
  email: string;
  anneesExperience: number;
  specialites: string;
  status: string;

  files?: string[];

}