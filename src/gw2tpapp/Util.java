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
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author pemsaai
 */
public class Util {

    //Database db = new Database();
    int data_id;
    String name;
    int rarity;
    int restriction_level;
    String img;
    int type_id;
    int sub_type_id;
    Date price_last_changed;    //"YYYY-MM-DD HH:II:SS UTC",
    int max_offer_unit_price;
    int min_sale_unit_price;
    int offer_availability;
    int sale_availability;
    int gw2db_external_id;             //# the ID they use in their URLs (for tooltips etc)
    int sale_price_change_last_hour;   // # this is the percentage the item price changed since the last hour
    int offer_price_change_last_hour;
    JSONObject result;
    private Item gotit;
    private Recipe anyád;
    private Result r = new Result();

    public Util() {
    }

    public Item getItemByID(int id) throws ParseException {
        String jsonString = "";
        String requestUrl = "http://www.gw2spidy.com/api/v0.9/json/item/" + id;
        HttpURLConnection connection = null;
        try {

            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                jsonString = inputLine;
            }
            br.close();
            JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonString);
            result = json.getJSONObject("result");
            gotit = generateItem(result);
        } catch (FileNotFoundException fnf) {
            System.out.println("Az adott item nem található!");
        } catch (IOException e) {
            System.out.println("A szerver visszautasította a lekérést");
            e.printStackTrace();
        }
        return gotit;
    }

    private Date convertToDate(String s) throws ParseException {
       Timestamp sqlDate = new Timestamp(Integer.getInteger(s));
       return sqlDate;
    }

    public Item generateItem(JSONObject json) throws ParseException {
        data_id = json.getInt(json.names().getString(0));
        name = json.getString(json.names().getString(1));
        rarity = json.getInt(json.names().getString(2));
        restriction_level = json.getInt(json.names().getString(3));
        img = json.getString(json.names().getString(4));
        type_id = json.getInt(json.names().getString(5));
        sub_type_id = json.getInt(json.names().getString(6));
        //price_last_changed = convertToDate(json.names().getString(7));   //"YYYY-MM-DD HH:II:SS UTC",
        max_offer_unit_price = json.getInt(json.names().getString(8));
        min_sale_unit_price = json.getInt(json.names().getString(9));
        offer_availability = json.getInt(json.names().getString(10));
        sale_availability = json.getInt(json.names().getString(11));
        gw2db_external_id = json.getInt(json.names().getString(12));             //# the ID they use in their URLs (for tooltips etc)
        sale_price_change_last_hour = json.getInt(json.names().getString(13));   // # this is the percentage the item price changed since the last hour
        offer_price_change_last_hour = json.getInt(json.names().getString(14));
        return new Item(data_id, name, rarity, restriction_level, img, type_id, sub_type_id, price_last_changed, max_offer_unit_price, min_sale_unit_price, offer_availability, sale_availability, gw2db_external_id, sale_price_change_last_hour, offer_price_change_last_hour);
    }

    public void getRecipeByID(int id) throws ParseException {
        String jsonString = "";
        String requestUrl = "http://www.gw2spidy.com/api/v0.9/json/recipe/" + id;
        HttpURLConnection connection = null;
        try {

            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                jsonString = inputLine;
            }
            br.close();
            JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonString);
            result = json.getJSONObject("result");
            anyád = generateRecipe(result);
        } catch (FileNotFoundException fnf) {
            System.out.println("Az adott recept nem található!");
        } catch (IOException e) {
            System.out.println("A szerver visszautasította a lekérést");
            e.printStackTrace();
        }
    }

    public Recipe generateRecipe(JSONObject json) {

        int rdata_id = 0;
        String rname = null;
        int result_count = 0;
        int result_item_data_id = 0;
        int discipline_id = 0;
        int result_item_max_offer_unit_price = 0;
        int result_item_min_sale_unit_price = 0;
        int crafting_cost = 0;
        int rating = 0;

        try {
            rdata_id = json.getInt(json.names().getString(0));
            rname = json.getString(json.names().getString(1));
            result_count = json.getInt(json.names().getString(2));
            result_item_data_id = json.getInt(json.names().getString(3));
            discipline_id = json.getInt(json.names().getString(4));
            result_item_max_offer_unit_price = json.getInt(json.names().getString(5));
            result_item_min_sale_unit_price = json.getInt(json.names().getString(6));
            crafting_cost = json.getInt(json.names().getString(7));   //"YYYY-MM-DD HH:II:SS UTC",
            rating = json.getInt(json.names().getString(8));
        } catch (Exception e) {
            System.out.println("hibás recept");
            //e.printStackTrace();
            //System.out.println(new Recipe(rdata_id, rname, result_count, result_item_data_id, discipline_id, result_item_max_offer_unit_price, result_item_min_sale_unit_price, crafting_cost, rating).toString());
        }
        return new Recipe(rdata_id, rname, result_count, result_item_data_id, discipline_id, result_item_max_offer_unit_price, result_item_min_sale_unit_price, crafting_cost, rating);
    }

    void getItemFromDB(int id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    
}
