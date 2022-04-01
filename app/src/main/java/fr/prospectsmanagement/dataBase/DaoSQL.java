package fr.prospectsmanagement.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * La classe DAO permet la lecture/écriture de la base de données
 * et de sa configuration en case de création ou mise à jour
 */
public class DaoSQL extends SQLiteOpenHelper {

    private static final String DB_NAME = "prospectmanagbd.db";
    private static final int DB_VERSION = 9;
    private ProspectBDD prospectBdd;
    private EmployeeBDD employeeBdd;

    public DaoSQL(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        prospectBdd = new ProspectBDD(this);
        employeeBdd = new EmployeeBDD(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(employeeBdd.getCreateBdd());
        db.execSQL(prospectBdd.getCreateBdd());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + prospectBdd.getTableName());
        db.execSQL("DROP TABLE " + employeeBdd.getTableName());
        onCreate(db);
    }

    public ProspectBDD getProspectBdd() {
        return prospectBdd;
    }

    public EmployeeBDD getEmployeeBdd() {
        return employeeBdd;
    }
}
