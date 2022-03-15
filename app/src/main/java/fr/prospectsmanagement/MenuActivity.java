package fr.prospectsmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.RelativeLayout;

import fr.prospectsmanagement.dataBase.DaoSQL;

public class MenuActivity extends AppCompatActivity {
    private DaoSQL dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);

        Entreprise e = new Entreprise("uneNom", 0);
        Prospect p = new Prospect("nom", "prenom", "tel", "mail", 1, e);
        dataBase.getProspectBdd().addProspectBdd(p);

        setContentView(R.layout.activity_menu);

        RelativeLayout RelativeLayout = findViewById(R.id.menuLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) RelativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }
}