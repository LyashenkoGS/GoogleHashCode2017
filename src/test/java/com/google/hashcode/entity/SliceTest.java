package com.google.hashcode.entity;

import com.google.hashcode.utils.IoUtils;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static com.google.hashcode.utils.InputFiles.EXAMPLE_INPUT_FILE_PATH;
import static org.junit.Assert.*;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class SliceTest {

    @Test
    public void isValid() throws Exception {
        Pizza pizza = new Pizza(new File(EXAMPLE_INPUT_FILE_PATH), IoUtils.parsePizza(EXAMPLE_INPUT_FILE_PATH), IoUtils.parseSliceInstructions(EXAMPLE_INPUT_FILE_PATH));
        Slice invalidSlice = new Slice(pizza.getCells());
        assertFalse(invalidSlice.isValid(pizza));
        //create a valid slice
        Slice validSlice = new Slice(Arrays.asList(
                new Cell(0, 0, Ingredient.TOMATO),
                new Cell(0, 1, Ingredient.MUSHROOM)));
        assertTrue(validSlice.isValid(pizza));
    }

    @Test
    public void generateStepDeltaBelow() {
        Slice slice = new Slice(Arrays.asList(
                new Cell(0, 0, Ingredient.MUSHROOM),
                new Cell(0, 1, Ingredient.TOMATO)));
        assertEquals(2, slice.generateStepDeltaBelow().cells.size());
    }

    @Test
    public void generateStepDeltaAbove() {
        Slice slice = new Slice(Arrays.asList(
                new Cell(0, 0, Ingredient.MUSHROOM),
                new Cell(0, 1, Ingredient.TOMATO)));
        assertEquals(2, slice.generateStepDeltaAbove().cells.size());
    }

    @Test
    public void generateStepDeltaLeft() {
        Slice slice = new Slice(Arrays.asList(
                new Cell(0, 0, Ingredient.MUSHROOM),
                new Cell(0, 1, Ingredient.TOMATO)));
        assertEquals(1, slice.generateStepDeltaLeft().cells.size());
    }

    @Test
    public void generateStepRight() {
        Slice slice = new Slice(Arrays.asList(
                new Cell(0, 0, Ingredient.MUSHROOM),
                new Cell(0, 1, Ingredient.TOMATO)));
        assertEquals(1, slice.generateStepDeltaRight().cells.size());
    }
}