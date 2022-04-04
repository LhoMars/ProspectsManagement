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

    public ApiGouv() {
    }

    /**
     * Récupère le siret d'une entreprise avec son nom
     * @param nom String : la dénomination de l'entreprise
     * @return long : le siret de l'entreprise
     */
    public long getSiretWithName(String nom) {
        try {
            URL url = new URL(apiURL + nom);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String stringBuffer;
            responseApi = "";
            while ((stringBuffer = bufferedReader.readLine()) != null) {
                responseApi = String.format("%s%s", responseApi, stringBuffer);
            }
            bufferedReader.close();

            resultApi = "Récupération siret";
        } catch (NoRouteToHostException e) {
            resultApi = "Aucune connexion au serveur";
        } catch (Exception e) {
            resultApi = "Erreur API";
            e.printStackTrace();
        }
        return siretWithJson(responseApi);
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

    public String getResultApi() {
        return resultApi;
    }
}
