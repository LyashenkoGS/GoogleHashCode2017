package com.google.hashcode.utils;

import com.google.hashcode.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class DFSMethods {
    private static final Logger LOGGER = LoggerFactory.getLogger(DFSMethods.class);

    private DFSMethods() {
    }

    /**
     * For each slice find all available steps. We DON'T change the pizza on this stage
     *
     * @param pizza  given pizza
     * @param output given slices in the pizza
     * @return available steps
     */
    public static Map<Slice, List<Step>> getAvailableSteps(Pizza pizza, List<Slice> output) {
        Map<Slice, List<Step>> groupedSteps = new HashMap<>();
        for (Slice slice : output) {
            List<Step> steps = new ArrayList<>();
            Slice stepLeftDelta = slice.generateStepDeltaLeft();
            Slice stepRightDelta = slice.generateStepDeltaRight();
            Slice stepAboveDelta = slice.generateStepDeltaAbove();
            Slice stepBelowDelta = slice.generateStepDeltaBelow();
            if (pizza.containsCells(stepLeftDelta)) steps.add(new Step(slice, stepLeftDelta));
            if (pizza.containsCells(stepRightDelta)) steps.add(new Step(slice, stepRightDelta));
            if (pizza.containsCells(stepAboveDelta)) steps.add(new Step(slice, stepAboveDelta));
            if (pizza.containsCells(stepBelowDelta)) steps.add(new Step(slice, stepBelowDelta));
            groupedSteps.put(slice, steps);
        }
        LOGGER.info("available steps for" +
                "\npizza: " + pizza
                + "\nslices: " + output
                + "\nsteps: " + groupedSteps);
        return groupedSteps;
    }

    /**
     * Pick-ups a step with a minimal cells delta number,
     * execute it(cut it from the pizza, and add to a slice)
     *
     * @param pizza given pizza
     * @param steps available steps
     * @return formed slice that includes an original slice and delta from a step
     */
    public static Slice performStep(Pizza pizza, Map<Slice, List<Step>> steps) {
        //1. Pick ups a steps list with minimal total cells number
        Step step = steps.values().stream().min((o1, o2) -> new StepsComparator().compare(o1, o2)).get().get(0);
        LOGGER.info("step to perform: " + step);
        //2. Cut all the step delta cells from pizza
        LOGGER.info("pizza before step: " + pizza
                + "\ndelta to remove from the pizza: " + step.delta);
        pizza.getCells().removeAll(step.delta.cells);
        LOGGER.info("pizza after step:" + pizza);
        //3. Add the step cells to an output slice
        Slice slice = new Slice(step.delta.cells);
        slice.cells.addAll(step.startPosition.cells);
        return slice;
    }

    /**
     * Finds a cells type with minimal cells numbers and generates one cell slices from them
     * Delete the slices from the pizza
     *
     * @param pizza given pizza
     * @return slices that are start positions for future slicing
     */
    public static List<Slice> cutAllStartPositions(Pizza pizza) {
        List<Cell> mushrooms = pizza.getCells().stream()
                .filter(cell -> cell.ingredient.equals(Ingredient.MUSHROOM))
                .collect(Collectors.toList());
        List<Cell> tomatoes = pizza.getCells().stream()
                .filter(cell -> cell.ingredient.equals(Ingredient.TOMATO))
                .collect(Collectors.toList());
        LOGGER.info("cutAllStartPositions for pizza: " + pizza
                + "\nmushrooms number: " + mushrooms.size()
                + "\ntomatoes number: " + tomatoes.size());
        List<Slice> startPositions = null;
        if (mushrooms.size() > tomatoes.size()) {
            startPositions = tomatoes.stream()
                    .map(Slice::new)
                    .collect(Collectors.toList());
            List<Cell> cellsToRemove = startPositions.stream()
                    .flatMap(slice -> slice.cells.stream())
                    .collect(Collectors.toList());
            pizza.getCells().removeAll(cellsToRemove);
        } else {
            startPositions = mushrooms.stream()
                    .map(Slice::new)
                    .collect(Collectors.toList());
            List<Cell> cellsToRemove = startPositions.stream()
                    .flatMap(slice -> slice.cells.stream())
                    .collect(Collectors.toList());
            pizza.getCells().removeAll(cellsToRemove);
        }
        LOGGER.info("pizza without start positions:" + pizza);
        return startPositions;
    }

}
