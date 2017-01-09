package com.google.hashcode.utils;

import com.google.hashcode.entity.PizzaCell;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class IoUtilsTest {
    private final String examplePizzaFile = "inputDataSets/example.in";

    @Test
    public void parseExampleInput() throws IOException {
        PizzaCell[][] pizzaCells = IoUtils.parsePizza(examplePizzaFile);
        assertEquals("We expect" + examplePizzaFile + "contains 3 rows", 3, pizzaCells.length);
        assertEquals("We expect" + examplePizzaFile + "contains 5 columns", 5, pizzaCells[0].length);
        assertFalse("We expect no null value in pizzaCells", IoUtils.convertToHumanReadableTable(pizzaCells).contains("null"));
    }

    @Test
    public void parseExampleSliceInstructions() throws IOException {
        assertEquals("We expect min 1 ingredient per slice", 1,
                IoUtils.parseSliceInstructions(examplePizzaFile).getMinNumberOfIngredientPerSlice().intValue());
        assertEquals("We expect max 6 cells per slice", 6,
                IoUtils.parseSliceInstructions(examplePizzaFile).getMaxNumberOfCellsPerSlice().intValue());
    }
}