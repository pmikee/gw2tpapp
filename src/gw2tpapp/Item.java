/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tpapp;

import java.util.Date;

/**
 * @author pemsaai MUSTHAVE http://sourceforge.net/projects/json-lib/?source=dlp
 * http://blog.eviac.com/2011/07/parsing-json-comes-as-response-to-web.html
 * http://stackoverflow.com/questions/9553823/server-returned-http-response-code-403-for-urlhow-do-i-fix-this
 * http://stackoverflow.com/questions/6544389/parsing-json-with-java
 * http://stackoverflow.com/questions/4706594/reading-json-file-error
 * http://answers.oreilly.com/topic/257-how-to-parse-json-in-java/
 */
public class Item {

    int data_id;
    String name;
    int rarity;
    String rarity2;
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

    public Item() {
        super();
    }
    
   /* public Item(int data_id, String name,int level, String rarity2, String img, ){
        
    }*/

    public Item(int data_id, String name, int rarity, int restriction_level, String img, int type_id, 
            int sub_type_id, Date price_last_changed, int max_offer_unit_price, int min_sale_unit_price,
            int offer_availability, int sale_availability, int gw2db_external_id, int sale_price_change_last_hour, 
            int offer_price_change_last_hour) {
        this.data_id = data_id;
        this.name = name;
        this.rarity = rarity;
        this.restriction_level = restriction_level;
        this.img = img;
        this.type_id = type_id;
        this.sub_type_id = sub_type_id;
        this.price_last_changed = price_last_changed;
        this.max_offer_unit_price = max_offer_unit_price;
        this.min_sale_unit_price = min_sale_unit_price;
        this.offer_availability = offer_availability;
        this.sale_availability = sale_availability;
        this.gw2db_external_id = gw2db_external_id;
        this.sale_price_change_last_hour = sale_price_change_last_hour;
        this.offer_price_change_last_hour = offer_price_change_last_hour;
    }

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public int getRestriction_level() {
        return restriction_level;
    }

    public void setRestriction_level(int restriction_level) {
        this.restriction_level = restriction_level;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getSub_type_id() {
        return sub_type_id;
    }

    public void setSub_type_id(int sub_type_id) {
        this.sub_type_id = sub_type_id;
    }

    public Date getPrice_last_changed() {
        return price_last_changed;
    }

    public void setPrice_last_changed(Date price_last_changed) {
        this.price_last_changed = price_last_changed;
    }

    public int getMax_offer_unit_price() {
        return max_offer_unit_price;
    }

    public void setMax_offer_unit_price(int max_offer_unit_price) {
        this.max_offer_unit_price = max_offer_unit_price;
    }

    public int getMin_sale_unit_price() {
        return min_sale_unit_price;
    }

    public void setMin_sale_unit_price(int min_sale_unit_price) {
        this.min_sale_unit_price = min_sale_unit_price;
    }

    public int getOffer_availability() {
        return offer_availability;
    }

    public void setOffer_availability(int offer_availability) {
        this.offer_availability = offer_availability;
    }

    public int getSale_availability() {
        return sale_availability;
    }

    public void setSale_availability(int sale_availability) {
        this.sale_availability = sale_availability;
    }

    public int getGw2db_external_id() {
        return gw2db_external_id;
    }

    public void setGw2db_external_id(int gw2db_external_id) {
        this.gw2db_external_id = gw2db_external_id;
    }

    public int getSale_price_change_last_hour() {
        return sale_price_change_last_hour;
    }

    public void setSale_price_change_last_hour(int sale_price_change_last_hour) {
        this.sale_price_change_last_hour = sale_price_change_last_hour;
    }

    public int getOffer_price_change_last_hour() {
        return offer_price_change_last_hour;
    }

    public void setOffer_price_change_last_hour(int offer_price_change_last_hour) {
        this.offer_price_change_last_hour = offer_price_change_last_hour;
    }

    @Override
    public String toString() {
        return "Item{" + "data_id=" + data_id + ", name=" + name + ", rarity=" + rarity + ", restriction_level=" + restriction_level + ", type_id=" + type_id + ", sub_type_id=" + sub_type_id + ", max_offer_unit_price=" + max_offer_unit_price + ", min_sale_unit_price=" + min_sale_unit_price + ", offer_availability=" + offer_availability + ", sale_availability=" + sale_availability + '}';
    }
    
    
}
