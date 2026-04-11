import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RendezVousService } from '../../../core/services/rendez-vous.service';
import { PatientService } from '../../../core/services/patient.service';
import { MedecinService } from '../../../core/services/medecin.service';
import { Patient, Medecin } from '../../../core/models/models';

@Component({
  selector: 'app-rdv-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './rdv-form.component.html'
})
export class RdvFormComponent implements OnInit {
  loading = signal(false);
  patients = signal<Patient[]>([]);
  medecins = signal<Medecin[]>([]);
  form: any = { patientId: null, medecinId: null, dateRdv: '', motif: '' };

  constructor(
    private rdvService: RendezVousService,
    private patientService: PatientService,
    private medecinService: MedecinService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.patientService.getAll().subscribe(p => this.patients.set(p));
    this.medecinService.getAll().subscribe(m => this.medecins.set(m));
  }

  save(): void {
    this.loading.set(true);
    this.rdvService.create(this.form).subscribe({
      next: () => this.router.navigate(['/rendez-vous']),
      error: () => this.loading.set(false)
    });
  }
}
