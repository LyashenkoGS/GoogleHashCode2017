package com.google.hashcode;

import com.google.hashcode.entity.Pizza;
import com.google.hashcode.entity.Slice;
import com.google.hashcode.entity.Step;
import com.google.hashcode.service.Slicer;
import com.google.hashcode.utils.DFSMethods;
import com.google.hashcode.utils.IoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.google.hashcode.utils.InputFiles.EXAMPLE_INPUT_FILE_PATH;


public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(Slicer.class);

    public static void main(String[] args) throws IOException {
        List<Slice> output;
        Pizza pizza = new Pizza(new File(EXAMPLE_INPUT_FILE_PATH), IoUtils.parsePizza(EXAMPLE_INPUT_FILE_PATH), IoUtils.parseSliceInstructions(EXAMPLE_INPUT_FILE_PATH));
        //get start positions
        output = DFSMethods.cutAllStartPositions(pizza);
        //get All steps
        Map<Slice, List<Step>> availableSteps = DFSMethods.getAvailableSteps(pizza, output);
        while (availableSteps.size() > 0) {
            Step step = DFSMethods.selectStep(availableSteps);
            output.remove(step.startPosition);
            output.add(DFSMethods.performStep(pizza, step));
            availableSteps = DFSMethods.getAvailableSteps(pizza, output);
            LOGGER.info("OUTPUT AFTER A STEP: "
            +"\n " + output);
        }
        IoUtils.writeToFile("outputDataSet/example.txt", IoUtils.parseSlices(output));
        LOGGER.info("GoogleHashCode2017! Pizza task");
        LOGGER.info(pizza.toString());

    }

}
