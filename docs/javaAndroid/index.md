# Introduction

## Diagramme de séquence
Ce diagramme présente la procédure d'enregistrement d'un prospect dans la base de données SQLite

``` mermaid
sequenceDiagram
actor Commercial

  Commercial ->>+ Activity Login: saisit de l'identifiant / mot de passe  
  Activity Login ->> DAO: getEmployeeWithIdentifiant(lIdentifiant)  
  DAO -) Employee: create new Employee(identifiant, motDePasse)  
  Employee -->> DAO: lEmployee
  DAO -->> Activity Login: lEmploye
  Activity Login ->> Employee: checkPassword(motDePasse)
  Employee -->> Activity Login: true
  Activity Login ->> Activity Login : new Intent
  Activity Login -->>- Commercial: startActivity(MenuActivity)
```
