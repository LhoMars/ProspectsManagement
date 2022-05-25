package fr.prospectsmanagement.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.mindrot.jbcrypt.BCrypt;

/**
 * La classe Employee implement Parcelable
 * ce qui lui permet la navigabilité
 * entre les différentes activités
 */
public class Employee implements Parcelable {
    private String identifiant;
    private String password;
    private String dateMiseAjour;

    public Employee(String identifiant, String password, String dateMiseAjour) {
        this.identifiant = identifiant;
        this.password = hashPassword(password);
        this.dateMiseAjour = dateMiseAjour;
    }

    public Employee() {
        this("", "", "");
    }

    protected Employee(Parcel in) {
        identifiant = in.readString();
        password = in.readString();
        dateMiseAjour = in.readString();
    }

    public boolean checkPassword(String mdp) {
        return BCrypt.checkpw(mdp, this.password);
    }

    public String hashPassword(String p) {
        return BCrypt.hashpw(p, BCrypt.gensalt(12));
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(identifiant);
        dest.writeString(password);
        dest.writeString(dateMiseAjour);
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getPassword() {
        return password;
    }

    public String getDateMiseAjour() {
        return dateMiseAjour;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateMiseAjour(String dateMiseAjour) {
        this.dateMiseAjour = dateMiseAjour;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "identifiant='" + identifiant + '\'' +
                ", password='" + password + '\'' +
                ", dateMiseAjour='" + dateMiseAjour + '\'' +
                '}';
    }
}
