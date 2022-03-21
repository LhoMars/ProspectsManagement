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
import androidx.constraintlayout.widget.ConstraintLayout;

import fr.prospectsmanagement.api.ApiBdd;
import fr.prospectsmanagement.dataBase.DaoSQL;

public class MenuActivity extends AppCompatActivity {
    private DaoSQL dataBase;
    public Button btnAjouter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);

        ApiBdd api = new ApiBdd();
        boiteMessage(api.getResult());

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