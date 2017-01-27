package com.google.hashcode.entity;

/**
 * Represents a pizza cell with it coordinates. There is no getters/setters for simplicity
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Cell {
    public int x;
    public int y;
    public Ingredient ingredient;
    /**
     * indicates if given cell has been sliced
     */
    public boolean sliced = false;

    public Cell(int x, int y, Ingredient ingredient) {
        this.x = x;
        this.y = y;
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return ingredient.toString();
    }

    public static Cell prototype(int x, int y) {
        return new Cell(x, y, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;

        Cell cell = (Cell) o;

        if (x != cell.x) return false;
        if (y != cell.y) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public int getX() {
        return x;
    }
}
