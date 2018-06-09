import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connection {

    public final static String urlstart = "http://127.0.0.1:8080/";

    public static String sendHTTPRequest(URL url, String method) {
        String resultJson = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Accept", "application/json");


            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            while ((output = br.readLine()) != null) {
    //            System.out.println(output);
                resultJson+=output;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    return resultJson;
    }



    public static String makePOST(String inputJson, URL url){
        String resultJson = "";
        try {

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");


            OutputStream os = conn.getOutputStream();
            os.write(inputJson.getBytes());
            os.flush();
/*
            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
*/
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
       //     System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
            //    System.out.println(output);
                resultJson+=output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return resultJson;

    }

    public static void makeUpdate(String inputJson, URL url){
        try {

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");


            OutputStream os = conn.getOutputStream();
            os.write(inputJson.getBytes());
            os.flush();
/*
            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
*/
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
         //   System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
          //      System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
