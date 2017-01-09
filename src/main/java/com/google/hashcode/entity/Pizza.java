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
    private final PizzaCell[][] pizzaCells;
    private final SliceInstruction sliceInstruction;

    public Pizza(File input, PizzaCell[][] pizzaCells, SliceInstruction sliceInstruction) {
        this.input = input;
        this.pizzaCells = pizzaCells;
        this.sliceInstruction = sliceInstruction;
    }

    public File getInput() {
        return input;
    }

    public PizzaCell[][] getPizzaCells() {
        return pizzaCells;
    }


    @Override
    public String toString() {
        return input.toString() +
                "\n" + IoUtils.convertToHumanReadableTable(pizzaCells) +
                "\n" + sliceInstruction.toString();
    }


    public SliceInstruction getSliceInstruction() {
        return sliceInstruction;
    }
}
