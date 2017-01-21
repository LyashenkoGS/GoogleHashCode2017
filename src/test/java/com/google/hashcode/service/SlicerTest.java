package com.google.hashcode.service;

import com.google.hashcode.entity.*;
import com.google.hashcode.utils.IoUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class SlicerTest {
    private static final String EXAMPLE_PIZZA_FILE = "inputDataSets/example.in";
    Cell[][] cells = IoUtils.parsePizza(EXAMPLE_PIZZA_FILE);
    SliceInstruction sliceInstruction = IoUtils.parseSliceInstructions(EXAMPLE_PIZZA_FILE);
    Pizza pizza = new Pizza(new File(EXAMPLE_PIZZA_FILE), cells, sliceInstruction);


    public SlicerTest() throws IOException {
    }

    @Test
    public void slicePizza() throws IOException {
        Slicer.slicePizza(pizza);
    }

    @Test
    public void checkSliceInstructions() {
        Slice slice = new Slice();
        slice.cells.add(new Cell(0, 0, Ingredient.MUSHROOM));
        slice.cells.add(new Cell(0, 0, Ingredient.TOMATO));
        assertTrue(Slicer.checkSliceInstructions(slice, new SliceInstruction(1, 2)));
    }

    @Test
    public void checkSliceInstructionsDontPass() {
        Slice slice = new Slice();
        slice.cells.add(new Cell(0, 0, Ingredient.MUSHROOM));
        slice.cells.add(new Cell(0, 0, Ingredient.MUSHROOM));
        assertFalse(Slicer.checkSliceInstructions(slice, new SliceInstruction(1, 2)));
    }
}
