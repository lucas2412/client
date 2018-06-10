import java.net.MalformedURLException;
import java.net.URL;

public class CustomerREST {

    public String create(String inputJSON, String object){
        String json = "";
        try{
            URL url = new URL(Connection.urlstart + String.format(object));
            System.out.println(url);
            json = Connection.makePOST(inputJSON,url);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        return json;
    }

    public String getCustomer(String id){
        String json = null;
        try{
            URL url = new URL(Connection.urlstart + String.format("customer/get/%s", id));
            json = Connection.sendHTTPRequest(url, "GET");
            System.out.println(url);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        return json;
    }


    public void updateCustomer(int id, String json) throws MalformedURLException {
        try{
            URL url = new URL(Connection.urlstart + "customer/"+ id );
            Connection.makeUpdate(json, url);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    public void deleteCustomer(int id) throws MalformedURLException {
        try{
            URL url = new URL(Connection.urlstart + "customer/"+ id );
            Connection.sendHTTPRequest(url, "DELETE");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }


}
