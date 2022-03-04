package fr.prospectsmanagement;

public class Prospect {
    private String nom;
    private String prenom;
    private String tel;
    private String mail;
    private int notes;
    private Entreprise lEntreprise;

    public Prospect(String nom, String prenom, String tel, String mail, int notes, Entreprise lEntreprise) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.mail = mail;
        this.notes = notes;
        this.lEntreprise = lEntreprise;
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

    public Entreprise getlEntreprise() {
        return lEntreprise;
    }
}
