package fr.prospectsmanagement;

import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import fr.prospectsmanagement.dataBase.DaoSQL;

public class MainActivity extends AppCompatActivity {
    private DaoSQL database;
    public EditText editIdentifiant = null;
    public EditText editPassword = null;
    public Button btnLogin = null;
    public Button btnForgetPassword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new DaoSQL(this);

        Entreprise e = new Entreprise("uneNom", 0);
        Prospect p = new Prospect("nom", "prenom", "tel", "mail", 1, e);
        database.getProspectBdd().addProspectBdd(p);

        setContentView(R.layout.activity_main);

        /* Selection des vues de l'activité */
        editIdentifiant = (EditText) findViewById(R.id.Identifiant);
        editPassword = (EditText) findViewById(R.id.MotDePasse);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnForgetPassword = (Button) findViewById(R.id.OublieMotDePasse);

        /*Ecouteur sur le bouton OK*/
        //btnLogin.setOnClickListener(EcouteurBouton);
    }
}