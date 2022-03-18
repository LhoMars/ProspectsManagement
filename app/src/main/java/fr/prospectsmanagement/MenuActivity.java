package fr.prospectsmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import fr.prospectsmanagement.api.ApiBdd;
import fr.prospectsmanagement.dataBase.DaoSQL;

public class MenuActivity extends AppCompatActivity {
    private DaoSQL dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);

        ApiBdd api = new ApiBdd();
        String apiMsg = api.callWebService();
        System.out.println("MESSAGE BOX : " + apiMsg);

        Entreprise e = new Entreprise("uneNom", 0);
        Prospect p = new Prospect("nom", "prenom", "tel", "mail", 1, e);
        dataBase.getProspectBdd().addProspectBdd(p);

        setContentView(R.layout.activity_menu);
    }

    private void boiteMessage(String msg) {
        AlertDialog.Builder boite = new AlertDialog.Builder(MenuActivity.this);
        boite.setTitle("Information");
        boite.setMessage(msg);
        boite.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        boite.show();
    }
}