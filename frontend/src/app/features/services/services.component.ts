import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ServiceHospitalierService } from '../../core/services/service-hospitalier.service';
import { ServiceHospitalier } from '../../core/models/models';

@Component({
  selector: 'app-services',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './services.component.html'
})
export class ServicesComponent implements OnInit {
  services = signal<ServiceHospitalier[]>([]);
  loading = signal(true);
  showForm = signal(false);
  editId: number | null = null;
  form = { nomService: '', description: '' };

  constructor(private serviceService: ServiceHospitalierService) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.loading.set(true);
    this.serviceService.getAll().subscribe({ next: s => { this.services.set(s); this.loading.set(false); }, error: () => this.loading.set(false) });
  }

  openForm(s?: ServiceHospitalier): void {
    this.editId = s?.id ?? null;
    this.form = { nomService: s?.nomService ?? '', description: s?.description ?? '' };
    this.showForm.set(true);
  }

  save(): void {
    const action = this.editId
      ? this.serviceService.update(this.editId, this.form)
      : this.serviceService.create(this.form);
    action.subscribe(() => {
      this.form = { nomService: '', description: '' };
      this.editId = null;
      this.showForm.set(false);
      this.load();
    });
  }

  delete(id: number): void {
    if (!confirm('Supprimer ce service ?')) return;
    this.serviceService.delete(id).subscribe(() => this.load());
  }
}
