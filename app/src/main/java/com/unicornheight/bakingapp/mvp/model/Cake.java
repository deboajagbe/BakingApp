
package com.unicornheight.bakingapp.mvp.model;

import java.io.Serializable;
import java.util.List;


public class Cake implements Serializable {

    private int id;
    private String image;
    private int servings;
    private String name;
    private List<CakesResponseSteps> steps;
    private List<CakesResponseIngredients> ingredients;


    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getServings() {
        return this.servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CakesResponseIngredients> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<CakesResponseIngredients> ingredients) {
        this.ingredients = ingredients;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CakesResponseSteps> getSteps() {
        return this.steps;
    }

    public void setSteps(List<CakesResponseSteps> steps) {
        this.steps = steps;
    }

}
