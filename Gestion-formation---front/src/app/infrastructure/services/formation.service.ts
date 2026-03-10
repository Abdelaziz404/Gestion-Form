import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Formation } from '../../core/models/formation.model';
import { Seance } from '../../core/models/seance.model';
import { MOCK_FORMATIONS, MOCK_SEANCES } from '../mock/mock-data';

@Injectable({
    providedIn: 'root'
})
export class FormationService {
    constructor() { }

    getAssignedFormations(formateurId: number): Observable<Formation[]> {
        // In a real app, we'd filter by formateurId
        return of(MOCK_FORMATIONS);
    }

    getFormationDetails(formationId: number): Observable<Formation | undefined> {
        return of(MOCK_FORMATIONS.find(f => f.formationId === formationId));
    }

    getSeancesByFormation(formationId: number): Observable<Seance[]> {
        return of(MOCK_SEANCES.filter(s => s.formationId === formationId));
    }

    createFormation(formation: Formation): Observable<boolean> {
        MOCK_FORMATIONS.push(formation);
        return of(true);
    }

    createSeance(seance: Seance): Observable<boolean> {
        MOCK_SEANCES.push(seance);
        return of(true);
    }
}
