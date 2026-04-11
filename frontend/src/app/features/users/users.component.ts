import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UtilisateurService } from '../../core/services/utilisateur.service';
import { ChangePasswordRequest, Utilisateur, UtilisateurRequest } from '../../core/models/models';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './users.component.html'
})
export class UsersComponent implements OnInit {
  users = signal<Utilisateur[]>([]);
  loading = signal(true);
  saving = signal(false);
  errorMsg = signal('');

  roles: Utilisateur['role'][] = ['ADMIN', 'MEDECIN', 'ASSISTANT', 'SPECIALISTE'];

  modalMode: 'create' | 'edit' = 'create';
  selectedId: number | null = null;
  showModal = signal(false);

  form: UtilisateurRequest = this.emptyForm();

  // Modal changement mot de passe (admin)
  showPwdModal = signal(false);
  savingPwd = signal(false);
  pwdErrorMsg = signal('');
  pwdSuccessMsg = signal('');
  pwdTargetUsername = '';
  pwdForm: ChangePasswordRequest & { confirm: string } = { newPassword: '', confirm: '' };

  constructor(private userService: UtilisateurService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading.set(true);
    this.userService.getAll().subscribe({
      next: u => { this.users.set(u); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  openCreate(): void {
    this.modalMode = 'create';
    this.selectedId = null;
    this.form = this.emptyForm();
    this.errorMsg.set('');
    this.showModal.set(true);
  }

  openEdit(u: Utilisateur): void {
    this.modalMode = 'edit';
    this.selectedId = u.id;
    this.form = { username: u.username, email: u.email, password: '', role: u.role, actif: u.actif };
    this.errorMsg.set('');
    this.showModal.set(true);
  }

  closeModal(): void {
    this.showModal.set(false);
  }

  save(): void {
    this.saving.set(true);
    this.errorMsg.set('');

    const obs = this.modalMode === 'create'
      ? this.userService.create(this.form)
      : this.userService.update(this.selectedId!, this.form);

    obs.subscribe({
      next: saved => {
        if (this.modalMode === 'create') {
          this.users.update(list => [...list, saved]);
        } else {
          this.users.update(list => list.map(u => u.id === saved.id ? saved : u));
        }
        this.form = this.emptyForm();
        this.saving.set(false);
        this.showModal.set(false);
      },
      error: err => {
        this.errorMsg.set(err.error?.message || 'Une erreur est survenue');
        this.saving.set(false);
      }
    });
  }

  toggle(id: number): void {
    this.userService.toggle(id).subscribe(updated =>
      this.users.update(list => list.map(u => u.id === id ? updated : u))
    );
  }

  delete(id: number): void {
    if (!confirm('Supprimer cet utilisateur ?')) return;
    this.userService.delete(id).subscribe(() =>
      this.users.update(list => list.filter(u => u.id !== id))
    );
  }

  openPwdModal(u: Utilisateur): void {
    this.pwdTargetUsername = u.username;
    this.selectedId = u.id;
    this.pwdForm = { newPassword: '', confirm: '' };
    this.pwdErrorMsg.set('');
    this.pwdSuccessMsg.set('');
    this.showPwdModal.set(true);
  }

  closePwdModal(): void {
    this.showPwdModal.set(false);
  }

  savePassword(): void {
    if (this.pwdForm.newPassword.length < 6) {
      this.pwdErrorMsg.set('Le mot de passe doit contenir au moins 6 caractères');
      return;
    }
    if (this.pwdForm.newPassword !== this.pwdForm.confirm) {
      this.pwdErrorMsg.set('Les mots de passe ne correspondent pas');
      return;
    }
    this.savingPwd.set(true);
    this.pwdErrorMsg.set('');
    this.userService.changePassword(this.selectedId!, { newPassword: this.pwdForm.newPassword }).subscribe({
      next: () => {
        this.pwdForm = { newPassword: '', confirm: '' };
        this.savingPwd.set(false);
        this.pwdSuccessMsg.set('Mot de passe modifié avec succès');
        setTimeout(() => this.showPwdModal.set(false), 1500);
      },
      error: err => {
        this.pwdErrorMsg.set(err.error?.message || 'Une erreur est survenue');
        this.savingPwd.set(false);
      }
    });
  }

  private emptyForm(): UtilisateurRequest {
    return { username: '', email: '', password: '', role: 'ASSISTANT', actif: true };
  }
}