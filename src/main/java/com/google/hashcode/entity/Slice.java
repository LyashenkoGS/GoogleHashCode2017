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

    public Slice(Cell... cell) {
        this.cells = new ArrayList<>(Arrays.asList(cell));
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
        return Collections.min(cells, Comparator.comparingInt(Cell::getY)).y;
    }

    public int maxX() {
        return Collections.max(cells, Comparator.comparingInt(Cell::getX)).x;
    }

    public int maxY() {
        return Collections.max(cells, Comparator.comparingInt(Cell::getY)).y;
    }


    /**
     * Coordinates are like in a 2D array
     *
     * @param y - row number, 0..max row number
     * @param x - column number,0..max column number
     * @return a pizza cell with specified coordinated
     */
    public Optional<Cell> getCell(int y, int x) {
        return cells.stream().filter(cell -> cell.x == x && cell.y == y).findFirst();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("slice : \n");
        if (maxX() + maxY() < 20) { //output coordinates
            int columnsCount = cells.stream().max(Comparator.comparingInt(Cell::getX)).get().getX();
            int rowsCount = cells.stream().max(Comparator.comparingInt(Cell::getY)).get().getY();
            //output columns coordinates
            stringBuilder.append(" ");
            for (int column = 0; column < columnsCount + 1; column++) {
                stringBuilder.append(" ").append(column);
            }
            stringBuilder.append("\n");
            for (int row = 0; row < rowsCount + 1; row++) {
                //output rows coordinates
                stringBuilder.append(row).append(" ");
                for (int column = 0; column < columnsCount + 1; column++) {
                    if (this.getCell(row, column).isPresent()) {
                        stringBuilder.append(this.getCell(row, column).get().toString()).append(" ");
                    } else {
                        stringBuilder.append(" ").append(" ");
                    }
                }
                stringBuilder.append("\n");
            }
        } else stringBuilder.append("\nsize: ").append(cells.size());
        return stringBuilder.toString().trim();
    }

    /**
     * check if slice valid for current pizza.
     *
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
        LOGGER.debug("\n" + pizza.getSliceInstruction() +
                "\nSlice :" + this +
                "\npassed validation: " + isPassedSliceInstructions);
        return isPassedSliceInstructions;
    }

    //region generate steps

    public Step generateStepAbove(Pizza pizza) {
        Slice delta = new Slice();
        for (int x = this.minX(); x <= this.maxX(); x++) {
            //try to get a cell
            Optional<Cell> cell = pizza.getCell(this.minY() - 1, x);
            if (cell.isPresent()) {
                delta.cells.add(cell.get());
            } else {
                LOGGER.debug("cant perform step left !");
                return null;
            }
        }
        LOGGER.debug("generateStepLeft"
                + "\nstep left delta: " + delta.toString());
        Step step = new Step(this, delta);
        if (step.isValid(pizza)) {
            return step;
        } else {
            LOGGER.debug("step is invalid !");
            return null;
        }
    }

    public Step generateStepBelow(Pizza pizza) {
        Slice delta = new Slice();
        for (int x = this.minX(); x <= this.maxX(); x++) {
            //try to get a cell
            Optional<Cell> cell = pizza.getCell(this.maxY() + 1, x);
            if (cell.isPresent()) {
                delta.cells.add(cell.get());
            } else {
                LOGGER.debug("cant perform step left !");
                return null;
            }
        }
        LOGGER.debug("generateStepLeft"
                + "\nstep left delta: " + delta.toString());
        Step step = new Step(this, delta);
        if (step.isValid(pizza)) {
            return step;
        } else {
            LOGGER.debug("step is invalid !");
            return null;
        }
    }

    public Step generateStepLeft(Pizza pizza) {
        Slice delta = new Slice();
        for (int y = this.minY(); y <= this.maxY(); y++) {
            //try to get a cell
            Optional<Cell> cell = pizza.getCell(y, minX() - 1);
            if (cell.isPresent()) {
                delta.cells.add(cell.get());
            } else {
                LOGGER.debug("cant perform step left !");
                return null;
            }
        }
        LOGGER.debug("generateStepLeft"
                + "\nstep left delta: " + delta.toString());
        Step step = new Step(this, delta);
        if (step.isValid(pizza)) {
            return step;
        } else {
            LOGGER.debug("step is invalid !");
            return null;
        }
    }

    public Step generateStepRight(Pizza pizza) {
        Slice delta = new Slice();
        for (int y = this.minY(); y <= this.maxY(); y++) {
            //try to get a cell
            Optional<Cell> cell = pizza.getCell(y, maxX() + 1);
            if (cell.isPresent()) {
                delta.cells.add(cell.get());
            } else {
                LOGGER.debug("cant perform step right !");
                return null;
            }
        }
        LOGGER.debug("generateStepLeft"
                + "\nstep left delta: " + delta.toString());
        Step step = new Step(this, delta);
        if (step.isValid(pizza)) {
            return step;
        } else {
            LOGGER.debug("step is invalid !");
            return null;
        }
    }
    //endregion

}

