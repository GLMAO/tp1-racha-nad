[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/t19xNtmg)


## IASD 25

#   TP1 — Gestion de Temps avec TimerService (Java)

##   Objectif du TP

L’objectif de ce TP est de **concevoir un système de gestion du temps modulaire en Java**, basé sur le modèle **Observer (pattern Observateur)**.  
Le but est d’avoir un **service de minuterie générique (`TimerService`)** capable de notifier automatiquement les composants qui en dépendent, tels qu’une **horloge** ou un **compte à rebours**.

---

## 🧩 Structure du Projet

Le projet est organisé en plusieurs modules :

###  `timer-service`
Contient les **interfaces de base** :
- `TimerService` : définit les méthodes d’accès au temps (`heures`, `minutes`, `secondes`, `dixièmes`).
- `TimeChangeProvider` : interface pour la gestion des observateurs.
- `TimeChangeListener` : interface à implémenter par tout composant souhaitant être notifié lors d’un changement de temps.

---

### ⚙️ `time-service-impl`
Contient **l’implémentation du service** :
- `TimeServiceImpl` : gère la mise à jour du temps toutes les 100 ms (un dixième de seconde).  
  Il notifie automatiquement tous les observateurs enregistrés.

---

### 🖥️ `timer-service-client`
Contient les **classes clientes observatrices du service** :
- `Horloge` : affiche l’heure courante au format `HH:MM:SS`.
- `CompteARebours` : effectue un décompte à partir d’une valeur initiale jusqu’à zéro.
- `HorlogeFrame` : interface graphique basée sur Swing, qui affiche l’heure en temps réel sous forme d’une fenêtre avec un grand texte central mis à jour automatiquement.
---

###  `launcher`
Point d’entrée du programme :
- Crée et relie le `TimerService` aux différents observateurs (`Horloge`, `CompteARebours`, `HorlogeFrame`).
- Permet de tester le bon fonctionnement du système en affichant les résultats dans la console.

---

##  Principe de Fonctionnement

Le système repose sur le **design pattern Observer** :

- Le `TimeServiceImpl` agit comme **fournisseur d’événements** (provider).  
- Les classes `Horloge` et `CompteARebours` jouent le rôle d’**observateurs** (listeners).

À chaque mise à jour du temps :
1. Le service met à jour ses valeurs (dixièmes, secondes, minutes, heures),
2. Il appelle la méthode `timeChanged()` de chaque observateur inscrit.

Ce mécanisme permet de **découpler la logique de calcul du temps de la logique d’affichage** ou de traitement.

---

##   Étapes de Réalisation

### 🔹 Étape A — Création des interfaces
- Définition de `TimerService`, `TimeChangeProvider` et `TimeChangeListener` dans le module `timer-service`.

### 🔹 Étape B — Implémentation du TimerService
- Création de la classe `TimerServiceImpl` dans `time-service-impl`.
- Utilisation d’un `Timer` Java pour mettre à jour le temps toutes les 100 ms.
- Gestion des débordements :  
  `60 dixièmes → 1 seconde`, `60 secondes → 1 minute`, `60 minutes → 1 heure`.
- Notification automatique des observateurs enregistrés.

### 🔹 Étape C — Création des clients
- `Horloge` : affiche l’heure mise à jour à chaque tick.  
- `CompteARebours` : diminue la valeur jusqu’à 0 et se désinscrit automatiquement.
- **HorlogeFrame** *(nouvelle classe)* : interface graphique utilisant `JFrame` et `JLabel` pour afficher l’heure en temps réel.

### 🔹 Étape D — Test et Exécution

#### Exemple dans `App.java` :

```java
public static void main(String[] args) throws InterruptedException {
    TimerService timer = new DummyTimeServiceImpl();

    // Horloges texte
    Horloge h1 = new Horloge("H1");
    h1.setTimerService(timer);

    Horloge h2 = new Horloge("H2");
    h2.setTimerService(timer);

    // Interface graphique
    new HorlogeFrame(timer);

    // Compte à rebours de 5 secondes
    new CompteARebours(timer, 5, "C5");

    // 10 comptes aléatoires entre 10 et 20 secondes
    Random rnd = new Random();
    for (int i = 0; i < 10; i++) {
        int v = 10 + rnd.nextInt(11);
        new CompteARebours(timer, v, "C" + i);
    }

    Thread.sleep(60000);
    System.out.println("Fin du test launcher.");
}
 ``` 
---
##   Résultats obtenus

- Le système affiche correctement le temps sous forme d’**horloge**.
- Le **compte à rebours** fonctionne indépendamment et s’arrête à zéro.
- L’architecture respecte les principes de **modularité** et de **réutilisabilité**.
- Chaque module peut être amélioré ou remplacé sans impacter les autres.
- Une interface graphique a été ajoutée avec la classe **HorlogeFrame** dans le module `timer-service-client`, permettant d’afficher l’heure en temps réel dans une fenêtre Swing.  
  Cette fenêtre se met à jour automatiquement via le service `TimerService`.

  ###  Exemple d’exécution

```java
public static void main(String[] args) {
    TimerService timer = new TimerServiceImpl();
    HorlogeFrame frame = new HorlogeFrame(timer);
    frame.setVisible(true);
}
 ``` 
 ---
##  Conclusion

Ce TP a permis de :

- comprendre la mise en œuvre du **Design Pattern Observer** en Java,  
- concevoir un système **extensible** et **faiblement couplé**,  
- manipuler les événements à travers **PropertyChangeListener**,  
- et illustrer concrètement le fonctionnement de ce pattern à travers :
  - une **Horloge** (affichage du temps en continu),
  - un **Compte à rebours** (décrémentation jusqu’à zéro),
  - et une **HorlogeFrame** (interface graphique Swing affichant le temps en temps réel).

Grâce à cette architecture modulaire et bien structurée, le projet peut facilement évoluer :  
il est possible d’ajouter de nouvelles fonctionnalités (pause/reprise, alarmes, timers multiples, etc.) sans modifier la logique principale du service.
