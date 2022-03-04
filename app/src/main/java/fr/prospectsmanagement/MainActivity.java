package fr.prospectsmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import fr.prospectsmanagement.dataBase.DaoSQL;

public class MainActivity extends AppCompatActivity {
    private DaoSQL dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);

        Entreprise e = new Entreprise("uneNom", 0);
        Prospect p = new Prospect("nom", "prenom", "tel", "mail", 1, e);
        dataBase.getProspectBdd().addProspectBdd(p);

        setContentView(R.layout.activity_main);
    }
}