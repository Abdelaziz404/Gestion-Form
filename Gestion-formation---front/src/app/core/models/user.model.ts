import { Role } from "../../shared/utils/Enum/role";


export interface User {
    personId: number;
    nom: string;
    prenom: string;
    password: string;
    email: string;
    telephone?: string;
    role: Role;
    imageUrl?: string;
}
