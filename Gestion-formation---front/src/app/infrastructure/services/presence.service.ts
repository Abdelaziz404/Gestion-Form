import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { MOCK_PARTICIPANTS } from '../mock/mock-data';
import { User } from '../../core/models/user.model';
import { Presence } from '../../core/models/presence.model';

@Injectable({
    providedIn: 'root'
})
export class PresenceService {
    private presences: Presence[] = [];

    constructor() { }

    getParticipantsBySeance(seanceId: number): Observable<User[]> {
        return of(MOCK_PARTICIPANTS);
    }

    recordPresence(presence: Presence): void {
        const index = this.presences.findIndex(p => p.seanceId === presence.seanceId && p.participantId === presence.participantId);
        if (index > -1) {
            this.presences[index] = presence;
        } else {
            this.presences.push(presence);
        }
    }

    getAttendanceRate(participantId: number, formationId: number, totalSeances: number): number {
        const presentCount = this.presences.filter(p => p.participantId === participantId && p.estPresent).length;
        return (presentCount / totalSeances) * 100;
    }
}
