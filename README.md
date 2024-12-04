# Système de Réservation de Vols avec Intégration Amadeus

Ce projet permet de développer un système de réservation de vols en ligne intégré à l'API Amadeus. Il offre une interface pour la recherche de vols, la gestion des réservations, les annulations, et le traitement des paiements sécurisés via PayPal et Visa. L'architecture du système repose sur une approche microservices, garantissant une grande scalabilité et une maintenance aisée.

## Table des matières

- [Description du projet](#description-du-projet)
- [Fonctionnalités](#fonctionnalités)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Installation](#installation)
- [Utilisation](#utilisation)
- [API](#api)
- [Contributeurs](#contributeurs)
- [License](#license)

## Description du projet

Le système de réservation permet aux utilisateurs de rechercher des vols, de réserver des billets, de gérer les annulations et de traiter les paiements en ligne de manière sécurisée. Il s'intègre à l'API **Amadeus** pour la recherche de vols et utilise des services de paiement tiers comme **PayPal** et **Visa** pour sécuriser les transactions.

L'architecture microservices permet une évolutivité flexible, chaque service gérant une fonctionnalité spécifique (recherche de vol, réservation, paiement, etc.). Ce projet est conçu pour offrir une expérience utilisateur fluide, sécurisée et évolutive.

## Fonctionnalités

- **Recherche de Vols** : Recherche de vols en fonction des critères (date, destination, nombre de passagers).
- **Réservation** : Création et gestion des réservations de vols.
- **Annulation et Remboursement** : Annulation de réservations et gestion des remboursements.
- **Paiement sécurisé** : Intégration avec PayPal et Visa pour le traitement des paiements en ligne.
- **Gestion des utilisateurs** : Authentification et gestion des profils utilisateurs.
- **Administration** : Gestion des vols, réservations et utilisateurs via un dashboard administrateur.

## Technologies

### Backend
- **Spring Boot** : Framework Java pour la création d'APIs RESTful et microservices.
- **Spring Security** : Pour gérer l'authentification et la sécurité des sessions.
- **Spring Data JPA** : Pour interagir avec la base de données relationnelle.
- **Spring Cloud (optionnel)** : Pour une gestion d'architecture microservices distribuée.

### Frontend
- **React.js / Angular / Flutter** : Interface utilisateur moderne et réactive.
- **Tailwind CSS** : Framework CSS pour des interfaces stylisées et faciles à maintenir.

### API
- **Amadeus API** : Utilisé pour la recherche et l'affichage des informations sur les vols.
- **PayPal API** : Traitement des paiements en ligne.
- **Visa API** : Option de paiement via carte bancaire.

### Bases de données
- **PostgreSQL / MySQL** : Base de données relationnelle pour stocker les informations des utilisateurs, réservations, paiements, etc.
- **MongoDB / Redis** : Base de données NoSQL pour les sessions et données temporaires.

### Authentification
- **OAuth 2.0** : Gestion sécurisée des sessions utilisateur via JWT.

### Système de notification
- **SendGrid / Twilio** : Envoi de notifications par email/SMS (confirmation de réservation, alertes).

## Architecture

L'architecture du système est basée sur une approche **microservices**, chaque service étant responsable d'une fonctionnalité spécifique. Ces services communiquent entre eux via des APIs RESTful. Les principaux composants sont :

- **Frontend** : Interface utilisateur avec React.js ou Flutter.
- **API Gateway** : Point d'entrée pour toutes les requêtes externes.
- **Microservices** : Services séparés pour la gestion des vols, des réservations, des paiements et des utilisateurs.
- **Base de données** : PostgreSQL/MySQL pour les données structurées et MongoDB/Redis pour les données temporaires.
- **Amadeus API** : Intégration pour la recherche de vols.
- **Service de paiement** : Intégration de PayPal et Visa pour le traitement sécurisé des paiements.

## Installation

### Prérequis
- **JDK 11 ou supérieur**
- **Maven** : Gestionnaire de dépendances et de build pour Spring Boot.
- **PostgreSQL ou MySQL** : Base de données relationnelle.
- **MongoDB ou Redis** : Pour la gestion des sessions et du cache.
- **Clés API Amadeus, PayPal et Visa**

### Étapes d'installation

1. Clonez le repository :
    ```bash
    git clone https://github.com/votre-compte/flight-booking-system.git
    cd flight-booking-system
    ```

2. Installez les dépendances avec Maven :
    ```bash
    cd backend
    mvn install
    ```

3. Configurez les variables d'environnement pour les clés API (Amadeus, PayPal, Visa) dans le fichier `application.properties` ou `application.yml`.

4. Lancez l'application Spring Boot :
    ```bash
    mvn spring-boot:run
    ```

5. Accédez à l'application dans votre navigateur à l'adresse `http://localhost:8080`.

6. Pour le frontend (React.js par exemple), installez les dépendances et démarrez le serveur :
    ```bash
    cd frontend
    npm install
    npm start
    ```

7. Accédez au frontend via `http://localhost:3000`.

## Utilisation

Une fois l'application installée et lancée, vous pouvez commencer à utiliser les fonctionnalités suivantes :

- **Recherche de Vols** : Saisissez vos critères de recherche (origine, destination, dates, nombre de passagers) pour afficher la liste des vols disponibles.
- **Réservation** : Sélectionnez un vol, entrez vos informations et réservez votre billet.
- **Paiement** : Effectuez un paiement via PayPal ou Visa pour finaliser votre réservation.
- **Annulation et Remboursement** : Gérez vos réservations via votre compte utilisateur.

## API

### Endpoints

#### 1. Recherche de Vols
- **GET /flights/search** : Recherche de vols
  - **Paramètres** : `origin`, `destination`, `departureDate`, `returnDate`, `passengerCount`
  - **Réponse** : Liste des vols disponibles.

#### 2. Réservation de Vol
- **POST /reservations** : Créer une réservation
  - **Paramètres** : `userID`, `flightID`, `seatClass`, `passengerDetails`
  - **Réponse** : Confirmation de réservation.

#### 3. Paiement
- **POST /payments** : Traiter un paiement
  - **Paramètres** : `paymentDetails`, `userID`, `reservationID`
  - **Réponse** : Statut du paiement.

#### 4. Annulation
- **DELETE /reservations/{reservationID}** : Annuler une réservation
  - **Réponse** : Confirmation de l'annulation.

## Contributeurs

- [Saint Paul](https://github.com/saintbassanaga) – Développeur principal
- [Collaborateurs](https://github.com/orgs/votre-compte/people)

## License

Ce projet est sous licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.
