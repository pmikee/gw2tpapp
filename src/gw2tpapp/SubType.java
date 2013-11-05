/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tpapp;

/**
 *
 * @author Miki
 */
public class SubType {
    
    String name;
    int subtype_id;

    public SubType(String name, int subtype_id) {
        this.name = name;
        this.subtype_id = subtype_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubtype_id() {
        return subtype_id;
    }

    public void setSubtype_id(int subtype_id) {
        this.subtype_id = subtype_id;
    }

    @Override
    public String toString() {
        return "SubType{" + "name=" + name + ", subtype_id=" + subtype_id + '}';
    }
    
    
}
