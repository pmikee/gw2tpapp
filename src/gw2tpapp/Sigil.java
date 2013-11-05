/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tpapp;

/**
 *
 * @author Miki
 */
class Sigil {

    int highestbuy;
    int lowestsell;
    String name;

    public Sigil(int highestbuy, int lowestsell, String name) {
        this.highestbuy = highestbuy;
        this.lowestsell = lowestsell;
        this.name = name;
    }

    public int getHighestbuy() {
        return highestbuy;
    }

    public void setHighestbuy(int highestbuy) {
        this.highestbuy = highestbuy;
    }

    public int getLowestsell() {
        return lowestsell;
    }

    public void setLowestsell(int lowestsell) {
        this.lowestsell = lowestsell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sigil{" + "highestbuy=" + highestbuy + ", lowestsell=" + lowestsell + ", name=" + name + '}';
    }
    
    
    
}
