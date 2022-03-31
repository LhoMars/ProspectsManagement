package fr.prospectsmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import fr.prospectsmanagement.R;
import fr.prospectsmanagement.dataBase.DaoSQL;


public class AjoutProspectActivity extends AppCompatActivity {
    private DaoSQL dataBase;
    public Button btnAnnuler = null;
    public Button btnRechercherEntreprise = null;
    public EditText raisonSociale = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);

        setContentView(R.layout.activity_addprospect);

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

        }
    };
}
