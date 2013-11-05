/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tpapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Miki
 */
public class FTPUploader {

    ArrayList<File> files = new ArrayList<>();
    FTPClient client = new FTPClient();
    FileInputStream fis = null;

    public void ftpconnect() throws FileNotFoundException, IOException {
        if (files.isEmpty()) {
            listFilesForFolder(new File("D:/gw2/static/"));
        }
        try {
            client.connect("ftp.atw.hu");
        } catch (SocketException ex) {
            Logger.getLogger(FTPUploader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FTPUploader.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            client.login("peterimiki", "910506");
            //System.out.println("Kapcsolódás");
        } catch (IOException ex) {
            Logger.getLogger(FTPUploader.class.getName()).log(Level.SEVERE, null, ex);
        }
        //File index = new File("index.html");
        //FileWriter fw = new FileWriter(index);
        //fw.write("<html><body  style=\"font-color:black;\">\n<p>");
        for (File f : files) {
            try {
                fis = new FileInputStream(f);
                //System.out.println(f.getParent());
                if (f.getParent().length() == 13) {
                    //fw.write("<a href=\"http://peterimiki.atw.hu/"
                    //        +f.getParent().substring(7, f.getParent().length()) +f.getName()+"\">"+f.getParent().substring(7, f.getParent().length()) +f.getName()+"</a><br>\n");
                    client.storeFile(f.getParent().substring(7, f.getParent().length()) + f.getName(), fis);
                } else if (f.getParent().length() > 13) {
                    //fw.write("<a href=\"http://peterimiki.atw.hu/"
                    //        +f.getParent().substring(22, f.getParent().length()) +f.getName()+"\">"+f.getParent().substring(22, f.getParent().length()) +f.getName()+"</a><br>\n");
                    client.storeFile(f.getParent().substring(22, f.getParent().length()) + f.getName(), fis);
                }
                //System.out.println("Feltöltés");
                //client.storeFile(f.getParent().substring(7, f.getParent().length()) +f.getName(), fis);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        //Date d = new Date();
        //d.getTime();
        //fw.write("</p><p>last updated: "+d.toString()+"</p>\n");
        //fw.write("</body></html>");
        //fw.close();
        //client.storeFile("index.html", new FileInputStream(index));
        client.logout();

    }

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                files.add(fileEntry);
            }
        }
    }
}
