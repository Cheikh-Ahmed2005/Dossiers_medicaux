
    -- Seed data for pi_s3 database
-- BCrypt hash for password "password" (tous les utilisateurs)

-- Services hospitaliers
INSERT IGNORE INTO services (id, nom_service, description) VALUES
(1, 'Cardiologie', 'Service des maladies cardiovasculaires'),
(2, 'Radiologie', 'Service d''imagerie médicale'),
(3, 'Pédiatrie', 'Service des soins pour enfants'),
(4, 'Laboratoire', 'Service des analyses biologiques'),
(5, 'Urgences', 'Service des urgences médicales'),
(6, 'Chirurgie', 'Service des interventions chirurgicales'),
(7, 'Neurologie', 'Service des maladies du système nerveux');

-- Utilisateurs (password: password)
INSERT IGNORE INTO utilisateurs (id, username, email, password, role, actif) VALUES
(1, 'admin', 'admin@medical.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN', true),
(2, 'dr.martin', 'martin@medical.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'MEDECIN', true),
(3, 'assistant1', 'assistant@medical.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ASSISTANT', true),
(4, 'dr.dupont', 'dupont@medical.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'SPECIALISTE', true),
(5, 'admin2', 'admin2@medical.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN', true),
(7, 'admin3', 'admin3@medical.com', '$2a$10$1kuEbaW7/rV935udkWYxMucJpAT6V77tGJwS81sYUYnDtrF.vv.P6', 'ADMIN', true);

-- Médecins
INSERT IGNORE INTO medecins (id, nom, prenom, specialite, service_id, utilisateur_id) VALUES
(1, 'Martin', 'Pierre', 'Cardiologie', 1, 2);

-- Spécialistes
INSERT IGNORE INTO specialistes (id, nom, prenom, specialite, service_id, utilisateur_id) VALUES
(1, 'Dupont', 'Sophie', 'Neurologie', 7, 4);

-- Assistants
INSERT IGNORE INTO assistants (id, nom, prenom, service_id, utilisateur_id) VALUES
(1, 'Bernard', 'Marie', 1, 3);

-- Patients
INSERT IGNORE INTO patients (id, nom, prenom, date_naissance, sexe, telephone, adresse, groupe_sanguin) VALUES
(1, 'Tremblay', 'Jean', '1985-03-15', 'M', '0612345678', '12 rue de la Paix, Paris', 'A+'),
(2, 'Leblanc', 'Marie', '1990-07-22', 'F', '0623456789', '45 avenue Victor Hugo, Lyon', 'O-'),
(3, 'Moreau', 'Paul', '1978-11-10', 'M', '0634567890', '8 boulevard Haussmann, Paris', 'B+'),
(4, 'Simon', 'Claire', '2000-01-05', 'F', '0645678901', '23 rue de la République, Marseille', 'AB+');
