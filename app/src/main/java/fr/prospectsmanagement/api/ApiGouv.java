package fr.prospectsmanagement.api;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.NoRouteToHostException;
import java.net.URL;

public class ApiGouv {
    public String apiURL = "https://entreprise.data.gouv.fr/api/sirene/v1/full_text/";
    String resultApi;
    String responseApi;

    public ApiGouv(){
    }

    public int getSiretWithName(String nom){
        try {
            URL url = new URL(apiURL+nom);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String stringBuffer;
            responseApi = "";
            while ((stringBuffer = bufferedReader.readLine()) != null) {
                responseApi = String.format("%s%s", responseApi, stringBuffer);
            }
            bufferedReader.close();

            resultApi = "Récupération réussi ";
        } catch (NoRouteToHostException e) {
            resultApi = "Aucune connexion au serveur";
        } catch (Exception e) {
            resultApi = "Erreur API";
            e.printStackTrace();
        }
        return siretWithJson(responseApi);
    }
    private int siretWithJson(String json){
        int res = 0;
        try {
            JSONObject reader = new JSONObject(json);
            JSONObject etablissement = reader.getJSONObject("etablissement");
            res = etablissement.getInt("l1_normalisee");
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
