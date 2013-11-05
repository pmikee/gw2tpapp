/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tpapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Miki
 */
public class ItemDataWrapper {

    HttpURLConnection connection;

    public ItemDataWrapper() {
    }
    public ArrayList<Date> listing_date = new ArrayList<>();
    public List<Integer> unit_price = new ArrayList<>();
    public ArrayList<Integer> quantity = new ArrayList<>();
    public ArrayList<Integer> listings = new ArrayList<>();

    public void getItemData(int id, String sellorbuy) {
        int page = 1;
        //int maxpage = getPageNumber();
        while (page < 5) {
            String jsonString = "";
            try {
                //SetupClient("http://www.gw2spidy.com/api/v0.9/json/items/all/" + page);
                String requestUrl = "http://www.gw2spidy.com/api/v0.9/json/listings/" + id + "/" + sellorbuy + "/" + page;
                URL url = new URL(requestUrl);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Language", "en-US");
                connection.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
                //System.out.println("Connecting to gw2spidy.com");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                InputStream is = connection.getInputStream();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        jsonString = inputLine;
                    }
                }
                //System.out.println("Getting the item list...");
                JSONObject outer = (JSONObject) JSONSerializer.toJSON(jsonString);
                JSONArray items = outer.getJSONArray("results");
                //System.out.println("Parsing list...");
                //System.out.println(outer.getInt("count"));
                for (int i = 0; i < items.size(); i++) {
                    JSONObject json = (JSONObject) items.get(i);
                    System.out.println(json.getInt("unit_price"));
                    unit_price.add(json.getInt("unit_price"));
                    quantity.add(json.getInt("quantity"));
                    listings.add(json.getInt("listings"));
                    DateFormat formatter;
                    Date date = null;
                    formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    try {
                        date = (Date) formatter.parse(json.getString("listing_datetime"));
                    } catch (ParseException ex) {
                        Logger.getLogger(ItemDataWrapper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.print(date + " ");
                    listing_date.add(date);

                }
                System.out.println("Max:" + Collections.max(unit_price));
                page++;
                System.out.println("Oldal:" + page);
            } catch (NullPointerException e) {
                System.out.println(e.getCause());
            } catch (FileNotFoundException fnf) {
                System.out.println(fnf.getCause());
                System.out.println("Az adott item nem található!");
            } catch (IOException e) {
                e.printStackTrace();
                //System.out.println("A szerver visszautasította a lekérést");
                System.out.println(e.getCause());

            }
        }
    }
}
