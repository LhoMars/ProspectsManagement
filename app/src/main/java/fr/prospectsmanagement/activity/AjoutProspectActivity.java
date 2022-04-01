package fr.prospectsmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import fr.prospectsmanagement.R;
import fr.prospectsmanagement.api.ApiBdd;
import fr.prospectsmanagement.api.ApiGouv;
import fr.prospectsmanagement.dataBase.DaoSQL;
import fr.prospectsmanagement.model.Prospect;


public class AjoutProspectActivity extends AppCompatActivity {
    private DaoSQL dataBase;
    public Button btnAnnuler = null;
    public Button btnRechercherEntreprise = null;
    public EditText raisonSociale = null;
    public EditText siretText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);

        setContentView(R.layout.activity_addprospect);

        /* Selection des vues de l'activité */
        raisonSociale = (EditText) findViewById(R.id.raisonSociale);
        siretText = (EditText) findViewById(R.id.Siret);

        /* Sélection du bouton par son ID puis on ajoute son événement */
        btnAnnuler = (Button) findViewById(R.id.btnAnnuler);
        btnAnnuler.setOnClickListener(eventBtnAnnuler);

        btnRechercherEntreprise = (Button) findViewById(R.id.btnRechercherEntreprise);
        btnRechercherEntreprise.setOnClickListener(eventBtnRechercherEntreprise);
    }

    public View.OnClickListener eventBtnAnnuler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent AjoutProspect = new Intent(AjoutProspectActivity.this, MenuActivity.class);
            startActivity(AjoutProspect);
        }
    };

    public View.OnClickListener eventBtnRechercherEntreprise = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ApiGouv apiGouv = new ApiGouv();

            try{
                long siret = apiGouv.getSiretWithName(raisonSociale.getText().toString());
                String siretString = Long.toString(siret);
                siretText.setText(siretString, TextView.BufferType.EDITABLE);

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };
}
