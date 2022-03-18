package fr.prospectsmanagement.api;

import android.provider.Settings;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.URL;

public class ApiBdd {
    final String ApiURL = "http://192.168.43.198/api/ProspectApi.php";
    String result = "";

    public ApiBdd() {
        callWebService("connexionBase");/*
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject newJSON = jsonObject.getJSONObject("data");
            System.out.println(newJSON.toString());
            jsonObject = new JSONObject(newJSON.toString());
            System.out.println(jsonObject.getString("rcv"));
            System.out.println(jsonObject.getJSONArray("argv"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void callWebService(String q) {
        try {
            URL url = new URL(ApiURL + "?function=" + q);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String stringBuffer;
            String string = "";
            while ((stringBuffer = bufferedReader.readLine()) != null) {
                string = String.format("%s%s", string, stringBuffer);
            }
            bufferedReader.close();
            result = "Récupération réussi ";
        } catch (NoRouteToHostException e) {
            result = "Aucune connexion au serveur";
        } catch (Exception e) {
            result = "Erreur API";
            e.printStackTrace();
        }
    }

    public String getResult() {
        return result;
    }
}
