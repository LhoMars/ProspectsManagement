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
    private EditText editIdentifiant = null;
    private EditText editPassword = null;
    private Button btnLogin = null;

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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_login);

        /* Selection des vues de l'activité */
        editIdentifiant = (EditText) findViewById(R.id.Identifiant);
        editPassword = (EditText) findViewById(R.id.MotDePasse);
        btnLogin = (Button) findViewById(R.id.btnLogin);

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