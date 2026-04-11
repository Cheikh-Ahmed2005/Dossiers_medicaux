import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Consultation } from '../models/models';

@Injectable({ providedIn: 'root' })
export class ConsultationService {
  private readonly API = 'http://localhost:8080/api/consultations';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Consultation[]> { return this.http.get<Consultation[]>(this.API); }
  getById(id: number): Observable<Consultation> { return this.http.get<Consultation>(`${this.API}/${id}`); }
  getByPatient(patientId: number): Observable<Consultation[]> { return this.http.get<Consultation[]>(`${this.API}/patient/${patientId}`); }
  getByMedecin(medecinId: number): Observable<Consultation[]> { return this.http.get<Consultation[]>(`${this.API}/medecin/${medecinId}`); }
  create(c: any): Observable<Consultation> { return this.http.post<Consultation>(this.API, c); }
  update(id: number, c: any): Observable<Consultation> { return this.http.put<Consultation>(`${this.API}/${id}`, c); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.API}/${id}`); }
}
