import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
}
