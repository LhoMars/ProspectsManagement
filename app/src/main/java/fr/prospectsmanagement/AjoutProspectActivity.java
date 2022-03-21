package fr.prospectsmanagement;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import fr.prospectsmanagement.dataBase.DaoSQL;


public class AjoutProspectActivity extends AppCompatActivity {
    private DaoSQL dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new DaoSQL(this);

        setContentView(R.layout.activity_addprospect);
    }
}
