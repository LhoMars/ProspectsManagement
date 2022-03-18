package fr.prospectsmanagement.api;

import android.provider.Settings;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.URL;

public class ApiBdd {
    String ApiURL = "http://192.168.43.198/api/ContactsListe.php";
    String result = "";
    String deviceId = "xxxxx";
    final String tag = "Your Logcat tag: ";

    public ApiBdd() {
    }

    public String callWebService() {
        String res = "";
        try {

            URL url = new URL(ApiURL);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String stringBuffer;
            String string = "";
            while ((stringBuffer = bufferedReader.readLine()) != null) {
                string = String.format("%s%s", string, stringBuffer);
            }
            bufferedReader.close();
            result = string;

            res = "Récupération réussi : " + result;

        } catch (NoRouteToHostException e) {
            res = "Aucune connexion";
            e.printStackTrace();
        } catch (Exception e) {
            res = "Erreur API";
            e.printStackTrace();
        } finally {
            //urlConnection.disconnect();
            return res;
        }
    }
}
