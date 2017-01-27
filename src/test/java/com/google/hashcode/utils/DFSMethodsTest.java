package com.google.hashcode.utils;

import com.google.hashcode.entity.Cell;
import com.google.hashcode.entity.Ingredient;
import com.google.hashcode.entity.Pizza;
import com.google.hashcode.entity.Slice;
import org.junit.Test;

import java.io.File;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class DFSMethodsTest {

    @Test
    public void rightStep() throws Exception {
        //Given a pizza
        String exampleInputFile = "inputDataSets/example.in";
        Pizza pizza = new Pizza(new File(exampleInputFile), IoUtils.parsePizza(exampleInputFile), IoUtils.parseSliceInstructions(exampleInputFile));

        //then perform a step right from a particular cell
        DFSMethods.rightStep(pizza, new Slice(new Cell(1, 1, Ingredient.MUSHROOM)));
    }

}