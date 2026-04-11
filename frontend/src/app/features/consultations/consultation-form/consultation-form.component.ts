import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ConsultationService } from '../../../core/services/consultation.service';
import { PatientService } from '../../../core/services/patient.service';
import { MedecinService } from '../../../core/services/medecin.service';
import { Patient, Medecin } from '../../../core/models/models';

@Component({
  selector: 'app-consultation-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './consultation-form.component.html'
})
export class ConsultationFormComponent implements OnInit {
  isEdit = false;
  loading = signal(false);
  patients = signal<Patient[]>([]);
  medecins = signal<Medecin[]>([]);

  form: any = { patientId: null, medecinId: null, diagnostic: '', traitement: '', notes: '' };

  constructor(
    private consultationService: ConsultationService,
    private patientService: PatientService,
    private medecinService: MedecinService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.patientService.getAll().subscribe(p => this.patients.set(p));
    this.medecinService.getAll().subscribe(m => this.medecins.set(m));

    const id = this.route.snapshot.paramMap.get('id');
    if (id && id !== 'new') {
      this.isEdit = true;
      this.consultationService.getById(+id).subscribe(c => {
        this.form = {
          id: c.id, patientId: c.patient?.id, medecinId: c.medecin?.id,
          diagnostic: c.diagnostic, traitement: c.traitement, notes: c.notes
        };
      });
    }
  }

  save(): void {
    this.loading.set(true);
    const action = this.isEdit
      ? this.consultationService.update(this.form.id, this.form)
      : this.consultationService.create(this.form);
    action.subscribe({ next: () => this.router.navigate(['/consultations']), error: () => this.loading.set(false) });
  }
}
