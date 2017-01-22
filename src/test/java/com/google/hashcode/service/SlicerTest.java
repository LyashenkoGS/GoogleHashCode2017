package com.google.hashcode.service;

import com.google.hashcode.entity.*;
import com.google.hashcode.utils.IoUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class SlicerTest {
    private static final String EXAMPLE_PIZZA_FILE = "inputDataSets/example.in";
    private Cell[][] cells = IoUtils.parsePizza(EXAMPLE_PIZZA_FILE);
    private SliceInstruction sliceInstruction = IoUtils.parseSliceInstructions(EXAMPLE_PIZZA_FILE);
    private Pizza pizza = new Pizza(new File(EXAMPLE_PIZZA_FILE), cells, sliceInstruction);

    public SlicerTest() throws IOException {
    }

    @Test
    public void slicePizza() throws IOException {
        List<Slice> slices = Slicer.slicePizza(pizza);
    }

    @Test
    public void checkSliceInstructions() {
        Slice slice = new Slice();
        slice.cells.add(new Cell(0, 0, Ingredient.MUSHROOM));
        slice.cells.add(new Cell(0, 0, Ingredient.TOMATO));
        assertTrue(Slicer.validateSlice(slice, new SliceInstruction(1, 2)));
    }

    @Test
    public void checkSliceInstructionsDontPass() {
        Slice slice = new Slice();
        slice.cells.add(new Cell(0, 0, Ingredient.MUSHROOM));
        slice.cells.add(new Cell(0, 0, Ingredient.MUSHROOM));
        assertFalse(Slicer.validateSlice(slice, new SliceInstruction(1, 2)));
    }

    @Test
    public void canBeSliced() {
        new Slice().add(pizza.getCells()[0][0]);
        assertTrue(Slicer.canBeSliced(pizza));
    }
}
