package com.google.hashcode.entity;

/**
 * Represents a pizza cell with it coordinates. There is no getters/setters for simplicity
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Cell {
    final int x;
    final int y;
    final Ingredient ingredient;

    public Cell(int x, int y, Ingredient ingredient) {
        this.x = x;
        this.y = y;
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return ingredient.toString();
    }
}
