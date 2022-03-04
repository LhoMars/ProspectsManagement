package fr.prospectsmanagement;

public class Employee {
    private String identifiant;
    private String password;

    public Employee(String identifiant, String password) {
        this.identifiant = identifiant;
        this.password = password;
    }

    public String getIdentifiant() {
        return identifiant;
    }
    public String getPassword() {
        return password;
    }
}
