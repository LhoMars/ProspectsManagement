package fr.prospectsmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.graphics.drawable.AnimationDrawable;

import fr.prospectsmanagement.api.ApiBdd;
import fr.prospectsmanagement.dataBase.DaoSQL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private DaoSQL dataBase;
    public Button btnAjouter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);

        ApiBdd api = new ApiBdd();

        api.callWebService("getAllProspects");
        boiteMessage(api.getResultApi());

        JSONArray json = api.getJsonData();
        updateBddProspects(json);


        ArrayList<Prospect> lesProspects = dataBase.getProspectBdd().getAllProspects();
        JSONArray jsonProspects = api.createJsonProspects(lesProspects);
        api.postJsonProspect("InsertProspect", jsonProspects.toString());

        setContentView(R.layout.activity_menu);

        /*Animation du Background mise en coommentaire pour l'instant, couleurs à changer.*/

        RelativeLayout RelativeLayout = findViewById(R.id.menuLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) RelativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        /* Sélection du bouton par son ID puis on ajoute son événement */
        btnAjouter = (Button) findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(eventBtnAjouter);
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
                    p.setPrenom(json.getString("prenomprospect"));
                    p.setNom(json.getString("nomprospect"));
                    p.setTel(json.getString("telprospect"));
                    p.setMail(json.getString("mailprospect"));
                    p.setNotes((json.getInt("noteprospect")));

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
}