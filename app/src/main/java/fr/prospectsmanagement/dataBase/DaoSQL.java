package fr.prospectsmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import fr.prospectsmanagement.dataBase.ProspectBDD;


public class DaoSQL extends SQLiteOpenHelper {

    private static final String DB_NAME = "prospectmanagbd.db";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase bdd;
    private ProspectBDD prospectBdd;

    public DaoSQL(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        prospectBdd = new ProspectBDD();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(prospectBdd.getCreateBdd());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + prospectBdd.getTableName() + ";");
        onCreate(db);
    }
    public void open(){
        //on ouvre la BDD en écriture
        bdd = this.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public long addProspectBdd(Prospect p) {
        open();
        //Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(NOM_COL, p.getNom());
		values.put(PRENOM_COL, p.getPrenom());
        values.put(TEL_COL,p.getTel());
        values.put(MAIL_COL, p.getMail());
        values.put(NOTES_COL, p.getNotes());
		//on insère l'objet dans la BDD via le ContentValues
		return bdd.insert(TABLE_NAME, null, values);
	}
/*
    public Prospect getProspectBdd(String nom, String prenom) {
        Cursor c = database.query(true,
                TABLE_NAME,                        // Nom de la table
                null,                             // les data souhaité.
                        "LOWER("+NOM_COL +")=LOWER(?) AND LOWER("+PRENOM_COL +")=LOWER(?)", // clause WHERE.
            new String[]

    {
        nom, prenom
    },    // argument de selection
            null,null,null,null);
        return

    cursorToUser(c);
}

    public void updatePrsopect(Prospect p) {
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
    }*/
}
