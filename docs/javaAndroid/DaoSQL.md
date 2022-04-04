# DAO SQL

## Introduction
Cette classe hérite de `SQLiteOpenHelper`. Elle fournit la création des tables et l'accès aux différentes classes pour utiliser la base de données.  
La classe `ProspectBDD` permet de communiquer avec les prospects et `EmployeeBDD` avec les employee dans le fichier db. Ces deux classes héritent de ObjectBDD.

### ObjectBDD
```java

/**
 * Cette classe fournit les attributs et méthodes
 * pour toutes les tables de la bdd
 */
public abstract class ObjectBDD {

    private String name;
    private String createSql;
    private DaoSQL maBaseSQLite;
    private SQLiteDatabase bdd;
```