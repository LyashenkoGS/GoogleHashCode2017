package com.google.hashcode.service;

import com.google.hashcode.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.valueOf;

/**
 * Slice a pizza according to a slice instructions
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Slicer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Slicer.class);

    private Slicer() {
    }

    public static List<Slice> slicePizza(Pizza pizza) {
        Cell[][] pizzaCells = pizza.getCells();
        List<Slice> pizzaSlices = new LinkedList<>();
        while (canBeSliced(pizza)) {
            Slice slice = step(pizzaCells);
            if (validateSlice(slice, pizza.getSliceInstruction())) {
                pizzaSlices.add(slice);
            }
        }
        LOGGER.info("slices:" + pizzaSlices);
        LOGGER.info("pizza slicing finished" +
                "\ntotal cells:" + pizza.cellsNumber() +
                "\nsliced cells :" + pizzaSlices.stream()
                .map(Slice::cellsNumber)
                .reduce(Integer::sum)
                .get() +
                "\nunsliced cells :" + pizza.unslicedCellsNumber());
        return pizzaSlices;
    }


    private static Slice step(Cell[][] pizzaCells) {
        Slice slice = new Slice();
        //find the top-left unused cell
        Cell startPosition = findStartPosition(pizzaCells);
        LOGGER.info("\nstart position: "
                + "\ny: " + startPosition.y
                + "\nx: " + startPosition.x);
        slice.add(pizzaCells[startPosition.y][startPosition.x]);
        slice.add(pizzaCells[startPosition.y][valueOf(startPosition.x) + 1]);
        slice.add(pizzaCells[valueOf(startPosition.y) + 1][startPosition.x]);
        slice.add(pizzaCells[valueOf(startPosition.y) + 1][valueOf(startPosition.x) + 1]);
        return slice;
    }

    private static Cell findStartPosition(Cell[][] pizzaCells) {
        for (int row = 0; row < pizzaCells.length; row++) {
            Cell[] pizzaRow = pizzaCells[row];
            for (int column = 0; column < pizzaRow.length; column++) {
                Cell cell = pizzaRow[column];
                if (!cell.sliced) {
                    //get it as a start
                    return cell;
                }
            }
        }
        //default return
        return pizzaCells[0][0];
    }

    static boolean validateSlice(Slice slice, SliceInstruction sliceInstruction) {
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
                "\npass validation: " + passSliceInstructions);
        return passSliceInstructions;
    }

    static boolean canBeSliced(Pizza pizza) {
        final List<Cell> unusedCells = Arrays.stream(pizza.getCells())
                .flatMap(Arrays::stream)
                .filter(cell -> !cell.sliced)
                .collect(Collectors.toList());
        int mushroomsNumber = unusedCells.stream()
                .filter(cell -> cell.ingredient.equals(Ingredient.MUSHROOM))
                .collect(Collectors.toList())
                .size();
        int tomatoesNumber = unusedCells.stream()
                .filter(cell -> cell.ingredient.equals(Ingredient.TOMATO))
                .collect(Collectors.toList())
                .size();
        SliceInstruction sliceInstruction = pizza.getSliceInstruction();
        boolean canBeSliced = unusedCells.size() > sliceInstruction.getMinNumberOfIngredientPerSlice() * 2
                && tomatoesNumber > sliceInstruction.getMinNumberOfIngredientPerSlice()
                && mushroomsNumber > sliceInstruction.getMinNumberOfIngredientPerSlice();
        LOGGER.info(
                "\ncells suitable for slicing :" + unusedCells.size() +
                        "\n :" + unusedCells +
                        "\ncan be sliced: " + canBeSliced);
        return canBeSliced;
    }
}
