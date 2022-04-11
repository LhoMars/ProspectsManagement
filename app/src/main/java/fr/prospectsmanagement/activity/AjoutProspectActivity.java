package fr.prospectsmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.prospectsmanagement.R;
import fr.prospectsmanagement.api.ApiGouv;
import fr.prospectsmanagement.dataBase.DaoSQL;
import fr.prospectsmanagement.model.Employee;
import fr.prospectsmanagement.model.Prospect;


public class AjoutProspectActivity extends AppCompatActivity {
    private DaoSQL dataBase;
    private Employee lEmployee;
    private Button btnAnnuler = null;
    private Button btnRechercherEntreprise = null;
    private Button btnEnregistrer = null;
    private EditText raisonSociale = null;
    private EditText siretText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);

        Intent i = getIntent();
        this.lEmployee = i.getParcelableExtra("employee");

        setContentView(R.layout.activity_addprospect);

        /* Selection des vues de l'activité */
        raisonSociale = (EditText) findViewById(R.id.RaisonSociale);
        siretText = (EditText) findViewById(R.id.Siren);

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

            long siret = apiGouv.getSiretWithName(raisonSociale.getText().toString());
            String siretString = Long.toString(siret);
            siretText.setText(siretString, TextView.BufferType.EDITABLE);

            boiteMessage(apiGouv.getResult());
        }
    };

    public View.OnClickListener eventBtnEnregistrer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            /* Récupération de ce qu'a écrit l'utilisateur dans les EditText
            puis on les transforment en chaine de caractère qu'on stockent dans de nouvelles variables*/

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

            EditText sirenProspectTxt = (EditText) findViewById(R.id.Siren);
            String sirenProspect = sirenProspectTxt.getText().toString();

            EditText raisonSocialeProspectTxt = (EditText) findViewById(R.id.RaisonSociale);
            String raisonSocialeProspect = raisonSocialeProspectTxt.getText().toString();

            /* Initialisation des regex pour faire des tests par rapport aux entrés de l'utilisateur*/

            String regexEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String regexPhoneNumber = "(33|0033|0)[1-9][0-9]{8}$";
            String regexSpace = "(\\s+)$";
            String regexNomPrenom = "[A-Z][A-Za-z\\é\\è\\ê\\ï\\-]+$";
            String regexNumbers = "[0-9]";
            String regexRaisonSociale = "[A-Z][A-Za-z\\é\\è\\ê\\ï\\-]+$";

            String telProspectTest = "";
            int noteProspectTest = 0;
            long sirenProspectTest = 0;

            if (nomProspectTxt.length() != 0 && prenomProspectTxt.length() != 0 && sirenProspectTxt.length() != 0
                    && raisonSocialeProspectTxt.length() != 0 && mailProspectTxt.length() != 0) {
                if (mailProspect.matches(regexEmail) && !mailProspect.matches(regexSpace)) {
                    if (prenomProspect.matches(regexNomPrenom) && !prenomProspect.matches(regexSpace)) {
                        if (nomProspect.matches(regexNomPrenom) && !nomProspect.matches(regexSpace)) {
                            if (sirenProspect.matches(regexNumbers) && !sirenProspect.matches(regexSpace)) {
                                sirenProspectTest = Long.parseLong(sirenProspect);
                                if (raisonSocialeProspect.matches(regexRaisonSociale) && !raisonSocialeProspect.matches(regexSpace)) {
                                    if (telProspect.length() == 0 || telProspect.matches(regexPhoneNumber)) {
                                        telProspectTest = (telProspect.length() == 0) ? "0" : telProspect;
                                        if (noteProspect.length() == 0 || (noteProspect.matches(regexNumbers) &&
                                                Integer.parseInt(noteProspect) <= 20 && Integer.parseInt(noteProspect) >= 1)) {
                                            noteProspectTest = (noteProspect.length() == 0) ? 0 : Integer.parseInt(noteProspect);
                                            Prospect newProspect = new Prospect(nomProspect, prenomProspect, telProspectTest, mailProspect,
                                                    noteProspectTest, sirenProspectTest, raisonSocialeProspect, false);

                                            dataBase.getProspectBdd().add(newProspect);

                                            Intent retourMenu = new Intent(AjoutProspectActivity.this, MenuActivity.class);
                                            startActivity(retourMenu);
                                        } else {
                                            boiteMessage("Le numéro de téléphone doit être vide ou correct");
                                        }
                                    } else {
                                        boiteMessage("Le numéro de téléphone doit être vide ou correct");
                                    }
                                } else {
                                    boiteMessage("La raison sociale n'est pas valable");
                                }
                            } else {
                                boiteMessage("le siret est incorrect");
                            }
                        } else {
                            boiteMessage("le nom de famille n'est pas accepté");
                        }
                    } else {
                        boiteMessage("Le prénom n'est pas accepté");
                    }
                } else {
                    boiteMessage("L'e-mail n'est pas valable");
                }
            } else {
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
            retourMenu.putExtra("employee", lEmployee);
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
