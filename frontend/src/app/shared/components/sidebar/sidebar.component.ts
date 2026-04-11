import { Component, computed, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './sidebar.component.html'
})
export class SidebarComponent {
  private authService = inject(AuthService);

  user = this.authService.currentUser;
  role = this.authService.role;

  isAdmin = computed(() => this.role() === 'ADMIN');
  isMedecin = computed(() => this.role() === 'MEDECIN');
  isAssistant = computed(() => this.role() === 'ASSISTANT');
  isSpecialiste = computed(() => this.role() === 'SPECIALISTE');
  isMedecinOrSpecialiste = computed(() => ['MEDECIN', 'SPECIALISTE'].includes(this.role()));

  // Modal changement de son propre mot de passe
  showChangePwdModal = signal(false);
  savingPwd = signal(false);
  pwdErrorMsg = signal('');
  pwdSuccessMsg = signal('');
  pwdForm = { currentPassword: '', newPassword: '', confirm: '' };

  logout(): void {
    this.authService.logout();
  }

  openChangePwdModal(): void {
    this.pwdForm = { currentPassword: '', newPassword: '', confirm: '' };
    this.pwdErrorMsg.set('');
    this.pwdSuccessMsg.set('');
    this.showChangePwdModal.set(true);
  }

  closeChangePwdModal(): void {
    this.showChangePwdModal.set(false);
  }

  saveOwnPassword(): void {
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
    this.authService.changeOwnPassword({
      currentPassword: this.pwdForm.currentPassword,
      newPassword: this.pwdForm.newPassword
    }).subscribe({
      next: () => {
        this.savingPwd.set(false);
        this.pwdSuccessMsg.set('Mot de passe modifié avec succès');
        setTimeout(() => this.showChangePwdModal.set(false), 1500);
      },
      error: err => {
        this.pwdErrorMsg.set(err.error?.message || 'Une erreur est survenue');
        this.savingPwd.set(false);
      }
    });
  }
}
