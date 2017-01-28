package com.google.hashcode.entity;

import com.google.hashcode.utils.IoUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.google.hashcode.utils.InputFiles.EXAMPLE_INPUT_FILE_PATH;
import static org.junit.Assert.assertEquals;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class PizzaTest {

    @Test
    public void getCell() throws Exception {
        Pizza pizza = new Pizza(new File(EXAMPLE_INPUT_FILE_PATH), IoUtils.parsePizza(EXAMPLE_INPUT_FILE_PATH), IoUtils.parseSliceInstructions(EXAMPLE_INPUT_FILE_PATH));
        assertEquals(pizza.getCell(0, 0), new Cell(0, 0, Ingredient.TOMATO));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCellException() throws Exception {
        Pizza pizza = new Pizza(new File(EXAMPLE_INPUT_FILE_PATH), IoUtils.parsePizza(EXAMPLE_INPUT_FILE_PATH), IoUtils.parseSliceInstructions(EXAMPLE_INPUT_FILE_PATH));
        pizza.getCell(100500, 0);
    }

    @Test
    public void testToString() throws IOException {
        Pizza pizza = new Pizza(new File(EXAMPLE_INPUT_FILE_PATH), IoUtils.parsePizza(EXAMPLE_INPUT_FILE_PATH), IoUtils.parseSliceInstructions(EXAMPLE_INPUT_FILE_PATH));
        System.out.println(pizza);
        assertEquals("inputDataSets/example.inSliceInstructions: \n" +
                "min 1 ingredient per slice, max 6 cells per slice \n" +
                "  0 1 2 3 4\n" +
                "0 T T T T T \n" +
                "1 T M M M T \n" +
                "2 T T T T T", pizza.toString());
    }

}