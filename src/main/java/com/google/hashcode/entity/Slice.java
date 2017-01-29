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


    /**
     * check if slice valid for current pizza.
     * @param pizza
     * @return
     */
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

    //region generate steps


    public Slice generateStepDeltaAbove() {
        List<Cell> delta = new ArrayList<>();
        for (int x = this.minX(); x <= this.maxX(); x++) {
            Cell cell = new Cell(this.minY() - 1, x, Ingredient.TOMATO);
            delta.add(cell);
        }
        LOGGER.info("generateStepDeltaAbove"
                + "\nslice :" + this.toString()
                + "\nstep above delta: " + delta.toString());
        return new Slice(delta);
    }

    public Slice generateStepDeltaBelow() {
        List<Cell> delta = new ArrayList<>();
        for (int x = this.minX(); x <= this.maxX(); x++) {
            Cell cell = new Cell(this.maxY() + 1, x, Ingredient.TOMATO);
            delta.add(cell);
        }
        LOGGER.info("generateStepDeltaBelow"
                + "\nslice :" + this.toString()
                + "\nstep below delta: " + delta.toString());
        return new Slice(delta);
    }

    public Slice generateStepDeltaLeft() {
        List<Cell> delta = new ArrayList<>();
        for (int y = this.minY(); y <= this.maxY(); y++) {
            Cell cell = new Cell(y, minX() -1 , Ingredient.TOMATO);
            delta.add(cell);
        }
        LOGGER.info("generateStepDeltaLeft"
                + "\nslice :" + this.toString()
                + "\nstep left delta: " + delta.toString());
        return new Slice(delta);
    }

    public Slice generateStepDeltaRight() {
        List<Cell> delta = new ArrayList<>();
        for (int y = this.minY(); y <= this.maxY(); y++) {
            Cell cell = new Cell(y, maxX() + 1, Ingredient.TOMATO);
            delta.add(cell);
        }
        LOGGER.info("generateStepDeltaRight"
                + "\nslice :" + this.toString()
                + "\nstep right delta: " + delta.toString());
        return new Slice(delta);
    }
    //endregion

}

