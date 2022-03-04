package fr.prospectsmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import fr.prospectsmanagement.dataBase.DaoSQL;

public class LoginActivity extends AppCompatActivity {
    private DaoSQL database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new DaoSQL(this);
        Employee e = new Employee("user","password");
        database.getEmployeeBdd().addemployeeBdd(e);

        setContentView(R.layout.activity_login);
    }
}