import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.*;

public class main {

    public static void main(String[] args) throws MalformedURLException {

          startBPMN();
     //   CustomerREST controller = new CustomerREST();

     //   controller.createCustomer("Mannheim", "asdasdads", "23822", "23912039", 2);
      //  controller.get("22");
    }


    public static void startBPMN() throws MalformedURLException {

        Boolean loop = true;
        CustomerREST customerController = new CustomerREST();
        ItemREST itemController = new ItemREST();
        WarehouseREST warehouseController = new WarehouseREST();
        OrderREST orderREST = new OrderREST();


        String customerAdresse = "";
        List orderItemList = new ArrayList();


        Scanner scanner = new Scanner(System.in);
        Integer customerWID = null;
        Integer customerID = null;


        System.out.println("Hallo lieber Kunde!:");
        System.out.println("Möchten Sie sich neu registrieren(1) oder haben Sie bereits ein Kundenkonto?(2)");
        String antwort = scanner.nextLine();

        switch (antwort) {
            case "1": {
                System.out.println("Hallo lieber Kunde. Bevor Sie Ihre Bestellung tätigen können, müssen Sie sich registrieren.");
                System.out.println("Bitte geben Sie zuerst Ihren Wohnort an:");
                String stadt = scanner.nextLine();
                System.out.println("Bitte geben Sie Ihre Straße an:");
                String straße = scanner.nextLine();
                System.out.println("Bitte geben Sie Ihre Postleitzahl  an:");
                String plz = scanner.nextLine();
                System.out.println("Zum Schluss benötigen wir noch Ihre Telefonnummer:");
                String tel = scanner.nextLine();

                JSONObject json = new JSONObject();
                json.put("c_TELEFONNUMMER", tel);
                json.put("c_STRASSE", straße);
                json.put("c_STADT", stadt);
                json.put("c_PLZ", plz);
                String customerJson = customerController.create(json.toString(), "customer");

                JSONObject jsonnew = new JSONObject(customerJson);
                customerWID = jsonnew.getJSONObject("customerDistrict").getInt("d_W_ID");
                customerID = jsonnew.getInt("c_ID");
                System.out.println("--------------------------------------------------");
                System.out.println("Sie haben sich erfolgreich registriert!");
                System.out.println("--------------------------------------------------");
                customerAdresse = straße + " " + plz + " " + stadt;
                break;
            }
            case "2": {
                System.out.println("Bitte geben Sie Ihre KundenID ein");
                String id = scanner.nextLine();
                customerID = Integer.parseInt(id);

                String customerJson = customerController.getCustomer(id);
                JSONObject json = new JSONObject(customerJson);
                System.out.println(json.toString());
                customerWID = json.getJSONObject("customerDistrict").getInt("d_W_ID");
                String straße = json.getString("c_STRASSE");
                String stadt = json.getString("c_STADT");
                String plz = json.getString("c_PLZ");
                customerAdresse = straße + " " + plz + " " + stadt;

                System.out.println("--------------------------------------------------");
                System.out.println("Danke, Sie haben sich erfolgreich eingeloggt");
                System.out.println("--------------------------------------------------");
                break;
            }


        }
        while (loop == true){

        System.out.println("Was möchten Sie als nächstes tun?");
        System.out.println("Bestellung aufgeben(1)");
        System.out.println("Bestellungen ansehen(2)");
        System.out.println("Adressdaten ändern(3)");
        System.out.println("Kundenkonto löschen(4)");
        System.out.println("Ausloggen(5)");
        String antwort2 = scanner.nextLine();
        switch (antwort2) {
            case "1": {
                List list = new ArrayList();
                System.out.println("--------------------------------------------------");
                System.out.println("Sie möchten also eine Bestellung aufgeben.");
                System.out.println("Nachfolgend finden Sie die Liste aller verfügbaren Items:");
                System.out.println("--------------------------------------------------");
                String json = itemController.getAllItems();
                JSONArray jarr = new JSONArray(json);
                for (int j = 0; j < jarr.length(); j++) {
                    int id = jarr.getJSONObject(j).getInt("i_ID");
                    String name = jarr.getJSONObject(j).getString("i_NAME");
                    Double preis = jarr.getJSONObject(j).getDouble("i_PRICE");

                    System.out.print("Item ID: " + id + "   ");
                    System.out.print("Itemname: " + name + "   ");
                    System.out.print("Preis: " + preis + " Euro   ");
                    System.out.println("");

                }

                boolean nochmal = true;
                while (nochmal == true) {
                    System.out.println("--------------------------------------------------");
                    System.out.println("Bitte geben Sie die ItemID ein, welche sie bestellen möchten!");
                    int itemid = scanner.nextInt();
                    System.out.println("Wieviele möchten Sie davon kaufen?.");
                    int anzahl = scanner.nextInt();
                    int bestand = Integer.parseInt(warehouseController.getItemStock(customerWID, itemid));
                    int array[] = new int[2];

                    if (bestand >= anzahl) {
                        System.out.println("Sie haben Glück!. Es sind genügend Items in ihrem Warehouse vorhanden. Der Versand kann sofort beginnen.");
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("wid",customerWID);
                        jsonObject.put("iid",itemid);
                        jsonObject.put("anzahl",anzahl);
                        String jsonString = jsonObject.toString();

                        warehouseController.reduceStock(jsonString);

                        boolean found = false;
                        for (int i = 0; i < orderItemList.size(); i++){
                            int[] a = (int[]) orderItemList.get(i);
                            if (a[0] == itemid){
                                a[1] += anzahl;
                                orderItemList.set(i,a);
                                found = true;
                            }
                        }
                        if (found == false) {
                            array[0] = itemid;
                            array[1] = anzahl;
                            orderItemList.add(array);
                        }

                    } else {
                        System.out.println("Es sind leider nicht genügend Items in Bestand. Die Items müssen zuerst an ihr Warehouse geschickt werden. Anschließend wird Ihre Bestellung versandt");
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("wid",customerWID);
                        jsonObject.put("iid",itemid);
                        jsonObject.put("anzahl",anzahl);
                        String jsonString = jsonObject.toString();
                        warehouseController.addStock(jsonString);
                        warehouseController.reduceStock(jsonString);

                        boolean found = false;
                        for (int i = 0; i < orderItemList.size(); i++){
                            int[] a = (int[]) orderItemList.get(i);
                            if (a[0] == itemid){
                                a[1] += anzahl;
                                orderItemList.set(i,a);
                                found = true;
                            }
                        }
                        if (found == false) {
                            array[0] = itemid;
                            array[1] = anzahl;
                            orderItemList.add(array);
                        }
                    }
                    System.out.println("Möchten Sie noch weitere Items zu Ihrer Bestellung hinzufügen? (Ja(1)/Nein(2)");
                    int ant = scanner.nextInt();
                    if (ant == 2) {
                        nochmal = false;
                    }
                }

                System.out.println("--------------------------------------------------");
                System.out.println("Vielen Dank für folgende Bestellung!");
                for (int i = 0; i < orderItemList.size(); i++) {
                    int[] array = (int[]) orderItemList.get(i);
                    System.out.println("ItemID: " + array[0] + " Menge: " + array[1]);
                }
                System.out.println("--------------------------------------------------");
                System.out.println("Die Bestellung wird an folgende Adresse versendet: ");
                System.out.println(customerAdresse);

                System.out.println("--------------------------------------------------");
                System.out.println("Speichere Order in Datenbank!");

                String orderjson = orderREST.createOrder(customerID, orderItemList.size());
                JSONObject ojson = new JSONObject(orderjson);
                int orderid = ojson.getInt("o_ID");

                System.out.println("Order erfolgreich gespeichert");
                System.out.println("--------------------------------------------------");
                System.out.println("Speichere Items in Orderlines");
                for (int i = 0; i < orderItemList.size(); i++) {
                    int[] array = (int[]) orderItemList.get(i);
                    orderREST.createOrderline(array[1], orderid, array[0]);
                }
                System.out.println("Orderlines erfolgreich gespeichert");
                System.out.println("--------------------------------------------------");

                break;
            }
            case "2": {
                System.out.println("--------------------------------------------------");
                System.out.println("Hier eine Übersicht über alle getätigeten Bestellungen von Customer mit der ID: " + customerID);
                System.out.println("--------------------------------------------------");
                String oderList = orderREST.getAllOrders(customerID);
                JSONArray jarr = new JSONArray(oderList);
                for (int j = 0; j < jarr.length(); j++) {
                    Integer oid = jarr.getJSONObject(j).getInt("o_ID");
                    Integer cid = jarr.getJSONObject(j).getInt("o_C_ID");
                    Integer orderLines = jarr.getJSONObject(j).getInt("o_OL_CNT");

                    if (customerID == cid) {
                        System.out.print("Order ID " + oid + "   ");
                        System.out.print("Orderlines: " + orderLines + "   ");
                        System.out.println("");
                    }
                }
                System.out.println("--------------------------------------------------");


                break;
            }

            case "3": {
                System.out.println("Sie wollen Ihre Adressdaten aktualisieren:");
                System.out.println("--------------------------------------------------");
                System.out.println("Bitte geben Sie die neue Straße ein");
                String straße = scanner.nextLine();
                System.out.println("Bitte geben Sie die neue Stadt ein");
                String stadt = scanner.nextLine();
                System.out.println("Bitte geben Sie die neue Postleitzahl ein");
                String plz = scanner.nextLine();
                System.out.println("Bitte geben Sie die neue Telefonnummer ein");
                String tel = scanner.nextLine();

                JSONObject json = new JSONObject();
                json.put("c_TELEFONNUMMER", tel);
                json.put("c_STRASSE", straße);
                json.put("c_STADT", stadt);
                json.put("c_PLZ", plz);
                customerController.updateCustomer(customerID, json.toString());
                System.out.println("--------------------------------------------------");
                System.out.println("Adressdaten erfolgreich aktualisert!");
                System.out.println("--------------------------------------------------");
                break;
            }

            case "4": {
                System.out.println("Sie wollen ihr Kundenkonto löschen!");
                System.out.println("Sind Sie sicher? Dann bitte ja eingeben");
                String a = scanner.nextLine();
                if (a.equals("ja")) {
                    customerController.deleteCustomer(customerID);
                    System.out.println("--------------------------------------------------");
                    System.out.println("Ihr Konto wurde erfolgreich gelöscht!");
                    loop = false;
                    break;
                }

                break;
            }

            case "5": {
                System.out.println("Sie haben sich ausgeloggt!.");
                loop = false;
                break;
            }
        }

        }
    }
}

