import { Role } from "../../shared/utils/role";


export interface User {
    personId: number;
    nom: string;
    prenom: string;
    email: string;
    telephone: string;
    role: Role;
}
