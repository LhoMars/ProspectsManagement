package fr.prospectsmanagement;

public class Entreprise {
    private String nom;
    private int siret;

    public Entreprise(String nom, int siret) {
        this.nom = nom;
        this.siret = siret;
    }

    public String getNom() {
        return nom;
    }

    public int getSiret() {
        return siret;
    }
}
