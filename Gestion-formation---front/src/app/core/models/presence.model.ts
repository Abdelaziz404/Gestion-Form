import { User } from './user.model';

export interface Participant extends User { }

export interface Presence {
    presenceId: number;
    estPresent: boolean;
    observation: string;
    seanceId: number;
    participantId: number;
}
