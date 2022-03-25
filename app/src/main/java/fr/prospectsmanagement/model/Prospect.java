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
    private int siret;
    private String raisonSocial;

    public Prospect(String nom, String prenom, String tel, String mail, int notes) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.mail = mail;
        this.notes = notes;
    }

    public Prospect() {
        this("", "", "", "", 0);
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

    public int getSiret() {
        return siret;
    }

    public String getRaisonSocial() {
        return raisonSocial;
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

    public void setSiret(int siret) {
        this.siret = siret;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
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
