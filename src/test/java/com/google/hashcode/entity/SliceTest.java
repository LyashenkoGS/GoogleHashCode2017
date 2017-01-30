package com.google.hashcode.entity;

import com.google.hashcode.utils.IoUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.google.hashcode.utils.FilesPaths.EXAMPLE_INPUT_FILE_PATH;
import static org.junit.Assert.*;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class SliceTest {
    private Pizza pizza;

    @Before
    public void setup() throws IOException {
        pizza = new Pizza(new File(EXAMPLE_INPUT_FILE_PATH), IoUtils.parsePizza(EXAMPLE_INPUT_FILE_PATH), IoUtils.parseSliceInstructions(EXAMPLE_INPUT_FILE_PATH));
    }

    @Test
    public void isValid() throws Exception {
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
        assertEquals(2, slice.generateStepBelow(pizza).delta.cells.size());
    }

    @Test
    public void centGenerateStepDeltaAbove() {
        Slice slice = new Slice(Arrays.asList(
                new Cell(0, 0, Ingredient.MUSHROOM),
                new Cell(0, 1, Ingredient.TOMATO)));
        assertEquals(null, slice.generateStepAbove(pizza));
    }

    @Test
    public void generateStepDeltaAbove() {
        Slice slice = new Slice(Arrays.asList(
                new Cell(1, 0, Ingredient.MUSHROOM),
                new Cell(1, 1, Ingredient.TOMATO)));
        assertEquals(2, slice.generateStepAbove(pizza).delta.cells.size());
    }

    @Test
    public void generateStepLeft() {
        Slice slice = new Slice(new ArrayList<>(Collections.singletonList(
                new Cell(1, 1, Ingredient.MUSHROOM))));
        assertEquals(2, slice.generateStepLeft(pizza).size());
    }

    @Test
    public void cantGenerateStepLeft() {
        Slice slice = new Slice(Arrays.asList(
                new Cell(0, 0, Ingredient.MUSHROOM),
                new Cell(0, 1, Ingredient.TOMATO)));
        assertEquals(null, slice.generateStepLeft(pizza));
    }

    @Test
    public void generateStepRight() {
        Slice slice = new Slice(Arrays.asList(
                new Cell(0, 0, Ingredient.MUSHROOM),
                new Cell(0, 1, Ingredient.TOMATO)));
        assertEquals(1, slice.generateStepRight(pizza).delta.cells.size());
        assertEquals(3, slice.generateStepRight(pizza).size());
    }

    @Test
    public void testToString() {
        Slice slice = new Slice(Arrays.asList(
                new Cell(0, 0, Ingredient.MUSHROOM)));
        assertEquals("slice : \n" +
                "  0\n" +
                "0 M", slice.toString());
        Slice slice1 = new Slice(Arrays.asList(
                new Cell(2, 3, Ingredient.TOMATO),
                new Cell(0, 1, Ingredient.MUSHROOM))
        );
        assertEquals("slice : \n" +
                "  0 1 2 3\n" +
                "0   M     \n" +
                "1         \n" +
                "2       T", slice1.toString());
    }
}