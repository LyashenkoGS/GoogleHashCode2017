package com.google.hashcode.utils;

import com.google.hashcode.entity.Cell;
import com.google.hashcode.entity.Ingredient;
import com.google.hashcode.entity.Pizza;
import com.google.hashcode.entity.Slice;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static com.google.hashcode.utils.InputFiles.EXAMPLE_INPUT_FILE_PATH;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class DFSMethodsTest {

    @Test
    public void rightStep() throws Exception {
        //Given a pizza
        Pizza pizza = new Pizza(new File(EXAMPLE_INPUT_FILE_PATH), IoUtils.parsePizza(EXAMPLE_INPUT_FILE_PATH), IoUtils.parseSliceInstructions(EXAMPLE_INPUT_FILE_PATH));
        //then perform a step right from a particular cell
        Slice actual = DFSMethods.rightStep(pizza, new Slice(pizza.getCell(1, 3))).get();
        Slice expected = new Slice(Arrays.asList(new Cell(1, 3, Ingredient.MUSHROOM), new Cell(1, 4, Ingredient.TOMATO)));
        Assert.assertEquals(expected, actual);
    }

}