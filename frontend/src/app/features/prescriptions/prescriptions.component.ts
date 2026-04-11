import { Component, OnInit, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrescriptionService } from '../../core/services/prescription.service';
import { ConsultationService } from '../../core/services/consultation.service';
import { Prescription, Consultation } from '../../core/models/models';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-prescriptions',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './prescriptions.component.html'
})
export class PrescriptionsComponent implements OnInit {
  private prescriptionService = inject(PrescriptionService);
  private consultationService = inject(ConsultationService);

  prescriptions = signal<Prescription[]>([]);
  consultations = signal<Consultation[]>([]);
  loading = signal(true);
  showForm = signal(false);

  form = { consultationId: null as number | null, medicament: '', dosage: '', duree: '', instructions: '' };
  editingId: number | null = null;

  ngOnInit(): void {
    this.load();
    this.consultationService.getAll().subscribe(c => this.consultations.set(c));
  }

  load(): void {
    this.loading.set(true);
    this.prescriptionService.getAll().subscribe({
      next: (p) => { this.prescriptions.set(p); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  openNew(): void {
    this.editingId = null;
    this.form = { consultationId: null, medicament: '', dosage: '', duree: '', instructions: '' };
    this.showForm.set(true);
  }

  openEdit(p: Prescription): void {
    this.editingId = p.id;
    this.form = {
      consultationId: p.consultation?.id ?? null,
      medicament: p.medicament,
      dosage: p.dosage ?? '',
      duree: p.duree ?? '',
      instructions: p.instructions ?? ''
    };
    this.showForm.set(true);
  }

  save(): void {
    if (!this.form.consultationId || !this.form.medicament) return;
    const payload = { ...this.form, consultationId: this.form.consultationId };
    const obs = this.editingId
      ? this.prescriptionService.update(this.editingId, payload)
      : this.prescriptionService.create(payload);
    obs.subscribe(() => {
      this.form = { consultationId: null, medicament: '', dosage: '', duree: '', instructions: '' };
      this.editingId = null;
      this.showForm.set(false);
      this.load();
    });
  }

  delete(id: number): void {
    if (!confirm('Supprimer cette prescription ?')) return;
    this.prescriptionService.delete(id).subscribe(() =>
      this.prescriptions.update(list => list.filter(p => p.id !== id))
    );
  }
}