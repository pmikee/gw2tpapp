/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tpapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Miki
 */
public class Generators {
    
    Database db = new Database();
    private static final String DBNAME = "item3";
    int col;
    ResultSetMetaData md;
    SigilFlipper sf = new SigilFlipper(db);
    
    public Generators(Database db) {
        this.db = db;
    }
    
    private void getItemColumns() throws SQLException {
        try {
            Statement st = db.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + DBNAME + " limit 1");
            md = rs.getMetaData();
            col = md.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void getFlippables() throws SQLException {
        try {
            Statement st = db.conn.createStatement();
            String lowestsell = md.getColumnName(md.getColumnCount());
            String highestbuy = md.getColumnName(md.getColumnCount() - 1);
            
            ResultSet rs = st.executeQuery("select name,( " + lowestsell + " * 0.85 - " + highestbuy + " ), "
                    + lowestsell + ", " + highestbuy
                    + " from " + DBNAME + " where ( " + lowestsell + " * 0.85 - " + highestbuy
                    + " ) > 1000 and rarity between 2 and 6 "
                    + " and " + lowestsell + "<200000 and name not like '%Recipe%' and name not like '%Satchel%' "
                    + " and name not like '%Pot%' and name not like '%Tray%' and name not like '%Box%' and "
                    + lowestsell + ">0 and " + highestbuy + ">0 "
                    + " order by ( " + lowestsell + " * 0.85 -" + highestbuy + " ) desc;");
            ResultSetMetaData metad = rs.getMetaData();
            FileWriter fstream = new FileWriter("D:/gw2/static/flip.html");
            try (PrintWriter out = new PrintWriter(fstream)) {
                out.println("<!DOCTYPE HTML>\n<html lang = \"en\">\n<head>\n<title> flip </title>\n"
                        + "<meta charset = \"UTF-8\" />\n"
                        + "<style type = \"text/css\">"
                        + "table\n{\nborder-collapse:collapse;\n}"
                        + "\ntd, th {\nborder: 1px solid green;\npadding:5px;}"
                        + "\n</style>"
                        + "</head>\n<body>\n<table id = flip>\n<tr>\n<th>lowest sell</th>\n<th>highest buy</th>"
                        + "\n<th>profit</th>\n<th>name</th></tr>\n");
                while (rs.next()) {
                    out.println("<tr>\n<td>" + rs.getString(metad.getColumnName(3)) + "</td>"
                            + "\n<td>" + rs.getString(metad.getColumnName(4)) + "</td>"
                            + "\n<td>" + rs.getString(metad.getColumnName(2)) + "</td>"
                            + "\n<td>" + rs.getString(metad.getColumnName(1)) + "</td></tr>");
                }
                Date e = new Date();
                e.getTime();
                out.println("</table>\n");
                out.println("<h5><p>Last updated: " + e.toString() + "</p></h5>\n</body>\n</html>");;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    private void getWeaponPricesList() throws SQLException {
        List<Integer> subtypes = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 8, 10, 11, 12, 16);
        List<String> names = Arrays.asList("Sword", "Hammer", "Longbow", "Shortbow", "Axe", "Dagger", "Greatsword",
                "Pistol", "Rifle", "Scepter", "Staff", "Shield");
        List<String> szoveg = new ArrayList<>();
        try {
            Statement st = db.conn.createStatement();
            String lowestsell = md.getColumnName(md.getColumnCount());
            String highestbuy = md.getColumnName(md.getColumnCount() - 1);
            int index = 0;
            for (Integer i : subtypes) {
                for (int j = 1; j < 8; j++) {
                    if (j != 6) {
                        ResultSet rs = st.executeQuery("select name, "
                                + lowestsell + ", " + highestbuy + " , ( " + lowestsell + " * 0.85 - " + highestbuy + " ), "
                                + "restriction_level from " + DBNAME + " where rarity= " + j
                                + " and type_id=18 and sub_type_id= " + i
                                + " and " + highestbuy + " > 1 and " + lowestsell + ">0 "
                                + " and restriction_level > 70 "
                                + " order by ( " + lowestsell + " * 0.85 - " + highestbuy + " ) desc; ");
                        ResultSetMetaData metad = rs.getMetaData();
                        if (!new File("D:/gw2/static/weapons/rarity" + j).exists()) {
                            new File("D:/gw2/static/weapons/rarity" + j).mkdirs();
                            System.out.println("nem létezett, létrehoztam");
                        }
                        FileWriter fstream = new FileWriter("D:/gw2/static/weapons/rarity" + j + "/"
                                + names.get(index) + ".html");
                        try (PrintWriter out = new PrintWriter(fstream)) {
                            out.println(generateHeader(j, names.get(index)));
                            while (rs.next()) {
                                out.println("<tr>\n<td>" + rs.getString(metad.getColumnName(2)) + "</td>"
                                        + "\n<td>" + rs.getString(metad.getColumnName(3)) + "</td>"
                                        + "\n<td>" + rs.getString(metad.getColumnName(4)) + "</td>"
                                        + "\n<td>" + rs.getString(metad.getColumnName(1)) + "</td>"
                                        + "\n<td>" + rs.getString(metad.getColumnName(5)) + "</td></tr>");
                            }
                            Date e = new Date();
                            e.getTime();
                            out.println("</table>\n");
                            out.println("<h5><p>Last updated: " + e.toString() + "</p></h5>\n</body>\n</html>");
                        }
                    }
                }
                index++;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public String generateHeader(int rarity, String type) {
        return "<!DOCTYPE HTML>\n<html lang = \"en\">\n<head>\n<title>" + rarity + "-" + type + "</title>\n"
                + "<meta charset = \"UTF-8\" />\n"
                + "<style type = \"text/css\">"
                + "table\n{\nborder-collapse:collapse;\n}"
                + "\ntd, th {\nborder: 1px solid green;\npadding:5px;}"
                + "\n</style>"
                + "</head>\n<body>\n<table id = " + rarity + "-" + type + ">\n<tr>\n<th>lowest sell</th>\n<th>highest buy</th>"
                + "\n<th>profit</th>\n<th>name</th>\n<th>level</th></tr>\n";
    }
    
    private void generateProfit() throws SQLException {
        try {
            Statement st = db.conn.createStatement();
            String lowestsell = md.getColumnName(md.getColumnCount());
            String highestbuy = md.getColumnName(md.getColumnCount() - 1);
            
            ResultSet rs = st.executeQuery("select name,( " + lowestsell + " * 0.85 - " + highestbuy + " ) , "
                    + md.getColumnName(md.getColumnCount() - 2) + ", " + md.getColumnName(md.getColumnCount() - 3)
                    + " from " + DBNAME + " where ( " + lowestsell + " *0.85 - " + highestbuy + " ) > 100 "
                    + " and rarity between 2 and 6 and " + lowestsell + ">0  and " + highestbuy + ">0 and "
                    + "( " + lowestsell + " > " + md.getColumnName(md.getColumnCount() - 4) + " and "
                    + md.getColumnName(md.getColumnCount() - 1) + " < " + md.getColumnName(md.getColumnCount() - 5) + " ) "
                    + " and " + lowestsell + "<200000 and name not like '%Recipe%' and name not like '%Satchel%' "
                    + " and name not like '%Pot%' and name not like '%Tray%' "
                    + " order by ( " + lowestsell + " * 0.85 -" + highestbuy + " )  desc;");
            ResultSetMetaData metad = rs.getMetaData();
            FileWriter fstream = new FileWriter("D:/gw2/static/valtozoflip.txt");
            try (PrintWriter out = new PrintWriter(fstream)) {
                while (rs.next()) {
                    out.println("price: " + rs.getString(metad.getColumnName(2))
                            + "  \teladodbszam: " + rs.getString(metad.getColumnName(3))
                            + "  \tmennyivesznek: " + rs.getString(metad.getColumnName(4))
                            + "   \tname: " + rs.getString(metad.getColumnName(1)));
                }
                Date e = new Date();
                e.getTime();
                out.println("Last updated: " + e.toString());
            }
            
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public void generateDyeList() throws SQLException {
        getItemColumns();
        try {
            Statement st = db.conn.createStatement();
            String lowestsell = md.getColumnName(md.getColumnCount());
            String highestbuy = md.getColumnName(md.getColumnCount() - 1);
            ResultSet rs = st.executeQuery("select name, "
                    + lowestsell + ", " + highestbuy
                    + " from " + DBNAME + " where name like '%Dye%' and name not like '%Unidentified%' "
                    + " order by name asc;");
            ResultSetMetaData metad = rs.getMetaData();
            new File("D:/gw2/dyes/").mkdirs();
            FileWriter fstream = new FileWriter("D:/gw2/dyes/dyelist.txt");
            try (PrintWriter out = new PrintWriter(fstream)) {
                while (rs.next()) {
                    out.println("lowestsell: " + rs.getString(metad.getColumnName(2))
                            + " \thighestbuy: " + rs.getString(metad.getColumnName(3))
                            + " \tname: " + rs.getString(metad.getColumnName(1)));
                }
                Date e = new Date();
                e.getTime();
                out.println("Last updated: " + e.toString());
            }
            
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    private void generateSigilList() throws SQLException {
        try {
            Statement st = db.conn.createStatement();
            String lowestsell = md.getColumnName(md.getColumnCount());
            String highestbuy = md.getColumnName(md.getColumnCount() - 1);
            ResultSet rs = st.executeQuery("select name, "
                    + lowestsell + ", " + highestbuy
                    + ", rarity from " + DBNAME + " where name like '%Sigil%' and rarity = 4 "
                    + " and name not like '%Recipe%' "
                    + " order by " + highestbuy + " asc;");
            ResultSetMetaData metad = rs.getMetaData();
            FileWriter fstream = new FileWriter("D:/gw2/static/sigils.txt");
            try (PrintWriter out = new PrintWriter(fstream)) {
                while (rs.next()) {
                    out.println("lowestsell: " + rs.getString(metad.getColumnName(2))
                            + " \thighestbuy: " + rs.getString(metad.getColumnName(3))
                            + "  \trarity: " + rs.getString(metad.getColumnName(4))
                            + " \tname: " + rs.getString(metad.getColumnName(1)));
                }
            }
            
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    private void generateExoSigilList() throws SQLException {
        try {
            Statement st = db.conn.createStatement();
            String lowestsell = md.getColumnName(md.getColumnCount());
            String highestbuy = md.getColumnName(md.getColumnCount() - 1);
            ResultSet rs = st.executeQuery("select name, "
                    + lowestsell + ", " + highestbuy
                    + ", rarity from " + DBNAME + " where name like '%Sigil%' and rarity = 5 "
                    + " and name not like '%Recipe%' "
                    + " order by " + lowestsell + " desc;");
            ResultSetMetaData metad = rs.getMetaData();
            FileWriter fstream = new FileWriter("D:/gw2/static/exosigils.txt");
            try (PrintWriter out = new PrintWriter(fstream)) {
                while (rs.next()) {
                    out.println("lowestsell: " + rs.getString(metad.getColumnName(2))
                            + " \thighestbuy: " + rs.getString(metad.getColumnName(3))
                            + "  \trarity: " + rs.getString(metad.getColumnName(4))
                            + " \tname: " + rs.getString(metad.getColumnName(1)));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    private void generateExoRuneList() throws SQLException {
        try {
            Statement st = db.conn.createStatement();
            String lowestsell = md.getColumnName(md.getColumnCount());
            String highestbuy = md.getColumnName(md.getColumnCount() - 1);
            ResultSet rs = st.executeQuery("select name, "
                    + lowestsell + ", " + highestbuy
                    + ", rarity from " + DBNAME + " where name like '%Rune of%' and rarity = 5 "
                    + " and name not like '%Recipe%' "
                    + " order by " + lowestsell + " desc;");
            ResultSetMetaData metad = rs.getMetaData();
            FileWriter fstream = new FileWriter("D:/gw2/static/exorunes.txt");
            try (PrintWriter out = new PrintWriter(fstream)) {
                while (rs.next()) {
                    out.println("lowestsell: " + rs.getString(metad.getColumnName(2))
                            + " \thighestbuy: " + rs.getString(metad.getColumnName(3))
                            + "  \trarity: " + rs.getString(metad.getColumnName(4))
                            + " \tname: " + rs.getString(metad.getColumnName(1)));
                }
            }
            
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    private void generateRuneList() throws SQLException {
        try {
            Statement st = db.conn.createStatement();
            String lowestsell = md.getColumnName(md.getColumnCount());
            String highestbuy = md.getColumnName(md.getColumnCount() - 1);
            ResultSet rs = st.executeQuery("select name, "
                    + lowestsell + ", " + highestbuy
                    + ", rarity from " + DBNAME + " where name like '%Rune%' and rarity = 4 "
                    + " and name not like '%Recipe%' "
                    + " order by " + highestbuy + " asc;");
            ResultSetMetaData metad = rs.getMetaData();
            FileWriter fstream = new FileWriter("D:/gw2/static/runes.txt");
            try (PrintWriter out = new PrintWriter(fstream)) {
                while (rs.next()) {
                    out.println("lowestsell: " + rs.getString(metad.getColumnName(2))
                            + " \thighestbuy: " + rs.getString(metad.getColumnName(3))
                            + "  \trarity: " + rs.getString(metad.getColumnName(4))
                            + " \tname: " + rs.getString(metad.getColumnName(1)));
                }
            }
            
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public void generateFiles() throws SQLException, FileNotFoundException, IOException {
        getItemColumns();
        generateExoRuneList();
        generateExoSigilList();
        generateProfit();
        generateRuneList();
        generateSigilList();
        getFlippables();
        getWeaponPricesList();
        sf.getNames();
        sf.getSigilNames();
    }
}
