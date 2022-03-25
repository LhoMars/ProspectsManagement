package fr.prospectsmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.graphics.drawable.AnimationDrawable;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import fr.prospectsmanagement.model.Prospect;
import fr.prospectsmanagement.R;
import fr.prospectsmanagement.api.ApiBdd;
import fr.prospectsmanagement.dataBase.DaoSQL;
import fr.prospectsmanagement.dataBase.ProspectBDD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private DaoSQL dataBase;
    public Button btnAjouter = null;

    TableLayout tableLayout;
    TableRow newTR;
    TextView newTxtNom,newTxtPrenom,newTxtEntreprise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);

        ApiBdd api = new ApiBdd();
        boiteMessage(api.getResultApi());

        /* A utiliser charger */
        api.callWebService("getAllProspects");
        JSONArray json = api.getJsonData();
        updateBddProspects(json);

<<<<<<< Updated upstream:app/src/main/java/fr/prospectsmanagement/activity/MenuActivity.java
=======
        /* Envoie prospect */
>>>>>>> Stashed changes:app/src/main/java/fr/prospectsmanagement/MenuActivity.java
        ArrayList<Prospect> lesProspects = dataBase.getProspectBdd().getAllProspects();
        JSONArray jsonProspects = api.createJsonProspects(lesProspects);
        api.postJsonProspect("InsertProspect", jsonProspects.toString());

        setContentView(R.layout.activity_menu);

        /*Animation du Background mise en coommentaire pour l'instant, couleurs à changer.*/

        RelativeLayout RelativeLayout = findViewById(R.id.menuLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) RelativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2200);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        /* Sélection du bouton par son ID puis on ajoute son événement */
        btnAjouter = (Button) findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(eventBtnAjouter);

        /* TABLE */
        tableLayout = (TableLayout)findViewById(R.id.tableLayoutProspects);
        tableauVueProspects();
    }

    public View.OnClickListener eventBtnAjouter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent AjoutProspect = new Intent(MenuActivity.this, AjoutProspectActivity.class);
            startActivity(AjoutProspect);
        }
    };

    private void updateBddProspects(JSONArray jsonData) {
        try {
            if (jsonData.length() != 0) {
                for (int i = 0; i < jsonData.length(); i++) {
                    JSONObject json = jsonData.getJSONObject(i);
                    Prospect p = new Prospect();
                    p.setPrenom(json.getString("prenom"));
                    p.setNom(json.getString("nom"));
                    p.setTel(json.getString("tel"));
                    p.setMail(json.getString("mail"));
                    p.setNotes((json.getInt("note")));
                    p.setSiret((json.getInt("siret")));
                    p.setRaisonSocial((json.getString("raisonsocial")));

                    if (dataBase.getProspectBdd().getProspectWithNom(p.getNom()) == null) {
                        dataBase.getProspectBdd().addProspectBdd(p);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void tableauVueProspects(){
        newTR = new TableRow(MenuActivity.this);
        newTxtNom = new TextView(MenuActivity.this);
        newTxtPrenom = new TextView(MenuActivity.this);
        newTxtEntreprise = new TextView(MenuActivity.this);

        ArrayList<Prospect> allProspects = dataBase.getProspectBdd().getAllProspects();

        for(int i = 0; i<5; i++){

            dataBase.getProspectBdd().getProspectWithNom("nom1");

            newTxtNom.setText("test1");
            newTxtPrenom.setText("test2");
            newTxtEntreprise.setText("test3");
        }

        newTR.addView(newTxtNom);
        newTR.addView(newTxtPrenom);
        newTR.addView(newTxtEntreprise);

        tableLayout.addView(newTR);
    }
}