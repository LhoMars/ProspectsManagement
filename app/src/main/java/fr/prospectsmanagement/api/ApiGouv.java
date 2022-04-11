package fr.prospectsmanagement.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.URL;

public class ApiGouv {
    public String url = "https://entreprise.data.gouv.fr/api/sirene/v1/full_text/";
    String result;
    String response;

    public ApiGouv() {
    }

    /**
     * Récupère le siret d'une entreprise avec son nom
     *
     * @param nom String : la dénomination de l'entreprise
     * @return long : le siret de l'entreprise
     */
    public long getSiretWithName(String nom) {
        try {
            URL url = new URL(this.url + nom);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String stringBuffer;
            response = "";
            while ((stringBuffer = bufferedReader.readLine()) != null) {
                response = String.format("%s%s", response, stringBuffer);
            }
            bufferedReader.close();

            result = (urlConnection.getResponseCode() == 200) ? "Récupération siret réussi" : "Erreur API";
            urlConnection.disconnect();
        } catch (NoRouteToHostException e) {
            result = "Aucune connexion au serveur";
        } catch (Exception e) {
            result = "Erreur API";
            e.printStackTrace();
        }
        return siretWithJson(response);
    }

    private long siretWithJson(String json) {
        long res = 0;
        try {
            JSONObject reader = new JSONObject(json);
            //JSONObject etablissement = reader.getJSONObject("etablissement");
            JSONArray etablissements = reader.getJSONArray("etablissement");
            JSONObject etablissement = (JSONObject) etablissements.get(0);
            res = etablissement.getLong("siret");
        } catch (Exception e) {
            System.out.println("Erreur Json Gouv");
            e.printStackTrace();
        }
        return res;
    }

    public String getResult() {
        return result;
    }
}
