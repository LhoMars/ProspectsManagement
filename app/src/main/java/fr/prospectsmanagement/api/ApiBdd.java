package fr.prospectsmanagement.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.URL;

public class ApiBdd {
    String ApiURL = "http://192.168.43.198/api/api.php?name=";
    String result = "";
    String deviceId = "xxxxx";
    final String tag = "Your Logcat tag: ";

    public ApiBdd() {
        callWebService("pen");
    }

    public void callWebService(String q) {
        try {
            URL url = new URL(ApiURL + q);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String stringBuffer;
            String string = "";
            while ((stringBuffer = bufferedReader.readLine()) != null){
                string = String.format("%s%s", string, stringBuffer);
            }
            bufferedReader.close();
            result = string;

        } catch (NoRouteToHostException e) {
        }catch (Exception e){
            System.out.println(" API erreur :");
            e.printStackTrace();
        }
    }
}
