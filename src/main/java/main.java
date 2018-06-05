import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {

         startBPMN();
     //   CustomerREST controller = new CustomerREST();

     //   controller.createCustomer("Mannheim", "asdasdads", "23822", "23912039", 2);
      //  controller.get("22");
    }


    public static void startBPMN() {
        CustomerREST customerController = new CustomerREST();
        ItemREST itemController = new ItemREST();
        WarehouseREST warehouseController = new WarehouseREST();
        String customerAdresse = "";
        List orderItemList = new ArrayList();


        Scanner scanner = new Scanner(System.in);
        Integer customerWID = null;

        System.out.println("Hallo lieber Kunde!:");
        System.out.println("Möchten Sie sich neu registrieren(1) oder haben Sie bereits ein Kundenkonto?(2)");
        String antwort = scanner.nextLine();
        switch (antwort) {
            case "1": {
                System.out.println("Hallo lieber Kunde. Bevor Sie Ihre Bestellung tätigen können, müssen Sie sich registrieren.");
                System.out.println("Bitte geben Sie zuerst Ihren Wohnort an:");
                String stadt = scanner.nextLine();
                System.out.println("Bitte geben Sie Ihre Straße  an:");
                String straße = scanner.nextLine();
                System.out.println("Bitte geben Sie Ihre Postleitzahl  an:");
                String plz = scanner.nextLine();
                System.out.println("Zum Schluss benötigen wir noch Ihre Telefonnummer:");
                String tel = scanner.nextLine();


                String customerJson = customerController.createCustomer(stadt, straße, plz, tel);
                JSONObject json = new JSONObject(customerJson);
                customerWID = json.getJSONObject("customerDistrict").getInt("d_W_ID");


                System.out.println("Sie haben sich erfolgreich registriert!");
                customerAdresse = straße + " " + plz + " " + stadt;
                break;
            }
            case "2": {
                System.out.println("Bitte geben Sie Ihre KundenID ein");
                String id = scanner.nextLine();

                String customerJson = customerController.getCustomer(id);
                JSONObject json = new JSONObject(customerJson);
                customerWID = json.getJSONObject("customerDistrict").getInt("d_W_ID");
                String straße = json.getString("c_STRAßE");
                String stadt = json.getString("c_STADT");
                String plz = json.getString("c_PLZ");
                customerAdresse = straße + " " + plz + " " + stadt;
                System.out.println("Danke, Sie haben sich erfolgreich eingeloggt");
                break;
            }


        }
        System.out.println("Was möchten Sie als nächstes tun?");
        System.out.println("Bestellung aufgeben(1)");
        System.out.println("Bestellungen ansehen(2)");
        System.out.println("Adresse ändern(3)");
        System.out.println("Telefonnummer ändern(4)");
        System.out.println("Kundenkonto löschen(5)");
        System.out.println("Ausloggen(6)");
        String antwort2 = scanner.nextLine();
        switch (antwort2) {
            case "1": {
                List list = new ArrayList();

                System.out.println("Sie möchten also eine Bestellung aufgeben.");
                System.out.println("Nachfolgend finden Sie die Liste aller verfügbaren Items:");

                String json =  itemController.getAllItems();
                JSONArray jarr = new JSONArray(json);
                for (int j = 0; j < jarr.length(); j++) {
                    int id = jarr.getJSONObject(j).getInt("i_ID");
                    String name   = jarr.getJSONObject(j).getString("i_NAME");
                    Double preis   = jarr.getJSONObject(j).getDouble("i_PRICE");

                    System.out.print("Item ID: " +id + "   ");
                    System.out.print("Itemname: " +name + "   ");
                    System.out.print("Preis: "+preis + "Euro   ");
                    System.out.println("");

                }
                boolean nochmal = true;
                while (nochmal == true) {
                    System.out.println("Bitte geben Sie die ItemID ein, welche sie bestellen möchten!");
                    int itemid = scanner.nextInt();
                    System.out.println("Wieviele möchten Sie davon kaufen?.");
                    int anzahl = scanner.nextInt();
                    int bestand = Integer.parseInt(warehouseController.getItemStock(customerWID, itemid));
                    int array[] = new int[2];

                    if (bestand >= anzahl) {
                        System.out.println("Sie haben Glück!. Es sind genügend Items in ihrem Warehouse vorhanden. Der Versand kann sofort beginnen.");
                        warehouseController.reduceStock(customerWID, itemid, anzahl);
                        array[0] = itemid;
                        array[1] = anzahl;
                        orderItemList.add(array);
                    } else {
                        System.out.println("Es sind leider nicht genügendt Items in Bestand. Die Items müssen zuerst an ihr Warehouse geschickt werden. Anschließend wird Ihre Bestellung versandt");
                        warehouseController.addStock(customerWID, itemid, anzahl);
                        warehouseController.reduceStock(customerWID, itemid, anzahl);
                        array[0] = itemid;
                        array[1] = anzahl;
                        orderItemList.add(array);
                    }
                    System.out.println("Möchten Sie noch weitere Items zu Ihrer Bestellung hinzufügen? (Ja(1)/Nein(2)");
                    int ant = scanner.nextInt();
                    if (ant == 2){
                        nochmal = false;
                    }
                }

                System.out.println("--------------------------------------------------");
                System.out.println("Vielen Dank für folgende Bestellung!");
                for (int i = 0; i < orderItemList.size(); i++) {
                    int [] array = (int[]) orderItemList.get(i);
                    System.out.println("ItemID: " + array[0] + " Menge: " + array[1]);
                }
                System.out.println("Die Bestellung wird an folgende Adresse versendet: ");
                System.out.println(customerAdresse);
                break;
            }
            case "2": {
                System.out.println("Bitte geben Sie Ihre KundenID ein");
                String id = scanner.nextLine();
                customerController.getCustomer(id);
                System.out.println("Danke, Sie haben sich erfolgreich eingeloggt");
                break;
            }

            case "3": {

            }

            case "4": {

            }

            case "5": {

            }
            case "6": {

            }

        }
    }
}

