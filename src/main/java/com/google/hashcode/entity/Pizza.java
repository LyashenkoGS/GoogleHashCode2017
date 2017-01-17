package com.google.hashcode.entity;

import com.google.hashcode.utils.IoUtils;

import java.io.File;

/**
 * Represents an immutable pizza
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Pizza {

    private final File input;
    private final Cell[][] cells;
    private final SliceInstruction sliceInstruction;

    public Pizza(File input, Cell[][] cells, SliceInstruction sliceInstruction) {
        this.input = input;
        this.cells = cells;
        this.sliceInstruction = sliceInstruction;
    }

    public File getInput() {
        return input;
    }

    public Cell[][] getCells() {
        return cells;
    }


    @Override
    public String toString() {
        return input.toString() +
                "\n" + IoUtils.convertToHumanReadableTable(cells) +
                "\n" + sliceInstruction.toString();
    }


    public SliceInstruction getSliceInstruction() {
        return sliceInstruction;
    }
}
