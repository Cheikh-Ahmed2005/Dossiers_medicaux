import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ChangePasswordRequest, Utilisateur, UtilisateurRequest } from '../models/models';

@Injectable({ providedIn: 'root' })
export class UtilisateurService {
  private readonly API = 'http://localhost:8080/api/utilisateurs';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Utilisateur[]> { return this.http.get<Utilisateur[]>(this.API); }
  getById(id: number): Observable<Utilisateur> { return this.http.get<Utilisateur>(`${this.API}/${id}`); }
  create(request: UtilisateurRequest): Observable<Utilisateur> { return this.http.post<Utilisateur>(this.API, request); }
  update(id: number, request: UtilisateurRequest): Observable<Utilisateur> { return this.http.put<Utilisateur>(`${this.API}/${id}`, request); }
  toggle(id: number): Observable<Utilisateur> { return this.http.patch<Utilisateur>(`${this.API}/${id}/toggle`, {}); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.API}/${id}`); }
  changePassword(id: number, request: ChangePasswordRequest): Observable<void> {
    return this.http.patch<void>(`${this.API}/${id}/password`, request);
  }
}