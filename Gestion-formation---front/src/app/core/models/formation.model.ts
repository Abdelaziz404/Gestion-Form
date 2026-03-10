import { User } from './user.model';

export interface Formation {
    formationId: number;
    titre: string;
    description: string;
    prix: number;
    duree: number;
    formateur?: User;
}
