package fr.prospectsmanagement.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import fr.prospectsmanagement.dataBase.ProspectBDD;


public class DaoSQL extends SQLiteOpenHelper {

    private static final String DB_NAME = "prospectmanagbd.db";
    private static final int DB_VERSION = 1;
    private ProspectBDD prospectBdd;

    public DaoSQL(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        prospectBdd = new ProspectBDD(this);
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

    public ProspectBDD getProspectBdd() {
        return prospectBdd;
    }
}
