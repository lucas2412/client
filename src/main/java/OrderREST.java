import org.json.JSONObject;

import java.net.URL;

/**
 * Created by Lucas on 06.06.2018.
 */
public class OrderREST {

    public String createOrder(int cid, int orderLineAmount) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cid", cid);
        jsonObject.put("olAmount", orderLineAmount);
        String jsonString = jsonObject.toString();

        String resultJson = null;
        try{
            URL url = new URL(Connection.urlstart + String.format("order"));
            System.out.println(url);
            resultJson = Connection.makePOST(jsonString, url);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        return resultJson;
    }

    public String createOrderline(int amount, int o_ID, int i_ID) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", amount);
        jsonObject.put("oid", o_ID);
        jsonObject.put("iid", i_ID);
        String jsonString = jsonObject.toString();

        String resultJson = null;
        try{
            URL url = new URL(Connection.urlstart + String.format("orderline"));
            System.out.println(url);
            resultJson = Connection.makePOST(jsonString, url);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        return resultJson;
    }

    public String getAllOrders(int c_ID) {

        String json = null;
        try{
            URL url = new URL(Connection.urlstart + String.format("order/getAll?cid=%s", c_ID));
            System.out.println(url);
            json = Connection.sendHTTPRequest(url, "GET");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        return json;
    }


}
