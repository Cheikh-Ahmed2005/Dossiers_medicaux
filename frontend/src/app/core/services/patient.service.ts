import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Patient } from '../models/models';

@Injectable({ providedIn: 'root' })
export class PatientService {
  private readonly API = 'http://localhost:8080/api/patients';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Patient[]> { return this.http.get<Patient[]>(this.API); }
  getById(id: number): Observable<Patient> { return this.http.get<Patient>(`${this.API}/${id}`); }
  search(q: string): Observable<Patient[]> { return this.http.get<Patient[]>(`${this.API}/search?q=${q}`); }
  create(p: Partial<Patient>): Observable<Patient> { return this.http.post<Patient>(this.API, p); }
  update(id: number, p: Partial<Patient>): Observable<Patient> { return this.http.put<Patient>(`${this.API}/${id}`, p); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.API}/${id}`); }
}
