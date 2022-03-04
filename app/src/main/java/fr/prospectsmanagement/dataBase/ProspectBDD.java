package fr.prospectsmanagement.dataBase;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import fr.prospectsmanagement.Prospect;

public class ProspectBDD extends ObjectBDD{

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
        values.put(TEL_COL,p.getTel());
        values.put(MAIL_COL, p.getMail());
        values.put(NOTES_COL, p.getNotes());
        //on insère l'objet dans la BDD via le ContentValues
        return getBdd().insert(getTableName(), null, values);
    }
    /*
public int removeLivreWithID(int id){
    //Suppression d'un livre de la BDD grâce à l'ID
    return bdd.delete(TABLE_LIVRES, COL_ID + " = " +id, null);
}

    public Livre getLivreWithTitre(String titre){
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_LIVRES, new String[] {COL_ID, COL_ISBN, COL_TITRE}, COL_TITRE + " LIKE \"" + titre +"\"", null, null, null, null);
        return cursorToLivre(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Livre cursorToLivre(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Livre livre = new Livre();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        livre.setId(c.getInt(NUM_COL_ID));
        livre.setIsbn(c.getString(NUM_COL_ISBN));
        livre.setTitre(c.getString(NUM_COL_TITRE));
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return livre;
    }*/
}
