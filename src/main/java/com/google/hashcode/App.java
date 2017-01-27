package com.google.hashcode;

import com.google.hashcode.entity.Cell;
import com.google.hashcode.entity.Pizza;
import com.google.hashcode.service.Slicer;
import com.google.hashcode.utils.IoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.hashcode.utils.InputFiles.EXAMPLE_INPUT_FILE_PATH;


public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(Slicer.class);

    public static void main(String[] args) throws IOException {
        Pizza pizza = new Pizza(new File(EXAMPLE_INPUT_FILE_PATH), IoUtils.parsePizza(EXAMPLE_INPUT_FILE_PATH), IoUtils.parseSliceInstructions(EXAMPLE_INPUT_FILE_PATH));
        //  IoUtils.writeToFile("outputDataSet/example.txt", IoUtils.parseSlices(Slicer.slicePizza(pizza)));
        LOGGER.info("GoogleHashCode2017! Pizza task");
        LOGGER.info(pizza.toString());

    }

}
