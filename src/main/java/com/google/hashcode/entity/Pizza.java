package com.google.hashcode.entity;

import com.google.hashcode.utils.IoUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an immutable pizza
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Pizza {

    private final File input;
    private final List<Cell> cells;

    public File getInput() {
        return input;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public SliceInstruction getSliceInstruction() {
        return sliceInstruction;
    }

    private final SliceInstruction sliceInstruction;

    public Pizza(File input, List<Cell> cells, SliceInstruction sliceInstruction) {
        this.input = input;
        this.cells = cells;
        this.sliceInstruction = sliceInstruction;
    }

    @Override
    public String toString() {
        return input.toString() +
             //TODO fix human readable output   "\n" + IoUtils.convertToHumanReadableTable(cells) +
                "\n" + sliceInstruction.toString();
    }


}
