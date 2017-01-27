package com.google.hashcode.entity;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Represents an immutable pizza
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Pizza {

    private final File input;
    private final List<Cell> cells;
    private final SliceInstruction sliceInstruction;

    public Pizza(File input, List<Cell> cells, SliceInstruction sliceInstruction) {
        this.input = input;
        this.cells = cells;
        this.sliceInstruction = sliceInstruction;
    }

    public File getInput() {
        return input;
    }

    public List<Cell> getCells() {
        return cells;
    }

    /**
     * Coordinates are like in a 2D array
     *
     * @param y - row number, 0..max row number
     * @param x - column number,0..max column number
     * @return a pizza cell with specified coordinated
     */
    public Cell getCell(int y, int x) {
        final Optional<Cell> cellByCoordinates = cells.stream().filter(cell -> cell.x == x && cell.y == y).findFirst();
        if (cellByCoordinates.isPresent()) {
            return cellByCoordinates.get();
        } else throw new IllegalArgumentException("No cell with "
                + "\n y: " + y
                + "\nx: " + x);
    }

    public SliceInstruction getSliceInstruction() {
        return sliceInstruction;
    }

    @Override
    public String toString() {
        return input.toString() +
                //TODO fix human readable output   "\n" + IoUtils.convertToHumanReadableTable(cells) +
                "\n" + sliceInstruction.toString();
    }

}
