import { Component, OnInit, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { PatientService } from '../../../core/services/patient.service';
import { ConsultationService } from '../../../core/services/consultation.service';
import { Patient, Consultation } from '../../../core/models/models';

@Component({
  selector: 'app-patient-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './patient-detail.component.html'
})
export class PatientDetailComponent implements OnInit {
  private patientService = inject(PatientService);
  private consultationService = inject(ConsultationService);
  private route = inject(ActivatedRoute);

  patient = signal<Patient | null>(null);
  consultations = signal<Consultation[]>([]);

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.patientService.getById(id).subscribe(p => this.patient.set(p));
    this.consultationService.getByPatient(id).subscribe(c => this.consultations.set(c));
  }
}
