import java.net.URL;

public class ItemREST {
/*
    public void createItem(String name, Double preis ){
        try{
            URL url = new URL(Connection.urlstart + String.format("item/create?name=%s&preis=%s", name, preis));
      //      System.out.println(url);
            Connection.sendHTTPRequest(url, "GET");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    public void getItem(String id){
        try{
            URL url = new URL(Connection.urlstart + String.format("item/get/%s", id));
        //    Connection.sendHTTPRequest(url, "GET");
            System.out.println(url);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    public void deleteItem(String id){
        try{
            URL url = new URL(Connection.urlstart + String.format("item/delete?id=%s", id));
       //     Connection.sendHTTPRequest(url, "GET");
            System.out.println("Deleted successfully");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    public void updateItem(int id, String name, Double preis){
        String updateString = "";
        try{
            if (name != null){
                updateString += "&name="+name;
            }
            if (preis != null){
                updateString += "&preis="+preis.toString();
            }

            URL url = new URL(Connection.urlstart + "item/update?id="+id +  updateString);
            Connection.sendHTTPRequest(url, "GET");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    */
    public String getAllItems() {
        String json = "";
        try{
            URL url = new URL(Connection.urlstart + "item/getAll");
            json = Connection.sendHTTPRequest(url, "GET");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    return json;
    }
}
