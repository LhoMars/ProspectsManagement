package fr.prospectsmanagement;

import android.os.Parcel;
import android.os.Parcelable;
import org.mindrot.jbcrypt.BCrypt;

public class Employee implements Parcelable {
    private String identifiant;
    private String password;

    public Employee(String identifiant, String password) {
        this.identifiant = identifiant;
        this.password = hashPassword(password);
    }
    public Employee(){
        this("","");
    }

    protected Employee(Parcel in) {
        identifiant = in.readString();
        password = in.readString();
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

    public String getIdentifiant() {
        return identifiant;
    }
    public String getPassword() {
        return password;
    }
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "identifiant='" + identifiant + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(identifiant);
        dest.writeString(password);

    }
}
