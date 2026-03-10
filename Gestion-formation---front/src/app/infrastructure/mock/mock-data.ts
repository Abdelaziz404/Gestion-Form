import { Formation } from '../../core/models/formation.model';
import { Seance } from '../../core/models/seance.model';
import { User } from '../../core/models/user.model';
import { Role } from '../../shared/utils/role';
import { Presence } from '../../core/models/presence.model';

export const MOCK_FORMATEUR: User = {
    personId: 1,
    nom: 'Dupont',
    prenom: 'Jean',
    email: 'jean.dupont@cni.tn',
    telephone: '+216 22 111 222',
    role: Role.FORMATEUR
};

export const MOCK_FORMATIONS: Formation[] = [
    {
        formationId: 101,
        titre: 'Angular Advanced Patterns',
        description: 'Master clean architecture and performance in Angular.',
        prix: 500,
        duree: 30,
        formateur: MOCK_FORMATEUR
    },
    {
        formationId: 102,
        titre: 'Deep Learning with Python',
        description: 'Practical course on neural networks and computer vision.',
        prix: 750,
        duree: 40,
        formateur: MOCK_FORMATEUR
    }
];

export const MOCK_SEANCES: Seance[] = [
    { seanceId: 1, formationId: 101, date: '2026-03-05', heureDebut: '09:00', salle: 'Room 101' },
    { seanceId: 2, formationId: 101, date: '2026-03-07', heureDebut: '09:00', salle: 'Room 101' },
    { seanceId: 3, formationId: 102, date: '2026-03-06', heureDebut: '14:00', salle: 'Room 203' }
];

export const MOCK_PARTICIPANTS: User[] = [
    { personId: 10, nom: 'Zied', prenom: 'Ben Ali', email: 'zied@gmail.com', telephone: '123456', role: Role.PARTICIPANT },
    { personId: 11, nom: 'Sarra', prenom: 'Trabelsi', email: 'sarra@gmail.com', telephone: '123456', role: Role.PARTICIPANT }
];
