package com.google.hashcode.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A rectangle piece of a pizza
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Slice {

    public List<Cell> cells = new ArrayList<>();

    public void add(Cell cell) {
        cells.add(cell);
        cell.sliced = true;
    }

    public int cellsNumber() {
        return cells.size();
    }

    @Override
    public String toString() {
        return cells.toString();
    }
}

