/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tpapp;

import java.util.ArrayList;

/**
 *
 * @author Miki
 */
public class TypeList {

    String name;
    int type;
    ArrayList<SubType> subtype = new ArrayList<>();

    public TypeList(String name, int type, ArrayList<SubType> subtype) {
        this.name = name;
        this.type = type;
        this.subtype = subtype;
    }

    public TypeList() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<SubType> getSubtype() {
        return subtype;
    }

    public void setSubtype(ArrayList<SubType> subtype) {
        this.subtype = subtype;
    }

    @Override
    public String toString() {
        return "TypeList{" + "name=" + name + ", type=" + type + ", subtype=" + subtype.toString() + '}';
    }
}
