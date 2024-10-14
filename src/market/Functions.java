package market;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import market.objects.Item;
import market.objects.ItemPrice;
import market.objects.NameData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

public class Functions {
    public static String getAPIData(String input){
        try{
            String readLine = null;
            URI apiUri = new URI(input);
            HttpURLConnection connection = (HttpURLConnection) apiUri.toURL().openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            while ((readLine = in.readLine()) != null){
                response.append(readLine);
            }

            in.close();
            return response.toString();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String postAPIData(String input, String messageContent){
        try{
            String readLine = null;

            URI apiUri = new URI(input);
            HttpURLConnection connection = (HttpURLConnection) apiUri.toURL().openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            OutputStream oS = connection.getOutputStream();
            oS.write(messageContent.getBytes());
            oS.flush();
            oS.close();

            BufferedReader bR = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            while ((readLine = bR.readLine()) != null){
                response.append(readLine);
            }

            bR.close();
            return response.toString();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Item[] getPrices(){
        String priceData = getAPIData("https://esi.evetech.net/latest/markets/prices/?datasource=tranquility");
        Gson gson = new GsonBuilder().create();
        ItemPrice[] itemPrices = gson.fromJson(priceData, ItemPrice[].class);
        NameData[] nameData = new NameData[itemPrices.length];

        for (int i = 0; i < itemPrices.length; ){
            String[] tempItemPriceArray = new String[Math.min(1000, itemPrices.length - i)];
            for (int j = 0; j < 1000 && j < itemPrices.length && i < itemPrices.length; j++, i++){
                tempItemPriceArray[j] = String.valueOf(itemPrices[i].getId());
            }

            NameData[] tempNameDataArray = gson.fromJson(postAPIData("https://esi.evetech.net/latest/universe/names/?datasource=tranquility", Arrays.toString(tempItemPriceArray)), NameData[].class);

            for (int k = 0; k < tempNameDataArray.length; k++){
                System.arraycopy(tempNameDataArray, 0, nameData, i - tempNameDataArray.length, tempNameDataArray.length);
            }
        }

        Dictionary<String, NameData> nameDataDictionary = new Hashtable<>();

        for (NameData nD:nameData){
            nameDataDictionary.put(String.valueOf(nD.getID()), nD);
        }

        Item[] items = new Item[nameData.length];

        try{
            for (int i = 0; i < itemPrices.length; i++){
                items[i] = new Item(itemPrices[i], nameDataDictionary.get(String.valueOf(itemPrices[i].getId())));
            }

            return items;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new Item[0];
        }
    }
}
