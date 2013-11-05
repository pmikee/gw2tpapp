/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tpapp;

import com.mysql.jdbc.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Miki
 */
public class Database {

    public Connection conn;
    public static final String DBNAME = "item3";
    
    public Connection connect() {
        //String url = "jdbc:mysql://192.168.1.39:3306/gw2items";
        String url = "jdbc:mysql://localhost:3306/gw2items";
        String driver = "com.mysql.jdbc.Driver";
        //System.out.println("kapcsolódás...");
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "root", "root");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("kapcsolódás...hiba");
        }
        return conn;
    }

    public void itemToDataBase(Item item) {
        try {
            PreparedStatement pst;
            pst = conn.prepareStatement("INSERT INTO "+DBNAME+"(data_id, name,rarity,restriction_level,img,type_id,sub_type_id,"
                    + "max_offer_unit_price,min_sale_unit_price,offer_availability,sale_availability,"
                    + "gw2db_external_id,sale_price_change_last_hour,offer_price_change_last_hour) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setInt(1, item.getData_id());
            pst.setString(2, item.getName());
            pst.setInt(3, item.getRarity());
            pst.setInt(4, item.getRestriction_level());
            pst.setString(5, item.getImg());
            pst.setInt(6, item.getType_id());
            pst.setInt(7, item.getSub_type_id());
            pst.setInt(8, item.getMax_offer_unit_price());
            pst.setInt(9, item.getMin_sale_unit_price());
            pst.setInt(10, item.getOffer_availability());
            pst.setInt(11, item.getSale_availability());
            pst.setInt(12, item.getGw2db_external_id());
            pst.setInt(13, item.getSale_price_change_last_hour());
            pst.setInt(14, item.getOffer_price_change_last_hour());
            pst.executeUpdate();
            System.out.println(item.toString());
        } catch (SQLException s) {
            //System.out.println("SQL statement is not executed!");
        }

    }

    public void recipeToDataBase(Recipe recipe) {
        try {
            PreparedStatement pst;
            pst = conn.prepareStatement("INSERT INTO recipe(data_id,name,result_count,"
                    + "result_item_data_id,discipline_id,result_item_max_offer_unit_price,"
                    + "result_item_min_sale_unit_offer,crafting_cost,rating) "
                    + "VALUES(?,?,?,?,?,?,?,?,?)");
            pst.setInt(1, recipe.getData_id());
            pst.setString(2, recipe.getName());
            pst.setInt(3, recipe.getResult_count());
            pst.setInt(4, recipe.getResult_item_data_id());
            pst.setInt(5, recipe.getDiscipline_id());
            pst.setInt(6, recipe.getResult_item_max_offer_unit_price());
            pst.setInt(7, recipe.getResult_item_min_sale_unit_price());
            pst.setInt(8, recipe.getCrafting_cost());
            pst.setInt(9, recipe.getRating());
            pst.executeUpdate();
        } catch (SQLException s) {
        }

    }

    public void close() throws SQLException {
        conn.close();
    }

    public Database(){
      conn = connect();
    }

    public Item getItemFromDB(int id) throws SQLException {
        Statement stmt;
        ResultSet rs = null;
        int numColumns = 0;
        try {
            stmt = conn.createStatement();
            String query = "select * from "+DBNAME+" where data_id=" + id + ";";
            rs = (ResultSet) stmt.executeQuery(query);
            try {
                rs.next();
                numColumns = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= numColumns; i++) {
                }

            } catch (Exception e) {
            }

        } catch (Exception e) {
        }
        return (new Item(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7),
                new java.util.Date(0), rs.getInt(9), rs.getInt(numColumns - 1), rs.getInt(numColumns), rs.getInt(10),
                rs.getInt(11), rs.getInt(12), rs.getInt(13)));
    }
}
