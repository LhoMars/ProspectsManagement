package fr.prospectsmanagement.dataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fr.prospectsmanagement.Employee;
import fr.prospectsmanagement.Prospect;

public class ProspectBDD extends ObjectBDD {

    private static final String ID_COL = "id";
    private static final int ID_COL_NUM = 0;

    private static final String NOM_COL = "nom";
    private static final int NOM_COL_NUM = 1;

    private static final String PRENOM_COL = "prenom";
    private static final int PRENOM_COL_NUM = 2;

    private static final String TEL_COL = "tel";
    private static final int TEL_COL_NUM = 3;

    private static final String MAIL_COL = "mail";
    private static final int MAIL_COL_NUM = 4;

    private static final String NOTES_COL = "notes";
    private static final int NOTES_COL_NUM = 5;

    private static final String ENTREPRISE_COL = "entreprise";
    private static final int ENTREPRISE_COL_NUM = 6;


    public ProspectBDD(DaoSQL maBaseSQLite) {
        super(maBaseSQLite, "prospect",
                "CREATE TABLE prospect (" +
                        ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NOM_COL + " TEXT, " +
                        PRENOM_COL + " TEXT, " +
                        TEL_COL + " TEXT, " +
                        MAIL_COL + " TEXT, " +
                        NOTES_COL + " INTEGER DEFAULT 0); ");
    }


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
        //on insère l'objet dans la BDD via le ContentValues
        return getBdd().insert(getTableName(), null, values);
    }

    public Prospect getProspectWithNom(String nom) {
        open();
        Cursor c = getBdd().query(getTableName(), new String[]{NOM_COL, PRENOM_COL, TEL_COL, MAIL_COL, NOTES_COL}, NOM_COL + " = '" + nom+ "'", null, null, null, null);
        return cursorToProspect(c);
    }

    //Cette méthode permet de convertir un cursor en un prospect
    private Prospect cursorToProspect(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c == null || c.getCount() == 0) {
            return null;
        }

        //Sinon on se place sur le premier élément
        c.moveToFirst();

        //On créé un employee
        Prospect p = new Prospect();
        p.setNom(c.getString(0));
        p.setPrenom(c.getString(1));
        p.setTel(c.getString(2));
        p.setMail(c.getString(3));
        p.setNotes(c.getInt(4));

        //On ferme le cursor
        c.close();
        close();
        //On retourne le prospect
        return p;
    }


}
