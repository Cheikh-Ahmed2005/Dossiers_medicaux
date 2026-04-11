export interface Utilisateur {
  id: number;
  username: string;
  email: string;
  role: 'ADMIN' | 'MEDECIN' | 'ASSISTANT' | 'SPECIALISTE';
  actif: boolean;
  createdAt?: string;
}

export interface UtilisateurRequest {
  username: string;
  email: string;
  password?: string;
  role: 'ADMIN' | 'MEDECIN' | 'ASSISTANT' | 'SPECIALISTE';
  actif: boolean;
}

export interface ChangePasswordRequest {
  newPassword: string;
}

export interface ChangeOwnPasswordRequest {
  currentPassword: string;
  newPassword: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  type: string;
  id: number;
  username: string;
  email: string;
  role: string;
}

export interface Patient {
  id: number;
  nom: string;
  prenom: string;
  dateNaissance?: string;
  sexe?: string;
  telephone?: string;
  adresse?: string;
  groupeSanguin?: string;
  allergies?: string;
  createdAt?: string;
}

export interface ServiceHospitalier {
  id: number;
  nomService: string;
  description?: string;
}

export interface Medecin {
  id: number;
  nom: string;
  prenom: string;
  specialite?: string;
  service?: ServiceHospitalier;
  utilisateur?: Utilisateur;
}

export interface Assistant {
  id: number;
  nom: string;
  prenom: string;
  service?: ServiceHospitalier;
}

export interface Consultation {
  id: number;
  patient: Patient;
  medecin: Medecin;
  dateConsultation: string;
  diagnostic?: string;
  traitement?: string;
  notes?: string;
  prescriptions?: Prescription[];
}

export interface Prescription {
  id: number;
  consultation?: Consultation;
  medicament: string;
  dosage?: string;
  duree?: string;
  instructions?: string;
}

export interface RendezVous {
  id: number;
  patient: Patient;
  medecin: Medecin;
  dateRdv: string;
  statut: 'PLANIFIE' | 'CONFIRME' | 'ANNULE' | 'TERMINE';
  motif?: string;
}

export interface DashboardStats {
  totalPatients: number;
  totalMedecins: number;
  totalConsultations: number;
  totalRendezVous: number;
  totalUtilisateurs: number;
  totalServices: number;
}
