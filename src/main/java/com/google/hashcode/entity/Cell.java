package com.google.hashcode.entity;

import java.util.Objects;

/**
 * Represents a pizza cell with it coordinates. There is no getters/setters for simplicity
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Cell {
    public int y;
    public int x;
    public Ingredient ingredient;

    public Cell(int y, int x, Ingredient ingredient) {
        this.y = y;
        this.x = x;
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return ingredient.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return this.x == cell.x &&
                this.y == cell.y &&
                this.ingredient == cell.ingredient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, ingredient);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
