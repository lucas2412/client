import java.net.URL;

public class WarehouseREST {

    public String getItemStock(int wid, int iid){
        String json = null;
        try{
            URL url = new URL(Connection.urlstart + String.format("warehouse/getStock?wid=%s&iid=%s", wid, iid));
     //       System.out.println(url);
            json = Connection.sendHTTPRequest(url, "GET");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        return json;
    }

    public void reduceStock(String json){

        try{
            URL url = new URL(Connection.urlstart + String.format("warehouse/reduceStock"));
       //     System.out.println(url);
            Connection.makeUpdate(json,url);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    public void addStock(String json){

        try{
            URL url = new URL(Connection.urlstart + String.format("warehouse/addStock"));
         //   System.out.println(url);
            Connection.makeUpdate(json,url);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }


}
