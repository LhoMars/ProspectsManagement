package fr.prospectsmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import fr.prospectsmanagement.model.Employee;
import fr.prospectsmanagement.model.Prospect;
import fr.prospectsmanagement.R;
import fr.prospectsmanagement.dataBase.DaoSQL;

public class LoginActivity extends AppCompatActivity {
    private DaoSQL database;
    public EditText editIdentifiant = null;
    public EditText editPassword = null;
    public Button btnLogin = null;
    public Button btnForgetPassword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new DaoSQL(this);

        /* Jeux de test pour la bdd
        Créer un employer pour se connecter */
        if (database.getEmployeeBdd().getEmployeeWithIdentifiant("user") == null) {
            Employee e = new Employee("user", "password");
            database.getEmployeeBdd().addemployeeBdd(e);
        }

        /* Créer des prospects de test */
        if (database.getProspectBdd().getProspectWithNom("nom1") == null) {
            /*Prospect p = new Prospect("nom1", "prenom1", "0123456789", "nom.nom@gmail.com", 0, 1, "entrepirse");
            Prospect p1 = new Prospect("nom2", "prenom2", "0123456789", "nom.nom@gmail.com", 0, 1, "entrepirse");
            Prospect p2 = new Prospect("nom3", "prenom3", "0123456789", "nom.nom@gmail.com", 0, 1, "entrepirse");
            database.getProspectBdd().addProspectBdd(p);
            database.getProspectBdd().addProspectBdd(p1);
            database.getProspectBdd().addProspectBdd(p2);*/

            Prospect p = new Prospect("nom1", "prenom1", "0123456789", "nom.un.nom@gmail.com", 0, 1, "entreprise1");
            Prospect p1 = new Prospect("nom2", "prenom2", "0223456789", "nom.deux.nom@gmail.com", 0, 1, "entreprise2");
            Prospect p2 = new Prospect("nom3", "prenom3", "0323456789", "nom.trois.nom@gmail.com", 0, 1, "entreprise3");
            Prospect p3 = new Prospect("nom4", "prenom4", "0423456789", "nom.quatre.nom@gmail.com", 0, 1, "entreprise4");
            Prospect p4 = new Prospect("nom5", "prenom5", "0523456789", "nom.cinq.nom@gmail.com", 0, 1, "entreprise5");
            Prospect p5 = new Prospect("nom6", "prenom6", "0623456789", "nom.six.nom@gmail.com", 0, 1, "entreprise6");
            database.getProspectBdd().addProspectBdd(p);
            database.getProspectBdd().addProspectBdd(p1);
            database.getProspectBdd().addProspectBdd(p2);
            database.getProspectBdd().addProspectBdd(p3);
            database.getProspectBdd().addProspectBdd(p4);
            database.getProspectBdd().addProspectBdd(p5);
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_login);

        /* Selection des vues de l'activité */
        editIdentifiant = (EditText) findViewById(R.id.Identifiant);
        editPassword = (EditText) findViewById(R.id.MotDePasse);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnForgetPassword = (Button) findViewById(R.id.OublieMotDePasse);

        /*Evenement sur le bouton login*/
        btnLogin.setOnClickListener(eventBtnLogin);

    }


    public View.OnClickListener eventBtnLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder boite = new AlertDialog.Builder(LoginActivity.this);
            boite.setTitle("Information");
            if (editIdentifiant.getText().length() > 0 && editPassword.getText().length() > 0) {
                Employee lEmploye = database.getEmployeeBdd().getEmployeeWithIdentifiant(editIdentifiant.getText().toString());

                if (lEmploye != null && lEmploye.checkPassword(editPassword.getText().toString())) {
                    Intent menu = new Intent(LoginActivity.this, MenuActivity.class);
                    menu.putExtra("employee", lEmploye);
                    startActivity(menu);
                } else {
                    boite.setMessage("Le combo identifiant/mot de passe n'est pas valide");
                    boite.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            editIdentifiant.requestFocus();
                        }
                    });
                    boite.show();
                }
            } else {
                boite.setMessage("L'identifiant et/ou le mot de passe n'est pas saisie");
                boite.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        editIdentifiant.requestFocus();
                    }
                });
                boite.show();
            }
        }
    };
}