package fr.prospectsmanagement.api;

import android.provider.Settings;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.NoRouteToHostException;
import java.net.URL;

public class ApiBdd {
    String ApiURL = "http://192.168.43.198/api/ContactsListe.php";
    String result = "";
    String deviceId = "xxxxx";
    final String tag = "Your Logcat tag: ";

    public ApiBdd() {}

    public String callWebService() {
        String res = "";
        try {

            URL url = new URL(ApiURL);
            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String stringBuffer;
            String string = "";
            while ((stringBuffer = bufferedReader.readLine()) != null) {
                string = String.format("%s%s", string, stringBuffer);
            }
            bufferedReader.close();
            result = string;*/
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            byte[] buffer = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int bytesRead = 0;
            while((bytesRead = bis.read(buffer)) > 0) {
                String text = new String(buffer, 0, bytesRead);
                sb.append(text);
            }
            bis.close();

            System.out.println("REST API " + sb);

            res = "Récupération réussi";

        } catch (NoRouteToHostException e) {
            res = "Aucune connexion";

        } catch (Exception e) {
            res = " API erreur";
            e.printStackTrace();
        }finally {
            return res;
        }
    }
}
