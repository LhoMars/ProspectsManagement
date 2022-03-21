package fr.prospectsmanagement.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.NoRouteToHostException;
import java.net.URL;

public class ApiBdd {
    final String ApiURL = "http://192.168.43.198/api/ProspectApi.php";
    String resultApi;
    String responseApi;
    Boolean access;

    public ApiBdd() {
        callWebService("connexionBase");
    }

    public void callWebService(String q) {
        try {
            URL url = new URL(ApiURL + "?function=" + q);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String stringBuffer;
            responseApi = "";
            while ((stringBuffer = bufferedReader.readLine()) != null) {
                responseApi = String.format("%s%s", responseApi, stringBuffer);
            }
            bufferedReader.close();

            resultApi = "Récupération réussi ";
            access = true;
        } catch (NoRouteToHostException e) {
            resultApi = "Aucune connexion au serveur";
            access = false;
        } catch (Exception e) {
            resultApi = "Erreur API";
            access = false;
            e.printStackTrace();
        }
    }

    public JSONArray getJsonData() {
        JSONArray data = new JSONArray();
        if (access) {
            try {
                JSONObject jsonObject = new JSONObject(responseApi.substring(responseApi.indexOf("{"), responseApi.lastIndexOf("}") + 1));
                data = jsonObject.getJSONArray("data");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public String getResultApi() {
        return resultApi;
    }
}
