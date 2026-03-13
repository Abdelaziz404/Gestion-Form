import { StatutInscription } from "src/app/shared/utils/Enum/StatusInscription";

export interface CandidatureDetailsResponse {
    id: number;
    nom: string;
    prenom: string;
    email: string;
    anneesExperience: number;
    specialites: string;
    status: StatutInscription;
    files: File[];
}