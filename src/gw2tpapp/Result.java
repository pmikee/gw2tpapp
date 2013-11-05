/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tpapp;

/**
 *
 * @author Miki
 */
public class Result {
    String res;
    Recipe recipe;

    public Result(String res, Recipe recipe) {
        this.res = res;
        this.recipe = recipe;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "Result{" + "res=" + res + ", recipe=" + recipe + '}';
    }
    
    public Result(){
        
    }
}
