package com.google.hashcode.service;

import com.google.hashcode.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Slice a pizza according to a slice instructions
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Slicer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Slicer.class);

    public static List<Slice> slicePizza(Pizza pizza) {
        LOGGER.info("Pizza to slice :" + pizza.toString());
        //iterate from the top-left cell
        Cell[][] pizzaCells = pizza.getCells();
        SliceInstruction sliceInstruction = pizza.getSliceInstruction();
        //get smallest possible slice
        Slice slice = new Slice();
        slice.cells.add(pizzaCells[0][0]);
        slice.cells.add(pizzaCells[0][1]);
        slice.cells.add(pizzaCells[1][0]);
        slice.cells.add(pizzaCells[1][1]);
        //check instruction
        checkSliceInstructions(slice, sliceInstruction);
        //if pass add slice to results
        List<Slice> pizzaSlices = new LinkedList<>();
        pizzaSlices.add(slice);
        LOGGER.info("slices:" + pizzaSlices);
        return pizzaSlices;
    }

    static boolean checkSliceInstructions(Slice slice, SliceInstruction sliceInstruction) {
        int mushroomsNumber = slice.cells.stream()
                .filter(cell -> cell.ingredient.equals(Ingredient.MUSHROOM))
                .collect(Collectors.toList())
                .size();
        int tomatoesNumber = slice.cells.stream()
                .filter(cell -> cell.ingredient.equals(Ingredient.TOMATO))
                .collect(Collectors.toList())
                .size();
        boolean passSliceInstructions = slice.cells.size() <= sliceInstruction.getMaxNumberOfCellsPerSlice()
                && tomatoesNumber >= sliceInstruction.getMinNumberOfIngredientPerSlice()
                && mushroomsNumber >= sliceInstruction.getMinNumberOfIngredientPerSlice();
        LOGGER.info("\n" + sliceInstruction +
                "\nSlice :" + slice +
                "\nresult: " + passSliceInstructions);
        return passSliceInstructions;
    }
}
