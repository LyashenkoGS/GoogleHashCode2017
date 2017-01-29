package com.google.hashcode;

import com.google.hashcode.entity.Pizza;
import com.google.hashcode.entity.Slice;
import com.google.hashcode.entity.Step;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        slicePizza(EXAMPLE_INPUT_FILE_PATH, "outputDataSet/example.txt");

    }

    public static void slicePizza(String exampleInputFilePath, String outputFile) throws IOException {
        List<Slice> output;
        Pizza pizza = new Pizza(new File(exampleInputFilePath), IoUtils.parsePizza(exampleInputFilePath), IoUtils.parseSliceInstructions(exampleInputFilePath));
        //get start positions
        output = DFSMethods.cutAllStartPositions(pizza);
        //get All steps
        Map<Slice, List<Step>> availableSteps = DFSMethods.getAvailableSteps(pizza, output);
        while (!availableSteps.values().stream().allMatch(List::isEmpty)) {
            Step step = DFSMethods.selectStep(availableSteps);
            output.remove(step.startPosition);
            output.add(DFSMethods.performStep(pizza, step));
            availableSteps = DFSMethods.getAvailableSteps(pizza, output);
            LOGGER.info("OUTPUT AFTER A STEP: "
                    + "\n " + output);
        }
        IoUtils.writeToFile(outputFile, IoUtils.parseSlices(output));
        LOGGER.info("FINISHED for " + exampleInputFilePath + "!!!!!");
    }

}
