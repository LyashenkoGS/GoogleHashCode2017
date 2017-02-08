package com.google.hashcode;

import com.google.hashcode.entity.Pizza;
import com.google.hashcode.entity.Slice;
import com.google.hashcode.entity.Step;
import com.google.hashcode.utils.DFSMethods;
import com.google.hashcode.utils.IoUtils;
import com.google.hashcode.utils.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.hashcode.utils.FilesPaths.*;


public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        //slicePizza(EXAMPLE_INPUT_FILE_PATH, OUTPUT_DATA_SET_EXAMPLE_TXT);
        //slicePizza(SMALL_INPUT_FILE_PATH, OUTPUT_DATA_SET_SMALL_TXT);
        slicePizza(MEDIUM_INPUT_FILE_PATH, OUTPUT_DATA_SET_MEDIUM_TXT);
        //TODO troubles to input big files, possible exciting String max size
//        slicePizza(BIG_INPUT_FILE_PATH, OUTPUT_DATA_SET_BIG_TXT);
    }

    public static void slicePizza(String inputFile, String outputFile) throws IOException {
        Profiler profiler = new Profiler();
        List<Slice> startPositions;
        List<Slice> output = new ArrayList<>();
        Pizza pizza = new Pizza(new File(inputFile), IoUtils.parsePizza(inputFile), IoUtils.parseSliceInstructions(inputFile));
        //get start positions
        startPositions = DFSMethods.cutAllStartPositions(pizza);
        //get All steps
        Map<Slice, List<Step>> availableSteps = DFSMethods.getAvailableSteps(pizza, startPositions, output);
        while (!availableSteps.values().stream().allMatch(List::isEmpty)) {
        	DFSMethods.performAllSteps(pizza, availableSteps, startPositions, output);
        	//TODO available steps should include merging slices to each other
            availableSteps = DFSMethods.getAvailableSteps(pizza, startPositions, output);
        }
        IoUtils.writeToFile(outputFile, IoUtils.parseSlices(output));
        System.out.println("start positions = " + startPositions.size() + " output = "+output.size()+" pizza = " + pizza.getCells().size());
        LOGGER.info(profiler.measure(inputFile + " execution time: "));
    }

}
