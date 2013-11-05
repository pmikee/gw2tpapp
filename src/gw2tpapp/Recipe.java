/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tpapp;

/**
 *
 * @author pemsaai
 */
public class Recipe {

    int data_id;
    String name;
    int result_count;
    int result_item_data_id;
    int discipline_id;
    int result_item_max_offer_unit_price;
    int result_item_min_sale_unit_price;
    int crafting_cost;
    int rating;

    public Recipe(int data_id, String name, int result_count, int result_item_data_id, int discipline_id, int result_item_max_offer_unit_price, int result_item_min_sale_unit_price, int crafting_cost, int rating) {
        this.data_id = data_id;
        this.name = name;
        this.result_count = result_count;
        this.result_item_data_id = result_item_data_id;
        this.discipline_id = discipline_id;
        this.result_item_max_offer_unit_price = result_item_max_offer_unit_price;
        this.result_item_min_sale_unit_price = result_item_min_sale_unit_price;
        this.crafting_cost = crafting_cost;
        this.rating = rating;
    }
    
    public Recipe(){
        super();
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

    public int getResult_count() {
        return result_count;
    }

    public void setResult_count(int result_count) {
        this.result_count = result_count;
    }

    public int getResult_item_data_id() {
        return result_item_data_id;
    }

    public void setResult_item_data_id(int result_item_data_id) {
        this.result_item_data_id = result_item_data_id;
    }

    public int getDiscipline_id() {
        return discipline_id;
    }

    public void setDiscipline_id(int discipline_id) {
        this.discipline_id = discipline_id;
    }

    public int getResult_item_max_offer_unit_price() {
        return result_item_max_offer_unit_price;
    }

    public void setResult_item_max_offer_unit_price(int result_item_max_offer_unit_price) {
        this.result_item_max_offer_unit_price = result_item_max_offer_unit_price;
    }

    public int getResult_item_min_sale_unit_price() {
        return result_item_min_sale_unit_price;
    }

    public void setResult_item_min_sale_unit_price(int result_item_min_sale_unit_price) {
        this.result_item_min_sale_unit_price = result_item_min_sale_unit_price;
    }

    public int getCrafting_cost() {
        return crafting_cost;
    }

    public void setCrafting_cost(int crafting_cost) {
        this.crafting_cost = crafting_cost;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Recipe{" + "data_id=" + data_id + ", name=" + name
                + ", result_count=" + result_count + ", result_item_data_id="
                + result_item_data_id + ", discipline_id=" + discipline_id
                + ", result_item_max_offer_unit_price=" + result_item_max_offer_unit_price
                + ", result_item_min_sale_unit_price=" + result_item_min_sale_unit_price
                + ", crafting_cost=" + crafting_cost + ", rating=" + rating + '}';
    }
}
