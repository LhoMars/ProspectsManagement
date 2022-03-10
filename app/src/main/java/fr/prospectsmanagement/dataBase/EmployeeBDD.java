package fr.prospectsmanagement.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.prospectsmanagement.Employee;
import fr.prospectsmanagement.Prospect;

public class EmployeeBDD extends ObjectBDD {

    private static final String ID_COL = "id";
    private static final int ID_COL_NUM = 0;

    private static final String IDENTIFIANT_COL = "identifiant";
    private static final int IDENTIFIANT_COL_NUM = 1;

    private static final String PASSWORD_COL = "password";
    private static final int PASSWORD_COL_NUM = 2;


    public EmployeeBDD(DaoSQL maBaseSQLite) {
        super(maBaseSQLite, "employee",
                "CREATE TABLE employee (" +
                        ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        IDENTIFIANT_COL + " TEXT, " +
                        PASSWORD_COL + " TEXT); ");
    }

    public long addemployeeBdd(Employee e) {
        open();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(IDENTIFIANT_COL, e.getIdentifiant());
        values.put(PASSWORD_COL, e.getPassword());
        //on insère l'objet dans la BDD via le ContentValues
        return getBdd().insert(getTableName(), null, values);
    }
    /*
    public int updateLivre(int id, Livre livre){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_ISBN, livre.getIsbn());
        values.put(COL_TITRE, livre.getTitre());
        return bdd.update(TABLE_LIVRES, values, COL_ID + " = " +id, null);
    }

    public int removeELivreWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_LIVRES, COL_ID + " = " +id, null);
    }*/

    public Employee getEmployeeWithIdentifiant(String identifiant) {
        open();
        Cursor c = getBdd().query(getTableName(), new String[]{IDENTIFIANT_COL, PASSWORD_COL}, IDENTIFIANT_COL + " = '" + identifiant+ "'", null, null, null, null);
        return cursorToEmployee(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Employee cursorToEmployee(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c == null || c.getCount() == 0) {
            System.out.println("pas d'employe");
            return null;
        }
        
        //Sinon on se place sur le premier élément
        c.moveToFirst();

        //On créé un employee
        Employee e = new Employee();
        e.setIdentifiant(c.getString(0));
        e.setPassword(c.getString(1));

        //On ferme le cursor
        c.close();
        close();
        //On retourne l'employee
        return e;
    }
}


