package com.google.hashcode.entity;

import com.google.hashcode.utils.InputFiles;
import com.google.hashcode.utils.IoUtils;
import org.junit.Test;

import java.io.File;

import static com.google.hashcode.utils.InputFiles.*;
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

}