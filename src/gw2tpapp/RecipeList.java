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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Miki
 */
public class RecipeList {

    private Util util = new Util();
    private Database db;
    private ResultSetMetaData md;
    private int col;

    public RecipeList(Database db) {
        this.db=db;
    }

    public void getRecipeList(Boolean isUpdate) throws SQLException, Exception {
        if (isUpdate) {
            getRecipeColumns();
        }
        int page = 1;
        while (page < 72) {
            String jsonString = "";
            String requestUrl = "http://www.gw2spidy.com/api/v0.9/json/recipes/all/" + page;
            HttpURLConnection connection;
            try {

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
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println("Getting the item list...");
                JSONObject outer = (JSONObject) JSONSerializer.toJSON(jsonString);
                JSONArray items = outer.getJSONArray("results");
                //System.out.println("Parsing list...");
                //System.out.println(outer.getInt("count"));
                for (int i = 0; i < items.size(); i++) {
                    JSONObject json = (JSONObject) items.get(i);
                    if (isUpdate) {
                        updateRecipePrices(util.generateRecipe(json));
                    } else {
                        db.recipeToDataBase(util.generateRecipe(json));
                    }
                }
                page++;
                //System.out.println("Completed!");
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (FileNotFoundException fnf) {
                fnf.printStackTrace();
                System.out.println("Az adott item nem található!");
            } catch (IOException e) {
                e.printStackTrace();
                //System.out.println("A szerver visszautasította a lekérést");
            }
        }
    }

    public void updateRecipeTable() throws Exception {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        //System.out.println("Report Date: " + reportDate);
        try {
            Statement st = db.conn.createStatement();
            st.executeUpdate("ALTER TABLE recipe ADD COLUMN " + "sell" + reportDate + " integer");
            st.executeUpdate("ALTER TABLE recipe ADD COLUMN " + "buy" + reportDate + " integer");
            //System.out.println("Updated");
            getRecipeList(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void getRecipeColumns() {
        try {
            Statement st = db.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM recipe limit 1");
            md = rs.getMetaData();
            col = md.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //System.out.println("Number of recipe Column : " + col);
    }

    public void updateRecipePrices(Recipe recipe) {

        try {
            Statement st = db.conn.createStatement();
            st.executeUpdate("UPDATE recipe SET " + md.getColumnName(col - 1) + " = " + recipe.getResult_item_max_offer_unit_price() + ", "
                    + md.getColumnName(col) + " = "
                    + recipe.getResult_item_min_sale_unit_price() + " where data_id=" + recipe.getData_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println("1 row updated");
    }
}
