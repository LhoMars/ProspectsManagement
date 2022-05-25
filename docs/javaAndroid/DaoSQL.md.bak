# DAO SQL

## Introduction
Cette classe hérite de `SQLiteOpenHelper`. Elle fournit la création des tables et l'accès aux différentes classes pour utiliser la base de données.  
La classe `ProspectBDD` permet de communiquer avec les prospects et `EmployeeBDD` avec les employee dans le fichier db. Ces deux classes héritent de ObjectBDD.

### ObjectBDD
Cette classe fournit les méthodes `open()` et `close` pour accéder en écriture à la base de données et refermer sa connexion 
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

### EmployeeBDD
Cette classe permet de gérer les employées dans le fichier de base de données.  
Cette méthode utilise une requête imbriquée pour évier les injections sql au niveau du `where identifiant = monIdentifiant`.  
L'objet curseur permet de transformer le résultat d'une requête sql en variable utilisable tel que `String, int, boolean...`

```java

    /**
     * Récupère l'employee avec son nom
     *
     * @param identifiant String : le nom de l'employe
     * @return Employee ou null
     */
    public Employee getEmployeeWithIdentifiant(String identifiant) {
        open();

        String[] params = new String[(identifiant != null ? 1 : 0)];
        int paramIndex = 0;
        String where = "";
        if (identifiant != null) {
            where = IDENTIFIANT_COL + " = ?";
            params[paramIndex++] = identifiant;
        }
        
        Cursor c = getBdd().query(getTableName(), new String[]{IDENTIFIANT_COL, PASSWORD_COL}, where, params, null, null, null);
        if (c == null || c.getCount() == 0) {
            return null;
        }

        c.moveToFirst();
        Employee lEmploye = cursorToEmployee(c);
        c.close();
        close();
        return lEmploye;
    }
```

### ProspectBDD
Pour gérer les prospects cette classe fournit une méthode pour récupérer une liste de prospect avec différents critères non obligatoires.

```java
/**
     * Récupère un tableau de prospects
     *
     * @param nom          String ou null : le nom du prospect
     * @param prenom       String ou null : le prenom du prospect
     * @param raisonSocial String ou null : la raison social de l'entreprise
     * @return ArrayLsit<Prospect> ou null : la liste des prorpects correspondant aux critère ou null s'il n'existe aucun prospect correspondant
     */
    public ArrayList<Prospect> getProspect(String nom, String prenom, String raisonSocial) {...}
```

Pour l'ajout d'un prospect il est nécessaire d'utiliser la classe `ContentValues` pour insérer des données.
Par défaut le résultat renvoyé est l'identifiant de la nouvelle insertion.

```java
/**
     * Ajoute un prospect à la bdd
     *
     * @param p Prospect : le prospect à ajouter
     * @return long : l'id du prospect
     */
    public long addProspectBdd(Prospect p) {
        open();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(NOM_COL, p.getNom());
        values.put(PRENOM_COL, p.getPrenom());
        values.put(TEL_COL, p.getTel());
        values.put(MAIL_COL, p.getMail());
        values.put(NOTES_COL, p.getNotes());
        values.put(SIRET_COL, p.getSiret());
        values.put(RAISON_SOCIAL_COL, p.getRaisonSocial());
        //on insère l'objet dans la BDD via le ContentValues
        return getBdd().insert(getTableName(), null, values);
    }
```