package com.google.hashcode.entity;

import com.google.hashcode.utils.IoUtils;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static com.google.hashcode.utils.InputFiles.EXAMPLE_INPUT_FILE_PATH;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        Slice validSlice = new Slice(Arrays.asList(new Cell(0, 0, Ingredient.TOMATO), new Cell(0, 1, Ingredient.MUSHROOM)));
        assertTrue(validSlice.isValid(pizza));
    }

}