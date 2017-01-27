package com.google.hashcode.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A rectangle piece of a pizza
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Slice {

    public List<Cell> cells = new ArrayList<>();

    public Slice() {
    }

    public Slice(Cell cell) {
        this.cells = Collections.singletonList(cell);
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


   /* public Slice(Cell cell) {
        this.cells = Collections.singletonList(cell);
    }*/

    public int maxY() {
        return Collections.max(cells, Comparator.comparingInt(Cell::getX)).y;
    }

    @Override
    public String toString() {
        return cells.toString();
    }
}

