# Prospects Management

## PAGE DE SERVICE

**^^Mises à jour^^**

|Version|Date       |Auteur         |Description du changement                                          |
|-------|-----------|---------------|-------------------------------------------------------------------|
| 1.0.0 |01-04-2022 |Marcel Lhote   |création                                                           |
|||||

**^^Diffusion^^**

|Version |Date       |Nom          |Rôle|
|--------|-----------|-------------|----|
|1.0.0   |03-04-2022 |Marcel Lhote |SLAM|
|||||

**^^Plan de classement :^^** SLAM4 ; Java ; Android ;  
**^^Niveau de confidentialité :^^** confidentiel

---

## Introduction

L'application à pour objectif d'ajouter des prospects. Lors de rencontres dans des salons ou autres vous serez amener à
enregistrer des contacts pour votre entreprise.  
Prospect Management intègre les fonctionnalités d'ajout de prospect et de synchronisation avec le serveur distant.

### Architecture

L'application intègre sa propre base de données SQLite pour enregistrer les prospects mêmes ors connexion.  
L'utilisateur peut Synchroniser avec le serveur lorsque ce dernier est accessible par le biais d'une requête api.

![Architecture](./assets/architectureBdd.png)

### Cas d'utilisation

Ce diagramme présente les actuels fonctionnalité pur l'application

```mermaid
graph TB
rencontre(rencontre un prospect)
identifie(s'identifie)
ajoutepr(ajoute un prospect)
sync(syncroniser les données)
serveurBdd[(Serveur distant)]

Commercial:::user --> rencontre
    classDef user fill:#0acf00;
    
identifie -- <<include>> --> rencontre
rencontre -- <<extend>> --> ajoutepr
rencontre -- <<extend>> --> sync
serveurBdd <--> sync
```

Lors d'une rencontre avec un prospect, le commercial doit se connecter pour accéder à son application.  
Ensuite, il pourra enregistrer l'enregistrer et synchroniser ses données avec le serveur.

Dans le cas où le prospect existerait déjà, le serveur ne le rajoutera pas.
