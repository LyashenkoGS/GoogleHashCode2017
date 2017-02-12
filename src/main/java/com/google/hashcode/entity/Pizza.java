package com.google.hashcode.entity;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Represents an immutable pizza
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Pizza {

    private final File input;
    private final SliceInstruction sliceInstruction;
    private List<Cell> cells;

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

    public void setCells(List<Cell> cells) {
        this.cells = cells;
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

    public SliceInstruction getSliceInstruction() {
        return sliceInstruction;
    }

    @Override
    public String toString() {
        return input.toString()
                + ("\n" + sliceInstruction.toString()
                + "\n" + outputCellsArray()).trim();
    }

    /**
     * Indicates does this pizza contains each slice's cell
     *
     * @param slice given slice
     * @return true if the pizza contains the slice
     */
    public boolean containsCells(Slice slice) {
        return slice.cells.stream().allMatch(this.cells::contains);
    }

    private String outputCellsArray() {
        if (!cells.isEmpty() && cells.size() < 100) {
            StringBuilder stringBuilder = new StringBuilder();
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
            return stringBuilder.toString();
        } else {
            return "pizza size is:" + cells.size();
        }

    }


}
