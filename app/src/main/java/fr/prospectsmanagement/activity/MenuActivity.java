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

import fr.prospectsmanagement.activity.template.LoadingDialog;
import fr.prospectsmanagement.model.Prospect;
import fr.prospectsmanagement.R;
import fr.prospectsmanagement.api.ApiBdd;
import fr.prospectsmanagement.dataBase.DaoSQL;
import fr.prospectsmanagement.activity.template.ShowProspectAdaptater;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private DaoSQL dataBase;
    private LoadingDialog loading;
    private Button btnAjouter = null;
    private Button btnRechercherVisibility = null;
    private Button btnRechercher = null;
    private Button btnSynchroniser = null;
    private Button btnClearFiltres = null;
    private EditText nomFiltre = null;
    private EditText prenomFiltre = null;
    private EditText entrepriseFiltre = null;

    RecyclerView recycler_view;
    ShowProspectAdaptater model;
    ViewGroup filtresLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);
        loading = new LoadingDialog(this);

        setContentView(R.layout.activity_menu);

        RelativeLayout RelativeLayout = findViewById(R.id.menuLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) RelativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        /* Sélection des bouton par leur ID puis on les lies à des événements qui vont être appelés quand on clique. */
        btnAjouter = (Button) findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(eventBtnAjouter);

        btnRechercherVisibility = (Button) findViewById(R.id.btnRechercherVisibility);
        btnRechercherVisibility.setOnClickListener(eventBtnRechercherVisibility);

        btnRechercher = (Button) findViewById(R.id.btnRechercher);
        btnRechercher.setOnClickListener(eventBtnRechercher);

        btnClearFiltres = (Button) findViewById(R.id.btnClearFiltres);
        btnClearFiltres.setOnClickListener(eventBtnClearFiltres);

        btnSynchroniser = (Button) findViewById(R.id.btnSynchroniser);
        btnSynchroniser.setOnClickListener(eventBtnSynchroniser);

        /* Lien entre les éléments de l'xml et les variables créées aux début */
        filtresLayout = findViewById(R.id.filtresLayout);

        nomFiltre = findViewById(R.id.nomFiltre);
        prenomFiltre = findViewById(R.id.prenomFiltre);
        entrepriseFiltre = findViewById(R.id.entrepriseFiltre);


        /* TABLE */
        recycler_view = findViewById(R.id.recycler_view);
        setRecyclerView(dataBase.getProspectBdd().getProspect(null,null,null));
    }

    public View.OnClickListener eventBtnSynchroniser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loading.startLoadingDialog();

            ApiBdd api = new ApiBdd();
            api.callWebService("getAllProspects");
            JSONArray json = api.getJsonData();
            updateBddProspects(json);

            ArrayList<Prospect> lesProspects = dataBase.getProspectBdd().getProspect(null, null, null);//getAllProspects();
            JSONArray jsonProspects = api.createJsonProspects(lesProspects);
            api.postJsonProspect("InsertProspect", jsonProspects.toString());

            loading.dismissDialog();
            boiteMessage(api.getResultApi());
            setRecyclerView(dataBase.getProspectBdd().getProspect(null,null,null));
        }
    };

    public View.OnClickListener eventBtnAjouter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent AjoutProspect = new Intent(MenuActivity.this, AjoutProspectActivity.class);
            startActivity(AjoutProspect);
        }
    };

    public View.OnClickListener eventBtnRechercherVisibility = new View.OnClickListener() {
        boolean visible;

        @Override
        public void onClick(View v) {
            TransitionManager.beginDelayedTransition(filtresLayout);
            visible = !visible;
            filtresLayout.setVisibility(visible ? View.VISIBLE: View.GONE);
        }
    };

    public View.OnClickListener eventBtnRechercher = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ArrayList<Prospect> lesProspects = dataBase.getProspectBdd().getProspect(nomFiltre.getText().toString(),
                    prenomFiltre.getText().toString(),
                    entrepriseFiltre.getText().toString());

            setRecyclerView(lesProspects);
        }
    };

    public View.OnClickListener eventBtnClearFiltres = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    /**
     * Met à jour la base de données de la table prospect
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

                    if (dataBase.getProspectBdd().getProspect(p.getNom(), p.getPrenom(), p.getRaisonSocial()) == null) {
                        dataBase.getProspectBdd().addProspectBdd(p);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Envoi une popup à l'utilisateur
     * @param msg : le message à afficher
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

    private void setRecyclerView(ArrayList<Prospect> lesProspects) {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        model = new ShowProspectAdaptater(this, lesProspects);
        recycler_view.setAdapter(model);
    }
}