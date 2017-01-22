package com.google.hashcode;

import com.google.hashcode.entity.Cell;
import com.google.hashcode.entity.Pizza;
import com.google.hashcode.service.Slicer;
import com.google.hashcode.utils.IoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(Slicer.class);

    public static void main(String[] args) throws IOException {
        String exampleInputFile = "inputDataSets/example.in";
        Cell[][] ingredients = IoUtils.parsePizza(exampleInputFile);
        Pizza pizza = new Pizza(new File(exampleInputFile), ingredients, IoUtils.parseSliceInstructions(exampleInputFile));
        IoUtils.writeToFile("outputDataSet/example.txt", IoUtils.parseSlices(Slicer.slicePizza(pizza)));
        LOGGER.info("GoogleHashCode2017! Pizza task");

    }

}
