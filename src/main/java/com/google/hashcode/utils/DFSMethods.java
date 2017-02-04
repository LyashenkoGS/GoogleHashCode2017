package com.google.hashcode.utils;

import com.google.hashcode.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public abstract class DFSMethods {
    private static final Logger LOGGER = LoggerFactory.getLogger(DFSMethods.class);

    private DFSMethods() {
    }

    /**
     * For each slice find all available steps.<br>
     * If founded start position that haven't any steps and it is unvalid ->
     * remove this slice from startPositions and add all it's cells to pizza.
     * @param pizza          given pizza
     * @param startPositions given slices in the pizza
     * @param output
     * @return available steps
     */
    public static Map<Slice, List<Step>> getAvailableSteps(Pizza pizza, List<Slice> startPositions, List<Slice> output) {
        Map<Slice, List<Step>> groupedSteps = new HashMap<>();
        Iterator iter = startPositions.iterator();
        while (iter.hasNext()) {
            Slice startPosition = (Slice) iter.next();

            List<Step> steps = new ArrayList<>();
            Step stepLeft = startPosition.generateStepLeft(pizza);
            Step stepRight = startPosition.generateStepRight(pizza);
            Step stepAbove = startPosition.generateStepAbove(pizza);
            Step stepBelow = startPosition.generateStepBelow(pizza);

            steps.add(stepRight);
            steps.add(stepLeft);
            steps.add(stepBelow);
            steps.add(stepAbove);
            steps = steps.stream().filter(Objects::nonNull).collect(Collectors.toList());

            if (steps.size() == 0) {
                if (startPosition.isValid(pizza)) {
                    // if slice is valid and have'nt any steps -> cut it from
                    // startPositions
                    output.add(startPosition);
                    iter.remove();
                } else {
                    // if slice isn't valid and have'nt any steps -> return all
                    // it cells to pizza
                    pizza.getCells().addAll(startPosition.cells);
                    iter.remove();
                }
            } else {
                groupedSteps.put(startPosition, steps);
            }
        }
        LOGGER.info("available steps for" +
                "\npizza: " + pizza
                + "\nsteps: " + groupedSteps);
        return groupedSteps;
    }

    /**
     * Pick-ups a step with a minimal cells delta number,
     * execute it(cut it from the pizza, and add to a slice)
     *
     * @param pizza given pizza
     * @param step  step to perform
     * @param startPositions
     *@param output @return formed slice that includes an original slice and delta from a step
     */
    public static void performStep(Pizza pizza, Step step, List<Slice> startPositions, List<Slice> output) {
        //1. Pick ups a steps list with minimal total cells number
        LOGGER.info("STEP TO PERFORM " + step);
        //2. Cut all the step delta cells from pizza
        LOGGER.info("pizza before step: " + pizza
                + "\ndelta to remove from the pizza: " + step.delta);
        pizza.getCells().removeAll(step.delta.cells);

        //3. remove previous version start position from startPositions
        startPositions.remove(step.startPosition);

        List<Cell> returnedList = step.startPosition.cells;
        returnedList.addAll(step.delta.cells);
        Slice finalSlice = new Slice(returnedList);

        LOGGER.info("PIZZA AFTER STEP:" + pizza);
        //3. Add the step cells to an output slice

        if(finalSlice.cells.size() == pizza.getSliceInstruction().getMaxNumberOfCellsPerSlice()){
            output.add(finalSlice);
        } else{
            startPositions.add(finalSlice);
        }
    }

    /**
     * Selects a step which start position has minimal delta in all the steps
     *
     * @param steps
     * @return
     */
    public static Step selectStep(Map<Slice, List<Step>> steps) {
        List<Step> min = steps.values().stream()
                .min(Comparator.comparingLong(value -> value.stream().map(step -> step.delta.cells.size()).count())).get();
        if (!min.isEmpty()) {
            LOGGER.info("steps list with minimal number of delta cells: " + min);
            return min.get(0);
        } else {
            Optional<List<Step>> optionalStep = steps.values().stream().filter(steps1 -> !steps1.isEmpty()).findFirst();
            if (optionalStep.isPresent()) {
                final Step step = optionalStep.get().get(0);
                LOGGER.info("Selected step to perform:" + step);
                return step;
            } else return null;
        }
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
        LOGGER.info("cutAllStartPositions for pizza: "
                + "\n" + pizza
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
        LOGGER.info("pizza without start positions:"
                + "\n" + pizza);
        return startPositions;
    }

}
