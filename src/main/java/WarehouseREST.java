import java.net.URL;

public class WarehouseREST {

    public String getItemStock(int wid, int iid){
        String json = null;
        try{
            URL url = new URL(Connection.urlstart + String.format("warehouse/getStock?wid=%s&iid=%s", wid, iid));
            System.out.println(url);
            json = Connection.sendHTTPRequest(url, "GET");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        return json;
    }

    public String reduceStock(int wid, int iid, int anzahl){
        String json = null;
        try{
            URL url = new URL(Connection.urlstart + String.format("warehouse/reduceStock?wid=%s&iid=%s&anzahl=%s", wid, iid, anzahl));
            System.out.println(url);
            json = Connection.sendHTTPRequest(url, "GET");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        return json;
    }

    public String addStock(int wid, int iid, int anzahl){
        String json = null;
        try{
            URL url = new URL(Connection.urlstart + String.format("warehouse/addStock?wid=%s&iid=%s&anzahl=%s", wid, iid, anzahl));
            System.out.println(url);
            json = Connection.sendHTTPRequest(url, "GET");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        return json;
    }


}
