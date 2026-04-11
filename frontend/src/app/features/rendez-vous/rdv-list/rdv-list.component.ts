import { Component, OnInit, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { RendezVousService } from '../../../core/services/rendez-vous.service';
import { RendezVous } from '../../../core/models/models';

@Component({
  selector: 'app-rdv-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './rdv-list.component.html'
})
export class RdvListComponent implements OnInit {
  private rdvService = inject(RendezVousService);

  rdvList = signal<RendezVous[]>([]);
  loading = signal(true);

  statutColors: Record<string, string> = {
    PLANIFIE: 'bg-warning text-dark',
    CONFIRME: 'bg-success',
    ANNULE: 'bg-danger',
    TERMINE: 'bg-secondary'
  };

  ngOnInit(): void {
    this.rdvService.getAll().subscribe({
      next: r => { this.rdvList.set(r); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  updateStatut(id: number, statut: string): void {
    this.rdvService.updateStatut(id, statut).subscribe(updated =>
      this.rdvList.update(list => list.map(r => r.id === id ? updated : r))
    );
  }

  delete(id: number): void {
    if (!confirm('Supprimer ce rendez-vous ?')) return;
    this.rdvService.delete(id).subscribe(() =>
      this.rdvList.update(list => list.filter(r => r.id !== id))
    );
  }
}
