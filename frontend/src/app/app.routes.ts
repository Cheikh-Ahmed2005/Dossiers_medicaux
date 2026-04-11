import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { guestGuard } from './core/guards/guest.guard';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  {
    path: 'login',
    canActivate: [guestGuard],
    loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: '',
    canActivate: [authGuard],
    loadComponent: () => import('./shared/components/layout/layout.component').then(m => m.LayoutComponent),
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      {
        path: 'dashboard',
        loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent)
      },
      {
        path: 'patients',
        loadComponent: () => import('./features/patients/patient-list/patient-list.component').then(m => m.PatientListComponent)
      },
      {
        path: 'patients/new',
        loadComponent: () => import('./features/patients/patient-form/patient-form.component').then(m => m.PatientFormComponent)
      },
      {
        path: 'patients/:id',
        loadComponent: () => import('./features/patients/patient-detail/patient-detail.component').then(m => m.PatientDetailComponent)
      },
      {
        path: 'patients/:id/edit',
        loadComponent: () => import('./features/patients/patient-form/patient-form.component').then(m => m.PatientFormComponent)
      },
      {
        path: 'consultations',
        canActivate: [roleGuard], data: { roles: ['MEDECIN', 'SPECIALISTE', 'ADMIN'] },
        loadComponent: () => import('./features/consultations/consultation-list/consultation-list.component').then(m => m.ConsultationListComponent)
      },
      {
        path: 'consultations/new',
        canActivate: [roleGuard], data: { roles: ['MEDECIN', 'SPECIALISTE', 'ADMIN'] },
        loadComponent: () => import('./features/consultations/consultation-form/consultation-form.component').then(m => m.ConsultationFormComponent)
      },
      {
        path: 'consultations/:id/edit',
        canActivate: [roleGuard], data: { roles: ['MEDECIN', 'SPECIALISTE', 'ADMIN'] },
        loadComponent: () => import('./features/consultations/consultation-form/consultation-form.component').then(m => m.ConsultationFormComponent)
      },
      {
        path: 'prescriptions',
        canActivate: [roleGuard], data: { roles: ['MEDECIN', 'ADMIN'] },
        loadComponent: () => import('./features/prescriptions/prescriptions.component').then(m => m.PrescriptionsComponent)
      },
      {
        path: 'rendez-vous',
        loadComponent: () => import('./features/rendez-vous/rdv-list/rdv-list.component').then(m => m.RdvListComponent)
      },
      {
        path: 'rendez-vous/new',
        loadComponent: () => import('./features/rendez-vous/rdv-form/rdv-form.component').then(m => m.RdvFormComponent)
      },
      {
        path: 'medecins',
        canActivate: [roleGuard], data: { roles: ['ADMIN'] },
        loadComponent: () => import('./features/medecins/medecins.component').then(m => m.MedecinsComponent)
      },
      {
        path: 'services',
        canActivate: [roleGuard], data: { roles: ['ADMIN'] },
        loadComponent: () => import('./features/services/services.component').then(m => m.ServicesComponent)
      },
      {
        path: 'users',
        canActivate: [roleGuard], data: { roles: ['ADMIN'] },
        loadComponent: () => import('./features/users/users.component').then(m => m.UsersComponent)
      },
    ]
  },
  { path: '**', redirectTo: 'login' }
];
