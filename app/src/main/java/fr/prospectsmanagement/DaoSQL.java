package fr.prospectsmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;
import java.io.File;


public class DaoSQL {

    private static final String DB_NAME = "prospectmanagbd.db";
    private static final int FLAGS = 0;
    private static final String PASSWORD_BDD = "Passw@rdBdd777";

    private static final String TABLE_NAME = "prospect";

    private static final String ID_COL = "id";
    private static final int ID_COL_NUM = 0;

    private static final String NOM_COL = "nom";
    private static final int NOM_COL_NUM = 1;

    private static final String PRENOM_COL = "prenom";
    private static final int PRENOM_COL_NUM = 2;

    private static final String TEL_COL = "tel";
    private static final int BESTSCORE_COL_NUM = 3;

    private static final String DATE_COL = "mail";
    private static final int DATE_COL_NUM = 4;

    private static final String DATE_COL = "note";
    private static final int DATE_COL_NUM = 5;

    private SQLiteDatabase database;
    private File databaseFile;


    public DaoSQL(Context c) {
        SQLiteDatabase.loadLibs(c);
        this.databaseFile = c.getDatabasePath(DB_NAME);
        /*System.out.println("    TEST : " +databaseFile.exists());*/
        if (!databaseFile.exists()) {
            database = SQLiteDatabase.openOrCreateDatabase(databaseFile, PASSWORD_BDD, null);
            database.execSQL(createTableUser());
        } else {
            database = SQLiteDatabase.openDatabase(databaseFile.getPath(), PASSWORD_BDD, null, 0);
        }
    }

    public void open(){
        if(!database.isOpen()){
            database = SQLiteDatabase.openDatabase(databaseFile.getPath(), PASSWORD_BDD, null, 0);
        }
    }


    public String createTableUser() {
        return "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOM_COL + " TEXT NOT NULL, "
                + PRENOM_COL + " TEXT NOT NULL, "
                + TEL_COL + " INTEGER DEFAULT 0, "
                + DATE_COL + " TEXT)";
    }

    public void addUserBdd(String nom, String prenom) {
        open();
        ContentValues values = new ContentValues();

        // on defini les informations à inscérer en Bdd
        values.put(NOM_COL, nom);
        values.put(PRENOM_COL, prenom);

        // after adding all values we are passing
        // content values to our table.
        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    public User getUserBdd(String nom, String prenom) {
        open();
        Cursor c = database.query(true,
                TABLE_NAME,                        /**< Nom de la table */
                null,                             /**< les data souhaité.*/
                "LOWER("+NOM_COL + ")=LOWER(?) AND LOWER(" + PRENOM_COL + ")=LOWER(?)", /**< clause WHERE. */
                new String[]{nom, prenom},    /**< argument de selection */
                null, null, null, null);
        return cursorToUser(c);
    }

    public void updateUser(User user) {
        ContentValues values = new ContentValues();
        String sql = "UPDATE " + TABLE_NAME + " SET " + TEL_COL + " = ?, " + DATE_COL + " = ?" +
                " WHERE LOWER(" + NOM_COL + ") = LOWER(?) AND LOWER(" + PRENOM_COL + ") = LOWER(?)";
        SQLiteStatement stmt = database.compileStatement(sql);

        stmt.bindLong(1, user.getBestScore());
        stmt.bindString(2, user.getDateScore());
        stmt.bindString(3, user.getNom());
        stmt.bindString(4, user.getPrenom());

        stmt.executeInsert();
        database.close();

        //database.update(TABLE_NAME,values,NOM_COL + " = "+ user.getNom() + " AND " + PRENOM_COL + " = ",null,null,null);
    }

    private User cursorToUser(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un User
        User user = new User();

        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        user.setId(c.getInt(ID_COL_NUM));
        user.setNom(c.getString(NOM_COL_NUM));
        user.setPrenom(c.getString(PRENOM_COL_NUM));
        user.setBestScore(c.getInt(BESTSCORE_COL_NUM));
        user.setDateScore(c.getString(DATE_COL_NUM));
        //On ferme le cursor
        c.close();
        database.close();

        //On retourne le User
        return user;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }
}
