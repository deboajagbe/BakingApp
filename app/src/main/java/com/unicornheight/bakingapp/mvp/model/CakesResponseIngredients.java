package com.unicornheight.bakingapp.mvp.model;

public class CakesResponseIngredients implements java.io.Serializable {
    private static final long serialVersionUID = -8227914481685787127L;
    private double quantity;
    private String measure;
    private String ingredient;

    public double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return this.measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
