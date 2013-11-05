package gw2tpapp;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miki
 */
public class Gw2tpapp {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("SleepWhileInLoop")
    public static void main(String[] args) {
        try {
            Database db = new Database();
            RecipeList rl = new RecipeList(db);
            ItemList il = new ItemList(db);
            Generators g = new Generators(db);
            /*FTPUploader fup = new FTPUploader();
            ItemDataWrapper idw = new ItemDataWrapper();
             idw.getItemData(19721, "buy");
             guipanel a = new guipanel(db);
             a.getData();*/
            System.out.println("Start");
            il.getItemList(false);
            //rl.getRecipeList(false);
            System.out.println("End");
            //g.generateDyeList();
            while (true) {
                try {
                    db.connect();
                    Date d = new Date();
                    d.getTime();
                    System.out.println("Start: " + d.toString());
                    il.updateItemTable();
                    //rl.updateRecipeTable();
                    //g.generateFiles();
                    db.conn.close();
                    //fup.ftpconnect();
                    Date e = new Date();
                    e.getTime();
                    System.out.println("End: " + e.toString());
                    Thread.sleep(1800000);
                } catch (Exception e) {
                    Logger.getLogger(Gw2tpapp.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}