package fr.prospectsmanagement.dataBase;

import android.content.ContentValues;
import android.database.Cursor;
import fr.prospectsmanagement.model.Prospect;

import java.util.ArrayList;

/**
 * Cette classe permet d'accéder à la table prospect de la bdd
 */
public class ProspectBDD extends ObjectBDD {

    private static final String ID_COL = "id";

    private static final String NOM_COL = "nom";

    private static final String PRENOM_COL = "prenom";

    private static final String TEL_COL = "tel";

    private static final String MAIL_COL = "mail";

    private static final String NOTES_COL = "notes";

    private static final String SIRET_COL = "siret";

    private static final String RAISON_SOCIAL_COL = "raisonsocial";

    public ProspectBDD(DaoSQL maBaseSQLite) {
        super(maBaseSQLite, "prospect",
                "CREATE TABLE prospect (" +
                        ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NOM_COL + " TEXT, " +
                        PRENOM_COL + " TEXT, " +
                        TEL_COL + " TEXT, " +
                        MAIL_COL + " TEXT, " +
                        NOTES_COL + " INTEGER DEFAULT 0, " +
                        SIRET_COL + " INTEGER, " +
                        RAISON_SOCIAL_COL + " TEXT); ");
    }


    /**
     * Ajoute un prospect à la bdd
     *
     * @param p Prospect : le prospect à ajouter
     * @return long : l'id
     */
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
        values.put(SIRET_COL, p.getSiret());
        values.put(RAISON_SOCIAL_COL, p.getRaisonSocial());
        //on insère l'objet dans la BDD via le ContentValues
        return getBdd().insert(getTableName(), null, values);
    }

    /**
     * Récupère le prospect avec son nom
     *
     * @param nom String : le nom du prospect
     * @return Prospect ou null : si il n'existe aucun prospect ou plus que 1 le résultat est null
     */
    public Prospect getProspectWithNom(String nom) {
        open();
        Cursor c = getBdd().query(getTableName(), new String[]{NOM_COL, PRENOM_COL, TEL_COL, MAIL_COL, NOTES_COL, SIRET_COL, RAISON_SOCIAL_COL}, NOM_COL + " = '" + nom + "'", null, null, null, null);

        if (c == null || c.getCount() == 0 || c.getCount() > 1) {
            return null;
        }

        c.moveToFirst();
        Prospect leProspect = cursorToProspect(c);
        c.close();
        close();

        return leProspect;
    }

    /**
     * Récupère tout les prospects de la bdd
     *
     * @return ArrayList : l'ensemble des prospects
     */
    public ArrayList<Prospect> getAllProspects() {
        open();
        ArrayList<Prospect> lesProspects = new ArrayList();
        Cursor c = getBdd().query(getTableName(), new String[]{NOM_COL, PRENOM_COL, TEL_COL, MAIL_COL, NOTES_COL, SIRET_COL, RAISON_SOCIAL_COL}, null, null, null, null, null);

        if (c == null || c.getCount() == 0) {
            return null;
        }

        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            lesProspects.add(cursorToProspect(c));
            c.moveToNext();
        }

        c.close();
        close();
        return lesProspects;
    }

    /**
     * Cette méthode permet de convertir un cursor en un prospect
     *
     * @param c Cursor : le cuseur du résultat de la bdd
     * @return Prospect : le prospect spécifique
     */
    private Prospect cursorToProspect(Cursor c) {
        /* On créé un prospect
        et on donne ses paramètres */

        Prospect p = new Prospect();
        p.setNom(c.getString(0));
        p.setPrenom(c.getString(1));
        p.setTel(c.getString(2));
        p.setMail(c.getString(3));
        p.setNotes(c.getInt(4));
        p.setSiret(c.getInt(5));
        p.setRaisonSocial(c.getString(6));

        //On retourne le prospect
        return p;
    }
}
