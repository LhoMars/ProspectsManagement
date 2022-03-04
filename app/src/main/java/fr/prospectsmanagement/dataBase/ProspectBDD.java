package fr.prospectsmanagement.dataBase;

public class ProspectBDD {

    private static final String TABLE_NAME = "prospect";
    private static final String ID_COL = "id";
    private static final int ID_COL_NUM = 0;

    private static final String NOM_COL = "nom";
    private static final int NOM_COL_NUM = 1;

    private static final String PRENOM_COL = "prenom";
    private static final int PRENOM_COL_NUM = 2;

    private static final String TEL_COL = "tel";
    private static final int BESTSCORE_COL_NUM = 3;

    private static final String MAIL_COL = "mail";
    private static final int DATE_COL_NUM = 4;

    private static final String NOTES_COL = "notes";
    private static final int NOTES_COL_NUM = 5;

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_NAME + " (" +
            ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOM_COL + " TEXT, " +
            PRENOM_COL + " TEXT, " +
            TEL_COL + " TEXT, " +
            MAIL_COL + " TEXT, " +
            NOTES_COL + " INTEGER DEFAULT 0); ";

    public ProspectBDD() {
    }
    public String getTableName(){
        return TABLE_NAME;
    }
    public String getCreateBdd(){
        return CREATE_BDD;
    }
}
