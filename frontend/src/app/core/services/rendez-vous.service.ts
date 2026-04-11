import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RendezVous } from '../models/models';

@Injectable({ providedIn: 'root' })
export class RendezVousService {
  private readonly API = 'http://localhost:8080/api/rendez-vous';

  constructor(private http: HttpClient) {}

  getAll(): Observable<RendezVous[]> { return this.http.get<RendezVous[]>(this.API); }
  getByPatient(id: number): Observable<RendezVous[]> { return this.http.get<RendezVous[]>(`${this.API}/patient/${id}`); }
  getByMedecin(id: number): Observable<RendezVous[]> { return this.http.get<RendezVous[]>(`${this.API}/medecin/${id}`); }
  create(r: any): Observable<RendezVous> { return this.http.post<RendezVous>(this.API, r); }
  updateStatut(id: number, statut: string): Observable<RendezVous> {
    return this.http.patch<RendezVous>(`${this.API}/${id}/statut?statut=${statut}`, {});
  }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.API}/${id}`); }
}
