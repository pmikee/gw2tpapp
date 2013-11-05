/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tpapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import sun.font.EAttribute;

/**
 *
 * @author Miki
 */
public class SigilFlipper {

    Database db = new Database();
    ArrayList<Sigil> sigillist = new ArrayList<>();
    ArrayList<Sigil> runelist = new ArrayList<>();
    private static String DBNAME = "item3";
    int col;
    ResultSetMetaData md;

    public SigilFlipper(Database db) {
        this.db = db;
    }

    public void getNames() throws FileNotFoundException, IOException, SQLException {
        if (runelist.isEmpty()) {
            File f = new File("D:/gw2/static/ExoRunes.txt");
            FileReader fis = new FileReader(f);
            BufferedReader br = new BufferedReader(fis);
            String line;
            int sellprice;
            int buyprice;
            int lineno = 1;
            String name = "";
            while ((line = br.readLine()) != null) {
                String[] columns = line.split("\t");
                String[] sigilname = columns[3].split(" ");
                String[] sellpr = columns[1].split(" ");
                String[] buypr = columns[0].split(" ");
                sellprice = Integer.parseInt(sellpr[1]);
                buyprice = Integer.parseInt(buypr[1]);
                if (sigilname[4].equals("the")) {
                    name = sigilname[5];
                } else {
                    name = sigilname[4];
                }
                runelist.add(new Sigil(sellprice, buyprice, name));
            }
        }
        getProfitRunes();
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

    private void getProfitRunes() throws SQLException, IOException {
        ArrayList<String> szoveg = new ArrayList<>();
        getItemColumns();
        try {
            for (Sigil r : runelist) {
                Statement st = db.conn.createStatement();
                String lowestsell = md.getColumnName(md.getColumnCount());
                String highestbuy = md.getColumnName(md.getColumnCount() - 1);
                ResultSet rs = st.executeQuery("select name," + lowestsell + ", " + highestbuy + ", "
                        + r.getLowestsell() + "*0.85 -" + highestbuy
                        + " from " + DBNAME + " where name like '%of%" + r.getName() + "%' and name not like '%Rune%'"
                        + " and rarity=5 and restriction_level>67"
                        + " and type_id = 0 and name not like '%King%s%'"
                        + "order by "
                        + "( " + r.getLowestsell() + "*0.85 -" + highestbuy + " ) desc;");
                ResultSetMetaData metad = rs.getMetaData();
                if (rs.first()) {
                    szoveg.add(r.toString() + "\n");
                }
                try {
                    while (rs.next()) {
                        szoveg.add("flip profit: " + rs.getString(metad.getColumnName(4))
                                + " \thighestbuy: " + rs.getString(metad.getColumnName(3))
                                + " \tlowestsell: " + rs.getString(metad.getColumnName(2))
                                + " \tname: " + rs.getString(metad.getColumnName(1)) + "\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileWriter fstream = new FileWriter("D:/gw2/static/runeproba.txt");
        try (PrintWriter out = new PrintWriter(fstream)) {
            for (String s : szoveg) {
                out.print(s);
            }
            Date e = new Date();
            e.getTime();
            out.println("Last updated: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fstream.close();
        }

        //out.write(sb.toString());
    }

    public void getSigilNames() throws FileNotFoundException, IOException, SQLException {
        if (sigillist.isEmpty()) {
            File f = new File("D:/gw2/static/ExoSigils.txt");
            FileReader fis = new FileReader(f);
            BufferedReader br = new BufferedReader(fis);
            String line;
            int sellprice;
            int buyprice;
            String name = "";
            while ((line = br.readLine()) != null) {
                String[] columns = line.split("\t");
                String[] sigilname = columns[3].split(" ");
                String[] sellpr = columns[1].split(" ");
                String[] buypr = columns[0].split(" ");
                sellprice = Integer.parseInt(sellpr[1]);
                buyprice = Integer.parseInt(buypr[1]);
                if (sigilname[4].equals("the")) {
                    name = sigilname[5];
                } else {
                    name = sigilname[4];
                }
                sigillist.add(new Sigil(sellprice, buyprice, name));
                //System.out.println(new Sigil(sellprice, buyprice, name));
            }
        }
        getProfitSigils();
    }

    private void getProfitSigils() throws SQLException, IOException {
        ArrayList<String> szoveg = new ArrayList<>();
        szoveg.clear();
        getItemColumns();
        try {
            for (Sigil s : sigillist) {
                Statement st = db.conn.createStatement();
                String lowestsell = md.getColumnName(md.getColumnCount());
                String highestbuy = md.getColumnName(md.getColumnCount() - 1);
                ResultSet rs = st.executeQuery("select name," + lowestsell + ", " + highestbuy + ", "
                        + s.getLowestsell() + "*0.85 -" + highestbuy
                        + " from " + DBNAME + " where name like '%of%" + s.getName() + "%' and name not like '%Sigil%'"
                        + " and rarity=5 and restriction_level>67 and " + lowestsell + ">0 "
                        + " and type_id = 18 order by "
                        + "( " + s.getLowestsell() + "*0.85 -" + highestbuy + " ) desc;");
                ResultSetMetaData metad = rs.getMetaData();
                if (rs.first()) {
                    szoveg.add(s.toString() + "\n");
                }
                try {
                    while (rs.next()) {
                        szoveg.add("flip profit: " + rs.getString(metad.getColumnName(4))
                                + " \thighestbuy: " + rs.getString(metad.getColumnName(3))
                                + " \tlowestsell: " + rs.getString(metad.getColumnName(2))
                                + " \tname: " + rs.getString(metad.getColumnName(1)) + "\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileWriter fstream = new FileWriter("D:/gw2/static/sigilproba.txt");
        try (PrintWriter out = new PrintWriter(fstream)) {
            for (String s : szoveg) {
                out.print(s);
            }
            Date e = new Date();
            e.getTime();
            out.println("Last updated: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fstream.close();
        }

        //out.write(sb.toString());
    }
}
