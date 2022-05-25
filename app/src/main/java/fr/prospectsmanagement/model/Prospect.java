package fr.prospectsmanagement.model;

/**
 * Définit un prospect
 */
public class Prospect {
    private String nom;
    private String prenom;
    private String tel;
    private String mail;
    private int notes;
    private long siret;
    private String raisonSocial;
    private boolean isUpdate;

    public Prospect(String nom, String prenom, String tel, String mail, int notes, long siret, String raisonSocial,boolean isUpdate) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.mail = mail;
        this.notes = notes;
        this.siret = siret;
        this.raisonSocial = raisonSocial;
        this.isUpdate = isUpdate;
    }

    public Prospect() {
        this("", "", "", "", 0, 0, "", false);
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTel() {
        return tel;
    }

    public String getMail() {
        return mail;
    }

    public int getNotes() {
        return notes;
    }

    public long getSiret() {
        return siret;
    }

    public String getRaisonSocial() {
        return raisonSocial;
    }

    public boolean getIsUpdate() {
        return isUpdate;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setNotes(int notes) {
        this.notes = notes;
    }

    public void setSiret(long siret) {
        this.siret = siret;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    @Override
    public String toString() {
        return "Prospect{" +
                "nom= " + nom +
                ", prenom= " + prenom +
                ", tel= " + tel +
                ", mail= " + mail +
                ", notes= " + notes +
                '}';
    }
}
