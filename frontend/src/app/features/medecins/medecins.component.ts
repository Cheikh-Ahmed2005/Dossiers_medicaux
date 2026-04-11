import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MedecinService } from '../../core/services/medecin.service';
import { ServiceHospitalierService } from '../../core/services/service-hospitalier.service';
import { Medecin, ServiceHospitalier } from '../../core/models/models';

@Component({
  selector: 'app-medecins',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './medecins.component.html'
})
export class MedecinsComponent implements OnInit {
  medecins = signal<Medecin[]>([]);
  services = signal<ServiceHospitalier[]>([]);
  loading = signal(true);
  showForm = signal(false);
  editId: number | null = null;
  form: any = { nom: '', prenom: '', specialite: '', serviceId: null };

  constructor(
    private medecinService: MedecinService,
    private serviceService: ServiceHospitalierService
  ) {}

  ngOnInit(): void {
    this.load();
    this.serviceService.getAll().subscribe(s => this.services.set(s));
  }

  load(): void {
    this.loading.set(true);
    this.medecinService.getAll().subscribe({ next: m => { this.medecins.set(m); this.loading.set(false); }, error: () => this.loading.set(false) });
  }

  openForm(m?: Medecin): void {
    this.editId = m?.id ?? null;
    this.form = { nom: m?.nom ?? '', prenom: m?.prenom ?? '', specialite: m?.specialite ?? '', serviceId: m?.service?.id ?? null };
    this.showForm.set(true);
  }

  save(): void {
    const action = this.editId
      ? this.medecinService.update(this.editId, this.form)
      : this.medecinService.create(this.form);
    action.subscribe(() => {
      this.form = { nom: '', prenom: '', specialite: '', serviceId: null };
      this.editId = null;
      this.showForm.set(false);
      this.load();
    });
  }

  delete(id: number): void {
    if (!confirm('Supprimer ce médecin ?')) return;
    this.medecinService.delete(id).subscribe(() => this.load());
  }
}
