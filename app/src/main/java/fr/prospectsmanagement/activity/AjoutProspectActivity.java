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

            EditText raisonSocialeProspectTxt = (EditText) findViewById(R.id.RaisonSociale);
            String raisonSocialeProspect = raisonSocialeProspectTxt.getText().toString();

            EditText sirenProspectTxt = (EditText) findViewById(R.id.Siren);
            String sirenProspect = sirenProspectTxt.getText().toString();

            EditText nomProspectTxt = (EditText) findViewById(R.id.Nom);
            String nomProspect = nomProspectTxt.getText().toString();

            EditText prenomProspectTxt = (EditText) findViewById(R.id.Prenom);
            String prenomProspect = prenomProspectTxt.getText().toString();

            EditText mailProspectTxt = (EditText) findViewById(R.id.Email);
            String mailProspect = mailProspectTxt.getText().toString();

            EditText telProspectTxt = (EditText) findViewById(R.id.Telephone);
            String telProspect = telProspectTxt.getText().toString();

            EditText noteProspectTxt = (EditText) findViewById(R.id.Notes);
            String noteProspect = noteProspectTxt.getText().toString();

            /* Initialisation des Regex pour faire des tests par rapport aux entrées de l'utilisateur*/

            String regexRaisonSociale = "[A-Za-z\\é\\è\\ê\\ï\\-]+$";
            String regexSiren = "[0-9]{9}|0";
            String regexNomPrenom = "[A-Za-z\\é\\è\\ê\\ï\\-]+$";
            String regexEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String regexPhoneNumber = "(33|0033|0)[1-9][0-9]{8}$";
            String regexNote = "[0-9]|1[0-9]|20";
            String regexSpace = "(\\s+)$";

            long sirenProspectTest = 0;
            String telProspectTest = "";
            int noteProspectTest = 0;
            String errorMessage = "";

            if (nomProspectTxt.length() != 0 && prenomProspectTxt.length() != 0 && sirenProspectTxt.length() != 0
                    && raisonSocialeProspectTxt.length() != 0 && mailProspectTxt.length() != 0) {
                Prospect newProspect = new Prospect();

                if (raisonSocialeProspect.matches(regexRaisonSociale) && !raisonSocialeProspect.matches(regexSpace)) {
                    newProspect.setRaisonSocial(raisonSocialeProspect);
                }else{
                    errorMessage += "- Raison Sociale \n";
                }

                if (sirenProspect.matches(regexSiren) && !sirenProspect.matches(regexSpace)) {
                    sirenProspectTest = Long.parseLong(sirenProspect);
                    newProspect.setSiret(sirenProspectTest);
                }else{
                    errorMessage += "- Siren \n";
                }

                if (prenomProspect.matches(regexNomPrenom) && !prenomProspect.matches(regexSpace)) {
                    newProspect.setPrenom(prenomProspect);
                }else{
                    errorMessage += "- Prénom \n";
                }

                if (nomProspect.matches(regexNomPrenom) && !nomProspect.matches(regexSpace)) {
                    newProspect.setNom(nomProspect);
                }else{
                    errorMessage += "- Nom \n";
                }

                if (mailProspect.matches(regexEmail) && !mailProspect.matches(regexSpace)) {
                    newProspect.setMail(mailProspect);
                }else{
                    errorMessage += "- Mail \n";
                }

                if (telProspect.length() == 0 || telProspect.matches(regexPhoneNumber)) {
                    telProspectTest = (telProspect.length() == 0) ? "0" : telProspect;
                    newProspect.setTel(telProspectTest);
                }else{
                    errorMessage += "- Téléphone \n";
                }

                if (noteProspect.length() == 0 || (noteProspect.matches(regexNote))) {
                    noteProspectTest = (noteProspect.length() == 0) ? 0 : Integer.parseInt(noteProspect);
                    newProspect.setNotes(noteProspectTest);
                }else{
                    errorMessage += "- Note \n";
                }

                newProspect.setIsUpdate(false);

                if(errorMessage.length()==0){
                    dataBase.getProspectBdd().add(newProspect);

                    Intent retourMenu = new Intent(AjoutProspectActivity.this, MenuActivity.class);
                    retourMenu.putExtra("employee",lEmployee);
                    startActivity(retourMenu);
                }else{
                    boiteMessage("Sont invalides :\n"+errorMessage);
                }
            } else {
                boiteMessage("L'un des champs suivants est vide.\n" +
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
