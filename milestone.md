# Milestones

## Phase 1 : Analyse et préparation
- **Durée estimée** : 2 semaines
- **Livrables** :
    1. **Documentation complète des besoins** :
        - Identification des besoins fonctionnels et non fonctionnels.
        - Analyse des exigences des utilisateurs et des parties prenantes.
        - Élaboration d'un document détaillant les cas d'utilisation, les flux de travail et les priorités fonctionnelles.
        - Collecte des informations sur les API tierces nécessaires (Amadeus, PayPal, etc.).
        - Définition des objectifs de performance et de sécurité.
    2. **Architecture technique validée** :
        - Conception de l'architecture technique et des choix technologiques.
        - Élaboration de l'architecture API.
        - Validation avec les parties prenantes.
    3. **Configuration de l'environnement de développement** :
        - Mise en place des bases de données, de l'API Gateway, et des intégrations tierces.
        - Configuration des outils de développement et du système de gestion de version.
        - Préparation de l'environnement pour les développeurs.

## Phase 2 : Développement des fonctionnalités principales
- **Durée estimée** : 6 semaines
- **Livrables** :
    1. **Recherche des vols** : Endpoint `/flights` fonctionnel avec connexion à l'API Amadeus.
    2. **Réservation des billets** : Endpoint `/reservations` avec logique de validation et stockage.
    3. **Paiement sécurisé** : Endpoint `/payments` intégrant PayPal et Visa.
    4. **Annulation** : Endpoint `/reservations/{id}` avec gestion des politiques d'annulation.

## Phase 3 : Intégration des notifications
- **Durée estimée** : 2 semaines
- **Livrables** :
    1. Notifications configurées pour SMS (Twilio) et emails (AWS SES).
    2. Workflow pour envoi automatique après réservation, paiement, ou annulation.

## Phase 4 : Sécurité et monitoring
- **Durée estimée** : 2 semaines
- **Livrables** :
    1. Mise en place de OAuth 2.0 avec Auth0 ou un service équivalent.
    2. Intégration de Prometheus et Grafana pour la surveillance en temps réel.
    3. Tests de sécurité pour garantir la conformité aux normes PCI DSS.

## Phase 5 : Tests et lancement
- **Durée estimée** : 4 semaines
- **Livrables** :
    1. Tests unitaires et d'intégration complétés à 100 %.
    2. Tests de charge pour vérifier la capacité du système à gérer des pics de trafic.
    3. Déploiement progressif sur un environnement de production (canary deployment).
    4. Documentation utilisateur et technique finalisée.
