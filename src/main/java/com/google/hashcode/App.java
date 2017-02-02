package com.google.hashcode;

import com.google.hashcode.entity.Pizza;
import com.google.hashcode.entity.Slice;
import com.google.hashcode.entity.Step;
import com.google.hashcode.utils.DFSMethods;
import com.google.hashcode.utils.IoUtils;
import com.google.hashcode.utils.ThreadMethods;

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
//        slicePizza(EXAMPLE_INPUT_FILE_PATH, OUTPUT_DATA_SET_EXAMPLE_TXT);
//        slicePizza(SMALL_INPUT_FILE_PATH, OUTPUT_DATA_SET_SMALL_TXT);
        //TODO troubles to input big files, possible exciting String max size
//        slicePizza(BIG_INPUT_FILE_PATH, OUTPUT_DATA_SET_BIG_TXT);
        slicePizza(MEDIUM_INPUT_FILE_PATH, OUTPUT_DATA_SET_MEDIUM_TXT);
    }

    public static void slicePizza(String inputFile, String outputFile) throws IOException {
        List<Slice> output = new ArrayList<Slice>();
        List<Slice> startPositions = new ArrayList<Slice>();
        long t1 = System.currentTimeMillis();
        System.out.println("algoritm starts");
        Pizza pizza = new Pizza(new File(inputFile), IoUtils.parsePizza(inputFile, startPositions), IoUtils.parseSliceInstructions(inputFile));
        //get start positions
        // all start points are sets in parsePizza (it faster on 2 sec on my home ps)
//        startPositions = DFSMethods.cutAllStartPositions(pizza);
        //get All steps
//        Map<Slice, List<Step>> availableSteps = DFSMethods.getAvailableSteps(pizza, startPositions, output);
        Map<Slice, List<Step>> availableSteps = ThreadMethods.threadAvailableSteps(pizza, startPositions, output);
        while (!availableSteps.values().stream().allMatch(List::isEmpty)) {
        	System.out.println("START POSITIONS = " + startPositions.size());
        	System.out.println("OUTPUT = " + output.size());
//            Step step = DFSMethods.selectStep(availableSteps);
//            startPositions.remove(step.startPosition);
//            startPositions.add(DFSMethods.performStep(pizza, step));
        	DFSMethods.performStep(pizza, startPositions, output, availableSteps);
            //TODO available steps should include merging slices to each other
//            availableSteps = DFSMethods.getAvailableSteps(pizza, startPositions, output);
        	availableSteps = ThreadMethods.threadAvailableSteps(pizza, startPositions, output);
        }
        IoUtils.writeToFile(outputFile, IoUtils.parseSlices(output));
        long t2 = System.currentTimeMillis();
        System.out.println("algoritm ends in "+ (t2-t1) + " miliseconds");
        LOGGER.info("FINISHED for " + inputFile + "!!!!!");
    }

}
