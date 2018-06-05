import java.net.URL;

public class CustomerREST {

    public String createCustomer(String stadt, String straße, String plz, String tel){
        String json = null;
        try{
            URL url = new URL(Connection.urlstart + String.format("customer/create?stadt=%s&strasse=%s&plz=%s&tel=%s", stadt, straße, plz, tel));
            System.out.println(url);
            json = Connection.sendHTTPRequest(url, "GET");
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

    public void deleteCustomer(String id){
        try{
            URL url = new URL(Connection.urlstart + String.format("customer/delete?id=%s", id));
            Connection.sendHTTPRequest(url, "GET");
            System.out.println("Deleted successfully");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    public void updateCustomer(int id, String stadt, String straße, String plz, String tel){
        String updateString = "";
        try{
            if (stadt != null){
                updateString += "&stadt="+stadt;
            }
            if (straße != null){
                updateString += "&strasse="+straße;
            }
            if (plz != null){
                updateString += "&plz"+plz;
            }
            if (tel != null){
                updateString += "&tel="+tel;
            }

            URL url = new URL(Connection.urlstart + "customer/update?id="+id +  updateString);
            Connection.sendHTTPRequest(url, "GET");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    public String getName(){
        return getClass().getName();
    }


}
