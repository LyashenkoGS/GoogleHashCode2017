package com.google.hashcode.entity;

import com.google.hashcode.utils.IoUtils;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

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

    public int cellsNumber() {
        return Arrays.stream(this.getCells())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList()).size();
    }

    public int unslicedCellsNumber() {
        return Arrays.stream(this.getCells())
                .flatMap(Arrays::stream)
                .filter(cell -> !cell.sliced)
                .collect(Collectors.toList()).size();
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
