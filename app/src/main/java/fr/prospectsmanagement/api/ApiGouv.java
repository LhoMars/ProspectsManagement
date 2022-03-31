package fr.prospectsmanagement.api;

import org.json.JSONArray;
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

    public long getSiretWithName(String nom){
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
    private long siretWithJson(String json){
        long res = 0;
        try {
            JSONObject reader = new JSONObject(json);
            //JSONObject etablissement = reader.getJSONObject("etablissement");
            JSONArray etablissements = reader.getJSONArray("etablissement");
            System.out.println("array : " + etablissements);

            JSONObject etablissement = (JSONObject) etablissements.get(0);
            System.out.println("JSON eta : " + etablissement);

            res = etablissement.getLong("siret");
            System.out.println("JSON GOUV : " + res);
        }catch(Exception e){
            System.out.println("Erreur Json Gouv");
            e.printStackTrace();
        }

        return res;
    }
}
