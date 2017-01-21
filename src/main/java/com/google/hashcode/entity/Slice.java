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

    @Override
    public String toString() {
        return cells.toString();
    }
}

