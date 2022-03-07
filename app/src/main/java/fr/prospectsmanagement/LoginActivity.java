package fr.prospectsmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
        Employee e = new Employee("user","password");
        database.getEmployeeBdd().addemployeeBdd(e);

        setContentView(R.layout.activity_login);

        /* Selection des vues de l'activité */
        editIdentifiant = (EditText) findViewById(R.id.Identifiant);
        editPassword = (EditText) findViewById(R.id.MotDePasse);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnForgetPassword = (Button) findViewById(R.id.OublieMotDePasse);

        /*Evenement sur le bouton login*/
        btnLogin.setOnClickListener(event);

        //System.out.println(database.getEmployeeBdd().getEmployeeWithIdentifiant("user"));
    }


    public View.OnClickListener event = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            System.out.println("Bouton onclick");
            if (editIdentifiant.getText().length() > 0 && editPassword.getText().length() > 0) {
                Employee lEmploye = database.getEmployeeBdd().getEmployeeWithIdentifiant(editIdentifiant.getText().toString());
                System.out.println(lEmploye);
            }else {
                System.out.println("DIALOG");
                AlertDialog.Builder boite = new AlertDialog.Builder(LoginActivity.this);
                boite.setTitle("Information");
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