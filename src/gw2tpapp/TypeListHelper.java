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
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Miki
 */
public class TypeListHelper {

    Database db;
    private HttpURLConnection connection = null;

    public HttpURLConnection SetupClient(String requestUrl) throws MalformedURLException, IOException {
        //String requestUrl = "http://www.gw2spidy.com/api/v0.9/json/items/all/1";
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
        return connection;

    }

    public TypeListHelper(Database db) {
        this.db = db;
    }
    TypeList tl = new TypeList();
    Item item = new Item();
    Util util = new Util();
    ArrayList<Item> itemlist = new ArrayList<>();
    int col;
    ResultSetMetaData md;
    public ArrayList<TypeList> typelist = new ArrayList<>();

    public void getTypeList() throws SQLException, MalformedURLException, IOException, IOException {
        String jsonString = "";
        try {
            //SetupClient("http://www.gw2spidy.com/api/v0.9/json/items/all/" + page);
            String requestUrl = "http://www.gw2spidy.com/api/v0.9/json/types";
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
            System.out.println(outer.names());
            JSONArray types = outer.getJSONArray("results");
                System.out.println(types.get(0));
            
            JSONObject asd =  types.getJSONObject(1);
            //System.out.println(typesobj.names());
            
            //JSONArray subtypes = typesobj.getJSONArray("subtypes");
            //JSONArray st = subtypes.getJSONArray("subtypes");
            //System.out.println("Parsing list...");
            //System.out.println(outer.getInt("count"));
            
            for (int i = 0; i < types.size(); i++) {
                ArrayList<SubType> subtypeslist = new ArrayList<>();
                JSONObject json = (JSONObject) types.get(i);
                String typename = json.getString(json.names().getString(1));
                System.out.println(json.names());
                int typeid = json.getInt("id");
                for (int j = 0; j < asd.size(); i++) {
                    JSONObject jso = (JSONObject) types.get(j);
                    String name = jso.getString(jso.names().getString(0));
                    int subtype_id = jso.names().getInt(1);
                    subtypeslist.add(new SubType(name, subtype_id));
                    System.out.println(new SubType(name, subtype_id).toString());
                }
                typelist.add(new TypeList(typename, typeid, subtypeslist));
            }
            //System.out.println("Oldal:"+page);
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

