import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ServiceHospitalier } from '../models/models';

@Injectable({ providedIn: 'root' })
export class ServiceHospitalierService {
  private readonly API = 'http://localhost:8080/api/services';

  constructor(private http: HttpClient) {}

  getAll(): Observable<ServiceHospitalier[]> { return this.http.get<ServiceHospitalier[]>(this.API); }
  create(s: any): Observable<ServiceHospitalier> { return this.http.post<ServiceHospitalier>(this.API, s); }
  update(id: number, s: any): Observable<ServiceHospitalier> { return this.http.put<ServiceHospitalier>(`${this.API}/${id}`, s); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.API}/${id}`); }
}
