package com.google.hashcode.utils;

import com.google.hashcode.entity.Cell;
import com.google.hashcode.entity.Ingredient;
import com.google.hashcode.entity.Slice;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class IoUtilsTest {
    private static final String TEST_OUTPUT_FILE = "testOutput.txt";
    private static final String PARAGON_OUTPUT_EXAMPLE_FILE = "src/test/resources/paragonOutputExample.txt";
    private static final String EXAMPLE_PIZZA_FILE = "inputDataSets/example.in";

    private static List<Slice> createSlicesForParagonOutputExample() {
        Slice slice0 = new Slice();
        Slice slice1 = new Slice();
        Slice slice2 = new Slice();

        slice0.cells.add(new Cell(0, 0, Ingredient.TOMATO));
        slice0.cells.add(new Cell(0, 1, Ingredient.TOMATO));
        slice0.cells.add(new Cell(0, 2, Ingredient.TOMATO));
        slice0.cells.add(new Cell(1, 0, Ingredient.TOMATO));
        slice0.cells.add(new Cell(1, 1, Ingredient.MUSHROOM));
        slice0.cells.add(new Cell(1, 2, Ingredient.TOMATO));

        slice1.cells.add(new Cell(2, 0, Ingredient.TOMATO));
        slice1.cells.add(new Cell(2, 1, Ingredient.MUSHROOM));
        slice1.cells.add(new Cell(2, 2, Ingredient.TOMATO));

        slice2.cells.add(new Cell(3, 0, Ingredient.TOMATO));
        slice2.cells.add(new Cell(3, 1, Ingredient.MUSHROOM));
        slice2.cells.add(new Cell(3, 2, Ingredient.TOMATO));
        slice2.cells.add(new Cell(4, 0, Ingredient.TOMATO));
        slice2.cells.add(new Cell(4, 1, Ingredient.TOMATO));
        slice2.cells.add(new Cell(4, 2, Ingredient.TOMATO));

        return Arrays.asList(slice0, slice1, slice2);
    }

    @Test
    public void parseExampleInput() throws IOException {
        Cell[][] ingredients = IoUtils.parsePizza(EXAMPLE_PIZZA_FILE);
        assertEquals("We expect" + EXAMPLE_PIZZA_FILE + "contains 3 rows", 3, ingredients.length);
        assertEquals("We expect" + EXAMPLE_PIZZA_FILE + "contains 5 columns", 5, ingredients[0].length);
        assertFalse("We expect no null value in ingredients", IoUtils.convertToHumanReadableTable(ingredients).contains("null"));
    }

    @Test
    public void parseExampleSliceInstructions() throws IOException {
        assertEquals("We expect min 1 ingredient per slice", 1,
                IoUtils.parseSliceInstructions(EXAMPLE_PIZZA_FILE).getMinNumberOfIngredientPerSlice().intValue());
        assertEquals("We expect max 6 cells per slice", 6,
                IoUtils.parseSliceInstructions(EXAMPLE_PIZZA_FILE).getMaxNumberOfCellsPerSlice().intValue());
    }

    @Test
    public void parseSlicesToOutputFormat() throws IOException, URISyntaxException {
        //Given a list of slices
        List<Slice> slicesForParagonOutputExample = createSlicesForParagonOutputExample();
        //Then parse slices according to the output format
        String outputDate = IoUtils.parseSlices(slicesForParagonOutputExample);
        IoUtils.writeToFile(TEST_OUTPUT_FILE, outputDate);
        assertEquals(IoUtils.readFromFile(PARAGON_OUTPUT_EXAMPLE_FILE), IoUtils.readFromFile(TEST_OUTPUT_FILE));
        //clean the file under the test
        Files.deleteIfExists(Paths.get(TEST_OUTPUT_FILE));
    }
}