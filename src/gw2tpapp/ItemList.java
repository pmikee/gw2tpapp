/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tpapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Miki
 */
public class ItemList {

    private Database db;
    private HttpURLConnection connection = null;
    private static final String DBNAME = "item3";
    private Util util = new Util();
    private int col;
    private ResultSetMetaData md;

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

    public ItemList(Database db) {
        this.db = db;
    }
   

    public void getItemList(Boolean isUpdate) throws SQLException, MalformedURLException, IOException, IOException, Exception {
        if (isUpdate) {
            getItemColumns();
        }

        int page = 1;
        int maxpage = getPageNumber();
        while (page < 210) {
            String jsonString = "";
            try {
                String requestUrl = "http://www.gw2spidy.com/api/v0.9/json/items/all/" + page;
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
                    //System.out.println(jsonString);
                }
                JSONObject outer = (JSONObject) JSONSerializer.toJSON(jsonString);
                JSONArray items = outer.getJSONArray("results");
                for (int i = 0; i < items.size(); i++) {
                    JSONObject json = (JSONObject) items.get(i);
                    if (isUpdate) {
                        updateItemPrices(util.generateItem(json));
                    } else {
                        db.itemToDataBase(util.generateItem(json));
                    }
                }
                page++;
            } catch (NullPointerException | IOException e) {
                System.out.println(e.getCause());
            } catch (ParseException pe) {
            }
        }
    }

    public int getPageNumber() throws MalformedURLException, IOException {
        String jsonString = "";
        SetupClient("http://www.gw2spidy.com/api/v0.9/json/items/all/1");
        InputStream is = connection.getInputStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                jsonString = inputLine;
            }
        }
        JSONObject outer = (JSONObject) JSONSerializer.toJSON(jsonString);

        return outer.getInt("last_page");
    }

    public void updateItemTable() throws SQLException, MalformedURLException, IOException, Exception {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        try {
            Statement st = db.conn.createStatement();
            st.executeUpdate("ALTER TABLE " + DBNAME + " ADD COLUMN " + "eladodbszam" + reportDate + " integer");
            st.executeUpdate("ALTER TABLE " + DBNAME + " ADD COLUMN " + "mennyitvesznek" + reportDate + " integer");
            st.executeUpdate("ALTER TABLE " + DBNAME + " ADD COLUMN " + "highestbuy" + reportDate + " integer");
            st.executeUpdate("ALTER TABLE " + DBNAME + " ADD COLUMN " + "lowestsell" + reportDate + " integer");
            getItemList(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getItemColumns() throws SQLException {
        try {
            Statement st = db.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + DBNAME + " limit 1");
            md = rs.getMetaData();
            col = md.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateItemPrices(Item item) throws SQLException {
        try {
            Statement st = db.conn.createStatement();
            st.executeUpdate("UPDATE " + DBNAME + " SET " + md.getColumnName(col - 1) + " = " + item.getMax_offer_unit_price() + ", "
                    + md.getColumnName(col) + " = " + item.getMin_sale_unit_price() + ","
                    + md.getColumnName(col - 2) + " = " + item.getOffer_availability() + ", "
                    + md.getColumnName(col - 3) + " = " + item.getSale_availability()
                    + " where data_id=" + item.getData_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
