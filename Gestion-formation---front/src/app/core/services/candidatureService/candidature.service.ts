import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from 'src/app/shared/utils/constants';
import { CandidatureRequest } from '../../dto/Candidature/CandidatureRequest';
import { CandidatureResponse } from '../../dto/Candidature/CandidatureResponse';

@Injectable({
  providedIn: 'root'
})
export class CandidatureService {

  private apiUrl = `${API_BASE_URL}/api/v1/candidatures`;

  constructor(private http: HttpClient) {}

  ajouterCandidature(data: FormData): Observable<string> {
    return this.http.post<string>(this.apiUrl, data, { responseType: 'text' as 'json' });
  }

  getCandidatures(): Observable<CandidatureResponse[]> {
    return this.http.get<CandidatureResponse[]>(this.apiUrl);
  }

  supprimerCandidature(id: number): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/${id}`);
  }

  accepterCandidature(id: number): Observable<string> {
    return this.http.put<string>(`${this.apiUrl}/${id}/accepter`, {}, { responseType: 'text' as 'json' });
  }

  changerStatus(id: number, status: string): Observable<string> {
    return this.http.put<string>(`${this.apiUrl}/${id}/status?status=${status}`, {}, { responseType: 'text' as 'json' });
  }

  // ----------------- VERIFICATION EMAIL -----------------
  verifierEmail(email: string): Observable<{ exists: boolean }> {
    return this.http.get<{ exists: boolean }>(`${this.apiUrl}/check-email?email=${email}`);
  }

    getCandidatureFiles(id: number): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/${id}/files`);
  }
}