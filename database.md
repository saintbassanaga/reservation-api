# Bases de Données pour le Système de Réservation de Vols

## 1. Base de données relationnelle (SQL)
Cette base de données sera utilisée pour stocker les informations structurées, telles que les utilisateurs, les réservations, les paiements et les vols.

**Tables principales :**
- **Utilisateurs (Users)** : Stocke les informations des utilisateurs (nom, email, mot de passe, etc.).
- **Réservations (Reservations)** : Contient les informations sur les réservations de vols (id réservation, utilisateur, vol, statut de réservation, date).
- **Vols (Flights)** : Stocke les détails des vols disponibles (id vol, origine, destination, date départ, date retour, etc.).
- **Paiements (Payments)** : Contient les informations des transactions de paiement (id paiement, utilisateur, montant, statut, méthode de paiement).
- **Annulations (Cancellations)** : Gère les annulations de réservations (id annulation, réservation annulée, date de l'annulation).

**Technologie recommandée :**
- **PostgreSQL** ou **MySQL** : Ces bases de données relationnelles sont adaptées pour la gestion des données structurées liées aux utilisateurs, réservations et paiements.

## 2. Base de données NoSQL (optionnelle)
Une base de données NoSQL peut être utilisée pour stocker des informations moins structurées ou temporaires. Cela peut être utile pour gérer les sessions utilisateur, les logs ou les caches.

**Exemples d'utilisation :**
- **Sessions** : Si vous souhaitez stocker temporairement des informations sur les sessions des utilisateurs (par exemple, un utilisateur en cours de réservation avant de finaliser le paiement).
- **Cache** : Pour mettre en cache les résultats des recherches de vols fréquentes ou des données temporairement utilisées afin de réduire la charge sur la base de données principale.
- **Logs** : Pour stocker les logs d'activité du système.

**Technologie recommandée :**
- **Redis** : Idéal pour gérer les sessions et les caches en mémoire.
- **MongoDB** : Peut être utilisé pour stocker des documents JSON ou des informations plus souples et non relationnelles.

## 3. Base de données pour les paiements
Si vous souhaitez séparer la gestion des paiements des autres données sensibles et utiliser un service de traitement des paiements comme PayPal ou Visa, vous pourriez avoir besoin d'une base de données dédiée pour stocker uniquement les transactions, en respectant les normes PCI DSS (si vous gérez directement les informations de paiement).

**Exemples de tables :**
- **Transactions** : Stocke les informations sur chaque paiement (id paiement, montant, date, statut, méthode de paiement).
- **Logs de paiement** : Garde une trace des erreurs, succès ou échecs de chaque transaction pour un audit.

**Technologie recommandée :**
- **PostgreSQL/MySQL** : Pour gérer les informations de transaction liées aux paiements.
- **Stripe ou PayPal API** : Si vous n'enregistrez pas directement les informations bancaires, ces services s'occupent du traitement et de la gestion des paiements.

## 4. Base de données d'administration (optionnelle)
Une base de données ou une table d'administration peut être utilisée pour gérer les données et activités liées à l'administration du système. Cela pourrait inclure des informations comme les logs d'administration, les rôles d'utilisateurs, et la gestion des configurations.

**Exemples d'utilisation :**
- **Logs d'administration** : Trace les actions des administrateurs du système.
- **Rôles et permissions** : Gère les différents niveaux d'accès (administrateur, utilisateur, etc.).
- **Configuration du système** : Gère les paramètres du système tels que les configurations API.

## Résumé des bases de données nécessaires :

1. **Base de données relationnelle SQL (PostgreSQL / MySQL)** :
    - Utilisateurs
    - Réservations
    - Vols
    - Paiements
    - Annulations

2. **Base de données NoSQL (Redis / MongoDB)** (facultatif) :
    - Sessions utilisateur
    - Cache des données fréquentes
    - Logs (optionnel)

3. **Base de données pour paiements (PostgreSQL / MySQL)** (facultatif si paiement géré en interne) :
    - Transactions de paiement

4. **Base de données d'administration (optionnelle)** :
    - Logs d'administration
    - Rôles et permissions
    - Configuration du système
