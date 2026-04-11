import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Medecin } from '../models/models';

@Injectable({ providedIn: 'root' })
export class MedecinService {
  private readonly API = 'http://localhost:8080/api/medecins';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Medecin[]> { return this.http.get<Medecin[]>(this.API); }
  getById(id: number): Observable<Medecin> { return this.http.get<Medecin>(`${this.API}/${id}`); }
  create(m: any): Observable<Medecin> { return this.http.post<Medecin>(this.API, m); }
  update(id: number, m: any): Observable<Medecin> { return this.http.put<Medecin>(`${this.API}/${id}`, m); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.API}/${id}`); }
}
