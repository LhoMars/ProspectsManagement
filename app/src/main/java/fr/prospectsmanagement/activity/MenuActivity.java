package fr.prospectsmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.graphics.drawable.AnimationDrawable;
import android.widget.TextView;

import fr.prospectsmanagement.activity.template.onClickInterface;
import fr.prospectsmanagement.model.Employee;
import fr.prospectsmanagement.model.Prospect;
import fr.prospectsmanagement.R;
import fr.prospectsmanagement.api.ApiBdd;
import fr.prospectsmanagement.dataBase.DaoSQL;
import fr.prospectsmanagement.activity.template.ShowProspectAdaptater;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MenuActivity extends AppCompatActivity {
    private DaoSQL dataBase;
    private Employee lEmployee;
    private Button btnAjouter = null;
    private Button btnRechercherVisibility = null;
    private Button btnRechercher = null;
    private Button btnSynchroniser = null;
    private EditText nomFiltre = null;
    private EditText prenomFiltre = null;
    private EditText entrepriseFiltre = null;
    private onClickInterface interfaceClickable;
    private RecyclerView recycler_view;
    private ShowProspectAdaptater model;
    private ViewGroup filtresLayout;
    private ViewGroup infosProspectLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);

        Intent i = getIntent();
        this.lEmployee = i.getParcelableExtra("employee");

        setContentView(R.layout.activity_menu);
        interfaceClickable = new onClickInterface() {
            TextView nomInfos = (TextView) findViewById(R.id.nomProspect);
            TextView prenomInfos = (TextView) findViewById(R.id.prenomProspect);
            TextView raisonSocialeInfos = (TextView) findViewById(R.id.raisonSocialeProspect);
            TextView sirenInfos = (TextView) findViewById(R.id.sirenProspect);
            TextView mailInfos = (TextView) findViewById(R.id.mailProspect);
            TextView telephoneInfos = (TextView) findViewById(R.id.telephoneProspect);
            TextView noteInfos = (TextView) findViewById(R.id.noteProspect);
            boolean visible;

            @Override
            public void setClick(int position, List<Prospect> lesProspects) {
                Prospect prospect = lesProspects.get(position);

                nomInfos.setText(prospect.getNom());
                prenomInfos.setText(prospect.getPrenom());
                raisonSocialeInfos.setText(prospect.getRaisonSocial());
                sirenInfos.setText(String.valueOf(prospect.getSiret()));
                mailInfos.setText(prospect.getMail());
                telephoneInfos.setText(prospect.getTel());
                noteInfos.setText(String.valueOf(prospect.getNotes()));

                filtresLayout.setVisibility(View.GONE);
                TransitionManager.beginDelayedTransition(infosProspectLayout);
                infosProspectLayout.setVisibility(View.VISIBLE);
            }
        };

        /* Cr??ation des variables pour lier les layouts au menu*/
        RelativeLayout RelativeLayout = findViewById(R.id.menuLayout);

        /* Animation de couleurs en fond de page */
        AnimationDrawable animationDrawable = (AnimationDrawable) RelativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        /* S??lection des bouton par leur ID puis on les lies ?? des ??v??nements qui vont ??tre appel??s quand on clique. */
        btnAjouter = (Button) findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(eventBtnAjouter);

        btnRechercherVisibility = (Button) findViewById(R.id.btnRechercherVisibility);
        btnRechercherVisibility.setOnClickListener(eventBtnRechercherVisibility);

        btnRechercher = (Button) findViewById(R.id.btnRechercher);
        btnRechercher.setOnClickListener(eventBtnRechercher);

        btnSynchroniser = (Button) findViewById(R.id.btnSynchroniser);
        btnSynchroniser.setOnClickListener(eventBtnSynchroniser);

        /* Lien entre les ??l??ments de l'xml et les variables cr????es aux d??but */
        filtresLayout = findViewById(R.id.filtresLayout);
        infosProspectLayout = findViewById(R.id.infosProspectLayout);

        nomFiltre = findViewById(R.id.nomFiltre);
        prenomFiltre = findViewById(R.id.prenomFiltre);
        entrepriseFiltre = findViewById(R.id.entrepriseFiltre);


        /* table des prospects */
        recycler_view = findViewById(R.id.recycler_view);
        setRecyclerView(dataBase.getProspectBdd().getProspects(null, null, null, true));
    }

    /**
     * Permet de synchroniser les donn??es du serveur avec l'utilisateur et les prospects qu'il
     * a possiblement ajout??.
     */

    public View.OnClickListener eventBtnSynchroniser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = new Date();
            String dateMiseAjour = formatter.format(date);

            ApiBdd api = new ApiBdd();
            api.callWebService("getAllProspects.php?date=" + lEmployee.getDateMiseAjour().replace(":", "!"));
            JSONArray json = api.getJsonData();
            updateBddProspects(json);

            ArrayList<Prospect> lesProspects = dataBase.getProspectBdd().getProspects(null, null, null, false);

            if (lesProspects != null) {
                JSONArray jsonProspects = api.createJsonProspects(lesProspects);
                api.postJsonProspect("insertProspect.php?date=" + lEmployee.getDateMiseAjour().replace(":", "!"), jsonProspects.toString());
            }
            lEmployee.setDateMiseAjour(dateMiseAjour);
            dataBase.getEmployeeBdd().update(lEmployee);

            boiteMessage(api.getResult());
            setRecyclerView(dataBase.getProspectBdd().getProspects(null, null, null, true));
        }
    };

    /**
     * Permet d'acc??der ?? la page d'ajout de prospect.
     */
    public View.OnClickListener eventBtnAjouter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent ajoutProspect = new Intent(MenuActivity.this, AjoutProspectActivity.class);
            ajoutProspect.putExtra("employee", lEmployee);
            startActivity(ajoutProspect);
        }
    };

    /**
     * La m??thode passe le bloc de recherche en visible.
     */

    public View.OnClickListener eventBtnRechercherVisibility = new View.OnClickListener() {
        boolean visible;

        @Override
        public void onClick(View v) {
            infosProspectLayout.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(filtresLayout);
            filtresLayout.setVisibility(View.VISIBLE);
        }
    };

    /**
     * On recherche les prospects par rapports aux crit??res entr??s dans les EditText par l'utilisateur
     * et en passant par des requ??tes SQL ?? l'int??rieur de la m??thode 'getProspects' provenant de
     * la classe 'ProspectBDD'.
     */

    public View.OnClickListener eventBtnRechercher = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<Prospect> lesProspects = dataBase.getProspectBdd().getProspects(nomFiltre.getText().toString(),
                    prenomFiltre.getText().toString(),
                    entrepriseFiltre.getText().toString(),
                    true);

            setRecyclerView(lesProspects);
        }
    };

    /**
     * Permet de supprimer les crit??res ajout??s et de r??-affich??s les prospects de base.
     */

    public void eventBtnClearFiltres(View v) {

        nomFiltre.setText("");
        prenomFiltre.setText("");
        entrepriseFiltre.setText("");

        ArrayList<Prospect> lesProspects = dataBase.getProspectBdd().getProspects(null, null, null, true);
        setRecyclerView(lesProspects);
    }

    ;

    /**
     * Met ?? jour la base de donn??es de la table prospect
     *
     * @param jsonData JSONArray : tableau de prospect au format json
     */
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
                    p.setSiret((json.getLong("siret")));
                    p.setRaisonSocial((json.getString("raisonsocial")));
                    p.setIsUpdate(true);

                    if (dataBase.getProspectBdd().getProspects(p.getNom(), p.getPrenom(), p.getRaisonSocial(), true) == null) {
                        dataBase.getProspectBdd().add(p);
                    } else {
                        dataBase.getProspectBdd().update(p);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Envoi une popup ?? l'utilisateur
     *
     * @param msg : le message ?? afficher
     */
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

    /**
     * Mise en place de l'affichage des prospects.
     */

    private void setRecyclerView(ArrayList<Prospect> lesProspects) {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        model = new ShowProspectAdaptater(this, lesProspects, interfaceClickable);
        recycler_view.setAdapter(model);
    }
}