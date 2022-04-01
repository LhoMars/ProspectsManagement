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
    private Button btnAnnuler = null;
    private Button btnRechercherEntreprise = null;
    private Button btnEnregistrer = null;
    private EditText raisonSociale = null;
    private EditText siretText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);

        setContentView(R.layout.activity_addprospect);

        /* Selection des vues de l'activité */
        raisonSociale = (EditText) findViewById(R.id.RaisonSociale);
        siretText = (EditText) findViewById(R.id.Siret);

        /* Sélection du bouton par son ID puis on ajoute son événement */
        btnRechercherEntreprise = (Button) findViewById(R.id.btnRechercherEntreprise);
        btnRechercherEntreprise.setOnClickListener(eventBtnRechercherEntreprise);

        btnEnregistrer = (Button) findViewById(R.id.btnEnregistrer);
        btnEnregistrer.setOnClickListener(eventBtnEnregistrer);

        btnAnnuler = (Button) findViewById(R.id.btnAnnuler);
        btnAnnuler.setOnClickListener(eventBtnAnnuler);
    }

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

    public View.OnClickListener eventBtnEnregistrer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText nomProspect = (EditText) findViewById(R.id.Nom);
            EditText prenomProspect = (EditText) findViewById(R.id.Prenom);
            EditText telProspect = (EditText) findViewById(R.id.Telephone);
            EditText mailProspect = (EditText) findViewById(R.id.Email);
            EditText notesProspect = (EditText) findViewById(R.id.Notes);
            EditText siretProspect = (EditText) findViewById(R.id.Siret);
            EditText raisonSocialeProspect = (EditText) findViewById(R.id.RaisonSociale);

            Prospect newProspect = new Prospect(nomProspect.getText().toString(), prenomProspect.getText().toString(),
                    telProspect.getText().toString(), mailProspect.getText().toString(),
                    Integer.parseInt(notesProspect.getText().toString()),
                    Long.parseLong(siretProspect.getText().toString()), raisonSocialeProspect.getText().toString());

            dataBase.getProspectBdd().addProspectBdd(newProspect);

            Intent retourMenu = new Intent(AjoutProspectActivity.this, MenuActivity.class);
            startActivity(retourMenu);
        }
    };

    public View.OnClickListener eventBtnAnnuler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent retourMenu = new Intent(AjoutProspectActivity.this, MenuActivity.class);
            startActivity(retourMenu);
        }
    };
}
