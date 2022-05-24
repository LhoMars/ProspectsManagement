package fr.prospectsmanagement.api;

import fr.prospectsmanagement.model.Prospect;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Cette classe fournit toutes les méthodes
 * pour effectuer des requêtes au serveur API
 */
public class ApiBdd {
    final String url = "http://172.31.1.70/apiAndroidProspects/";
    String result;
    String response;

    public ApiBdd() {
    }

    /**
     * Appel un service de l'api
     *
     * @param q String : service à appeler
     */
    public void callWebService(String q) {
        try {
            URL url = new URL(this.url + q);
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

            result = (urlConnection.getResponseCode() == 200) ? "Récupération réussi" : "Erreur API";
            urlConnection.disconnect();
        } catch (NoRouteToHostException e) {
            result = "Aucune connexion au serveur";
        } catch (Exception e) {
            result = "Erreur API";
            e.printStackTrace();
        }
    }

    /**
     * Envoi des données à l'api
     *
     * @param q    String : service api à envoyer
     * @param data String : la liste des Prospects au format Json string
     */
    public void postJsonProspect(String q, String data) {
        OutputStream out = null;

        try {
            URL url = new URL(this.url + q);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            out = new BufferedOutputStream(urlConnection.getOutputStream());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            out.close();

            result = (urlConnection.getResponseCode() == 200) ? "Récupération réussi" : "Erreur API";
            urlConnection.disconnect();
        } catch (NoRouteToHostException e) {
            result = "Aucune connexion au serveur";
        } catch (Exception e) {
            result = "Erreur API";
            e.printStackTrace();
        }
    }

    /**
     * Récupère les données de la réponse de l'api
     *
     * @return JSONArray : les données récupérées
     */
    public JSONArray getJsonData() {
        JSONArray data = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
            data = jsonObject.getJSONArray("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Formate une liste de prospect en json
     *
     * @param lesProspects ArrayList : la liste de prospects
     * @return JsonArray : les prospects au format json
     */
    public JSONArray createJsonProspects(ArrayList<Prospect> lesProspects) {
        //Creating a JSONObject object
        JSONArray jsonArray = new JSONArray();

        try {
            for (Prospect prospect : lesProspects) {
                JSONObject jsonObject = new JSONObject();
                //Inserting key-value pairs into the json object
                jsonObject.put("id", "default");
                jsonObject.put("nom", prospect.getNom());
                jsonObject.put("prenom", prospect.getPrenom());
                jsonObject.put("mail", prospect.getMail());
                jsonObject.put("tel", prospect.getTel());
                jsonObject.put("note", prospect.getNotes());
                jsonObject.put("siret", prospect.getSiret());
                jsonObject.put("raisonsocial", prospect.getRaisonSocial());

                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public String getResult() {
        return result;
    }
}
