package fr.prospectsmanagement.dataBase;

import android.database.sqlite.SQLiteDatabase;

public abstract class ObjectBDD {

    private String name;
    private String createSql;
    private DaoSQL maBaseSQLite;
    private SQLiteDatabase bdd;

    public ObjectBDD(DaoSQL maBaseSQLite, String name, String create) {
        this.maBaseSQLite = maBaseSQLite;
        this.name = name;
        this.createSql = create;
    }

    public void open(){
        this.bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        this.bdd.close();
    }

    public String getTableName(){
        return this.name;
    }

    public String getCreateBdd(){
        return this.createSql;
    }

    public SQLiteDatabase getBdd(){return this.bdd;}

    @Override
    public String toString() {
        return "ObjectBDD{" +
                "CREATE_BDD= '" + createSql + '\'' +
                ", maBaseSQLite= " + maBaseSQLite +
                ", bdd= " + bdd +
                '}';
    }
}
