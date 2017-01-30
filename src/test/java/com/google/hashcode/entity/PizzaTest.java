package com.google.hashcode.entity;

import com.google.hashcode.utils.IoUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static com.google.hashcode.utils.FilesPaths.EXAMPLE_INPUT_FILE_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class PizzaTest {

    private Pizza examplePizza;

    @Before
    public void setup() throws IOException {
        examplePizza = new Pizza(new File(EXAMPLE_INPUT_FILE_PATH), IoUtils.parsePizza(EXAMPLE_INPUT_FILE_PATH), IoUtils.parseSliceInstructions(EXAMPLE_INPUT_FILE_PATH));
    }

    @Test
    public void getCell() throws Exception {
        assertEquals(examplePizza.getCell(0, 0), Optional.of(new Cell(0, 0, Ingredient.TOMATO)));
    }

    @Test
    public void getCellException() throws Exception {
        Optional<Cell> cell = examplePizza.getCell(100500, 0);
        assertFalse(cell.isPresent());
    }

    @Test
    public void testToString() throws IOException {
        assertEquals("inputDataSets/example.inSliceInstructions: \n" +
                "min 1 ingredient per slice, max 6 cells per slice \n" +
                "  0 1 2 3 4\n" +
                "0 T T T T T \n" +
                "1 T M M M T \n" +
                "2 T T T T T", examplePizza.toString());
    }

    @Test
    public void containsCells() {
        //given a slice based on the pizza cells

    }

}