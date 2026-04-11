import { Component, OnInit, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PatientService } from '../../../core/services/patient.service';
import { AuthService } from '../../../core/services/auth.service';
import { Patient } from '../../../core/models/models';

@Component({
  selector: 'app-patient-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './patient-list.component.html'
})
export class PatientListComponent implements OnInit {
  private patientService = inject(PatientService);
  private authService = inject(AuthService);

  patients = signal<Patient[]>([]);
  loading = signal(true);
  searchQuery = '';
  role = this.authService.role;

  ngOnInit(): void { this.load(); }

  load(): void {
    this.loading.set(true);
    this.patientService.getAll().subscribe({
      next: (p) => { this.patients.set(p); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  search(): void {
    if (!this.searchQuery.trim()) { this.load(); return; }
    this.patientService.search(this.searchQuery).subscribe(p => this.patients.set(p));
  }

  delete(id: number): void {
    if (!confirm('Supprimer ce patient ?')) return;
    this.patientService.delete(id).subscribe(() => this.load());
  }

  canDelete(): boolean { return ['ADMIN', 'MEDECIN'].includes(this.role()); }
  canCreate(): boolean { return ['ADMIN', 'ASSISTANT', 'MEDECIN'].includes(this.role()); }
}
