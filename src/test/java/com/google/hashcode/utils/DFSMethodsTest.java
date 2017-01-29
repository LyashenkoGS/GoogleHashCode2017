package com.google.hashcode.utils;

import com.google.hashcode.entity.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.hashcode.utils.InputFiles.EXAMPLE_INPUT_FILE_PATH;
import static org.junit.Assert.assertEquals;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class DFSMethodsTest {

    private Pizza pizza;

    @Before
    public void setup() throws IOException {
        pizza = new Pizza(new File(EXAMPLE_INPUT_FILE_PATH), IoUtils.parsePizza(EXAMPLE_INPUT_FILE_PATH), IoUtils.parseSliceInstructions(EXAMPLE_INPUT_FILE_PATH));
    }

    @Test
    public void getAvailableSteps() throws IOException {
        Map<Slice, List<Step>> actualMap = DFSMethods.getAvailableSteps(pizza, DFSMethods.cutAllStartPositions(pizza));
        assertEquals(3, actualMap.keySet().size());
        assertEquals(3, actualMap.get(new Slice(new Cell(1, 1, Ingredient.MUSHROOM))).size());
        assertEquals(2, actualMap.get(new Slice(new Cell(1, 2, Ingredient.MUSHROOM))).size());
        assertEquals(3, actualMap.get(new Slice(new Cell(1, 3, Ingredient.MUSHROOM))).size());
    }

    @Test
    public void cutAllStartPositions() throws IOException {
        List<Slice> expected = Arrays.asList(
                new Slice(new Cell(1, 1, Ingredient.MUSHROOM)),
                new Slice(new Cell(1, 2, Ingredient.MUSHROOM)),
                new Slice(new Cell(1, 3, Ingredient.MUSHROOM))
        );
        assertEquals(expected, DFSMethods.cutAllStartPositions(pizza));
        assertEquals("We expect pizza size reduced to 15-3=12", 12, pizza.getCells().size());
    }

    @Test
    public void performStep() {
        List<Slice> output = DFSMethods.cutAllStartPositions(pizza);
        Map<Slice, List<Step>> availableSteps = DFSMethods.getAvailableSteps(pizza, output);
        Slice slice = DFSMethods.performStep(pizza, DFSMethods.selectStep(availableSteps));
        assertEquals(new Slice(Arrays.asList(new Cell(0, 2, Ingredient.TOMATO), new Cell(1, 2, Ingredient.MUSHROOM))), slice);
        assertEquals(11, pizza.getCells().size());
    }
}