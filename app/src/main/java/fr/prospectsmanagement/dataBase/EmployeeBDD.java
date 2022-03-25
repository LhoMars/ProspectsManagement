package fr.prospectsmanagement.dataBase;

import android.content.ContentValues;
import android.database.Cursor;
import fr.prospectsmanagement.Employee;

/**
 * Cette classe permet d'accéder à la table employee de la bdd
 */
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

    /**
     * Ajoute un employe à la bdd
     *
     * @param e Employee : l'employee à ajouter
     * @return long : l'id
     */
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

    /**
     * Récupère l'employee avec son nom
     *
     * @param identifiant String : le nom de l'employe
     * @return Employee ou null
     */
    public Employee getEmployeeWithIdentifiant(String identifiant) {
        open();
        Cursor c = getBdd().query(getTableName(), new String[]{IDENTIFIANT_COL, PASSWORD_COL}, IDENTIFIANT_COL + " = '" + identifiant + "'", null, null, null, null);
        if (c == null || c.getCount() == 0 || c.getCount() > 1) {
            return null;
        }

        c.moveToFirst();
        Employee lEmploye = cursorToEmployee(c);
        c.close();
        close();
        return lEmploye;
    }

    //Cette méthode permet de convertir un cursor en un employee
    private Employee cursorToEmployee(Cursor c) {
        /* On créé un employee
        et on donne ses paramètres */

        Employee e = new Employee();
        e.setIdentifiant(c.getString(0));
        e.setPassword(c.getString(1));

        //On retourne l'employee
        return e;
    }
}


