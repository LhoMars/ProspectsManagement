package fr.prospectsmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private DaoSQL dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);
        setContentView(R.layout.activity_main);

        Entreprise e = new Entreprise("uneNom", 0);
        Prospect p = new Prospect("nom", "prenom", "tel", "mail", 1, e);
        long a = dataBase.addProspectBdd(p);
        System.out.println(a);
    }
}