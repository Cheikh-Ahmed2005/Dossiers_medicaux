import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { PatientService } from '../../../core/services/patient.service';
import { Patient } from '../../../core/models/models';

@Component({
  selector: 'app-patient-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './patient-form.component.html'
})
export class PatientFormComponent implements OnInit {
  isEdit = false;
  loadingData = signal(false);
  saving = signal(false);
  error = signal('');

  patient: Partial<Patient> = {
    nom: '', prenom: '', sexe: 'M',
    telephone: '', adresse: '', groupeSanguin: '', allergies: ''
  };

  constructor(
    private patientService: PatientService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id && id !== 'new') {
      this.isEdit = true;
      this.loadingData.set(true);
      this.patientService.getById(+id).subscribe({
        next: (p) => {
          this.patient = {
            id: p.id,
            nom: p.nom,
            prenom: p.prenom,
            dateNaissance: p.dateNaissance,
            sexe: p.sexe,
            telephone: p.telephone,
            adresse: p.adresse,
            groupeSanguin: p.groupeSanguin,
            allergies: p.allergies
          };
          this.loadingData.set(false);
        },
        error: () => {
          this.error.set('Impossible de charger les données du patient.');
          this.loadingData.set(false);
        }
      });
    }
  }

  save(): void {
    this.saving.set(true);
    this.error.set('');
    const action = this.isEdit
      ? this.patientService.update(this.patient.id!, this.patient)
      : this.patientService.create(this.patient);

    action.subscribe({
      next: () => this.router.navigate(['/patients']),
      error: () => { this.saving.set(false); this.error.set('Erreur lors de la sauvegarde.'); }
    });
  }
}