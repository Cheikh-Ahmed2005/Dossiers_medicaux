import { Component, OnInit, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ConsultationService } from '../../../core/services/consultation.service';
import { Consultation } from '../../../core/models/models';

@Component({
  selector: 'app-consultation-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './consultation-list.component.html'
})
export class ConsultationListComponent implements OnInit {
  private consultationService = inject(ConsultationService);

  consultations = signal<Consultation[]>([]);
  loading = signal(true);

  ngOnInit(): void {
    this.consultationService.getAll().subscribe({
      next: (c) => { this.consultations.set(c); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  delete(id: number): void {
    if (!confirm('Supprimer cette consultation ?')) return;
    this.consultationService.delete(id).subscribe(() =>
      this.consultations.update(list => list.filter(c => c.id !== id))
    );
  }
}
