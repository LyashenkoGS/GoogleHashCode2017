package com.google.hashcode.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A rectangle piece of a pizza
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Slice {
    private static final Logger LOGGER = LoggerFactory.getLogger(Slice.class);

    public List<Cell> cells = new ArrayList<>();

    public Slice() {
    }

    public Slice(Cell cell) {
        this.cells = Collections.singletonList(cell);
    }

    public Slice(List<Cell> cells) {
        this.cells = cells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Slice)) return false;
        Slice slice = (Slice) o;
        return Objects.equals(cells, slice.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells);
    }

    public int minX() {
        return Collections.min(cells, Comparator.comparingInt(Cell::getX)).x;
    }

    public int minY() {
        return Collections.min(cells, Comparator.comparingInt(Cell::getX)).y;
    }

    public int maxX() {
        return Collections.max(cells, Comparator.comparingInt(Cell::getX)).x;
    }

    public int maxY() {
        return Collections.max(cells, Comparator.comparingInt(Cell::getX)).y;
    }

    @Override
    public String toString() {
        return cells.toString();
    }

    public boolean isValid(Pizza pizza) {
        //TODO check rectangularity
        int mushroomsNumber = this.cells.stream()
                .filter(cell -> cell.ingredient.equals(Ingredient.MUSHROOM))
                .collect(Collectors.toList())
                .size();
        int tomatoesNumber = this.cells.stream()
                .filter(cell -> cell.ingredient.equals(Ingredient.TOMATO))
                .collect(Collectors.toList())
                .size();
        boolean isPassedSliceInstructions = this.cells.size() <= pizza.getSliceInstruction().getMaxNumberOfCellsPerSlice()
                && tomatoesNumber >= pizza.getSliceInstruction().getMinNumberOfIngredientPerSlice()
                && mushroomsNumber >= pizza.getSliceInstruction().getMinNumberOfIngredientPerSlice();
        LOGGER.info("\n" + pizza.getSliceInstruction() +
                "\nSlice :" + this +
                "\npassed validation: " + isPassedSliceInstructions);
        return isPassedSliceInstructions;
    }

}

