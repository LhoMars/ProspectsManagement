package fr.prospectsmanagement.dataBase;

import android.database.sqlite.SQLiteDatabase;

public abstract class ObjectBDD {

    private static String TABLE_NAME;
    private String CREATE_BDD;
    private DaoSQL maBaseSQLite;
    private SQLiteDatabase bdd;

    public ObjectBDD(DaoSQL maBaseSQLite, String name, String create) {
        this.maBaseSQLite = maBaseSQLite;
        this.TABLE_NAME = name;
        this.CREATE_BDD = create;
        this.bdd = maBaseSQLite.getWritableDatabase();
    }

    public void open(){
        bdd = maBaseSQLite.getWritableDatabase();
    }
    public void close(){
        bdd.close();
    }
    public String getTableName(){
        return TABLE_NAME;
    }
    public String getCreateBdd(){
        return CREATE_BDD;
    }
    public SQLiteDatabase getBdd(){return bdd;}
}
