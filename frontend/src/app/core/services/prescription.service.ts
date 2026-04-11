import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Prescription } from '../models/models';

@Injectable({ providedIn: 'root' })
export class PrescriptionService {
  private readonly API = 'http://localhost:8080/api/prescriptions';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Prescription[]> { return this.http.get<Prescription[]>(this.API); }
  getByConsultation(consultationId: number): Observable<Prescription[]> { return this.http.get<Prescription[]>(`${this.API}/consultation/${consultationId}`); }
  create(p: any): Observable<Prescription> { return this.http.post<Prescription>(this.API, p); }
  update(id: number, p: any): Observable<Prescription> { return this.http.put<Prescription>(`${this.API}/${id}`, p); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.API}/${id}`); }
}