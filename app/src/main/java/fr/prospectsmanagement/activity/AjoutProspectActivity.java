package fr.prospectsmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
            EditText nomProspectTxt = (EditText) findViewById(R.id.Nom);
            String nomProspect = nomProspectTxt.getText().toString();

            EditText prenomProspectTxt = (EditText) findViewById(R.id.Prenom);
            String prenomProspect = prenomProspectTxt.getText().toString();

            EditText telProspectTxt = (EditText) findViewById(R.id.Telephone);
            String telProspect = telProspectTxt.getText().toString();

            EditText mailProspectTxt = (EditText) findViewById(R.id.Email);
            String mailProspect = mailProspectTxt.getText().toString();

            EditText noteProspectTxt = (EditText) findViewById(R.id.Notes);
            String noteProspect = noteProspectTxt.getText().toString();

            EditText siretProspectTxt = (EditText) findViewById(R.id.Siret);
            String siretProspect = siretProspectTxt.getText().toString();

            EditText raisonSocialeProspectTxt = (EditText) findViewById(R.id.RaisonSociale);
            String raisonSocialeProspect = raisonSocialeProspectTxt.getText().toString();

            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String phoneNumberPattern = "^(?:(?:\\+|00)33[\\s.-]{0,3}(?:\\(0\\)[\\s.-]{0,3})?|0)[1-9](?:(?:[\\s.-]?\\d{2}){4}|\\d{2}(?:[\\s.-]?\\d{3}){2})$\n";

            if(nomProspectTxt.length()!=0 && prenomProspectTxt.length()!=0 && siretProspectTxt.length()!=0
                    && raisonSocialeProspectTxt.length()!=0 && mailProspectTxt.length()!=0){
                if(mailProspect.matches(emailPattern)){
                    if(telProspect.matches(phoneNumberPattern) || telProspectTxt.length()==0){
                        if(noteProspectTxt.length()==0){
                            noteProspect = "0";
                        }
                        if(telProspectTxt.length()==0){
                            telProspect = "0";
                        }
                        Prospect newProspect = new Prospect(nomProspect, prenomProspect, telProspect, mailProspect,
                                Integer.parseInt(noteProspect), Long.parseLong(siretProspect), raisonSocialeProspect);

                        dataBase.getProspectBdd().addProspectBdd(newProspect);

                        Intent retourMenu = new Intent(AjoutProspectActivity.this, MenuActivity.class);
                        startActivity(retourMenu);
                    }else{
                        boiteMessage("Le numéro de téléphone n'est pas correct");
                    }
                }else{
                    boiteMessage("L'e-mail n'est pas valable");
                }
            }else{
                boiteMessage("Un ou plusieurs champs requis sont manquants !\n" +
                        "\n" +
                        "Sont obligatoires : \n" +
                        "La Raison Sociale de l'entreprise\n" +
                        "Le Siret\n" +
                        "Le Nom\n" +
                        "Le Prénom\n" +
                        "Le Mail");
            }
        }
    };

    public View.OnClickListener eventBtnAnnuler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent retourMenu = new Intent(AjoutProspectActivity.this, MenuActivity.class);
            startActivity(retourMenu);
        }
    };

    private void boiteMessage(String msg) {
        AlertDialog.Builder boite = new AlertDialog.Builder(AjoutProspectActivity.this);
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
