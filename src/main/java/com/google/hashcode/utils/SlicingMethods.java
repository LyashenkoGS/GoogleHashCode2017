package com.google.hashcode.utils;

import com.google.hashcode.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public abstract class SlicingMethods {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlicingMethods.class);

    private SlicingMethods() {
    }

    /**
     * Find all available steps for a given start positions, considering:<br>
     * * slice instructions for a given pizza<br>
     * * start position + slices should form a rectangle<br>
     * <p>
     * If there is no steps fo a given start position:<br>
     * 1. A start position is valid as a slice and can be cutted -> move it to output slices<br>
     * 2. A start position ISN'T  valid as a slice -> remove it from startPositions and add all it cells back to a pizza
     *
     * @param pizza          given pizza
     * @param startPositions given start positions in the pizza(a slice with cells number 1..max slice cells number)
     * @param output         list of valid and cutted slices
     * @return available steps
     */
    public static Map<Slice, List<Step>> getAvailableSteps(Pizza pizza, List<Slice> startPositions, List<Slice> output) {
        Map<Slice, List<Step>> groupedByAStartPositionSteps = new HashMap<>();
        Iterator iterator;
        //optimization for big arrays
        if (startPositions.size() > 1_000) {
            List<Slice> startPositionsSubset = startPositions.subList(0, 20);
            iterator = startPositionsSubset.iterator();
        }
        //iterate over all the start positions
        else {
            iterator = startPositions.iterator();
        }
        while (iterator.hasNext()) {
            Slice startPosition = (Slice) iterator.next();

            List<Step> steps = new ArrayList<>();
            Step stepLeft = startPosition.generateStepLeft(pizza);
            Step stepRight = startPosition.generateStepRight(pizza);
            Step stepAbove = startPosition.generateStepAbove(pizza);
            Step stepBelow = startPosition.generateStepBelow(pizza);

            steps.add(stepRight);
            steps.add(stepLeft);
            steps.add(stepBelow);
            steps.add(stepAbove);
            steps = steps.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            LOGGER.debug("There is no steps fo a given start position !");
            if (steps.isEmpty()) {
                LOGGER.debug("A start position is valid as a slice and can be cutted ->" +
                        " move it to output slices");
                if (startPosition.isValid(pizza)) {
                    output.add(startPosition);
                    iterator.remove();
                } else {
                    LOGGER.debug("A start position ISN'T valid as a slice -> " +
                            "remove it from startPositions and add all it cells");
                    pizza.getCells().addAll(startPosition.cells);
                    iterator.remove();
                }
            } else {
                groupedByAStartPositionSteps.put(startPosition, steps);
            }
        }
        LOGGER.debug("available steps for" +
                "\npizza: " + pizza
                + "\nsteps: " + groupedByAStartPositionSteps);
        return groupedByAStartPositionSteps;
    }

    /**
     * Performs a step with a minimal cells delta number and executes it (cut it from a pizza, and add to a slice)
     *
     * @param pizza          given pizza
     * @param step           step to perform
     * @param startPositions given start positions in the pizza(a slice with cells number 1..max slice cells number)
     * @param output         list of valid and cutted slices
     */
    public static void performStep(Pizza pizza, Step step, List<Slice> startPositions, List<Slice> output) {
        //1. Pick ups a steps list with minimal total cells number
        LOGGER.debug("STEP TO PERFORM " + step);
        //2. Cut all the step delta cells from pizza
        LOGGER.debug("pizza before step: " + pizza
                + "\ndelta to remove from the pizza: " + step.delta);
        pizza.getCells().removeAll(step.delta.cells);
        //3. remove step start position from total start positions
        startPositions.remove(step.startPosition);
        List<Cell> returnedList = step.startPosition.cells;
        returnedList.addAll(step.delta.cells);
        Slice finalSlice = new Slice(returnedList);
        LOGGER.debug("PIZZA AFTER STEP:" + pizza);
        //3. Add the step cells to an output slice if it's valid
        if (finalSlice.isValid(pizza)) {
            output.add(finalSlice);
        }
        //4. add start position + delta to start positions
        else {
            startPositions.add(finalSlice);
        }
    }

    /**
     * Selects a step which start position has minimal delta in all the steps
     *
     * @param steps available steps
     * @return a step with minimal delta
     */
    public static Step selectStep(Map<Slice, List<Step>> steps) {
        //TODO test and refactor this peace of shit properly !!
        List<Step> min = steps.values().stream()
                .min(Comparator.comparingLong(value ->
                        value.stream()
                                .map(step -> step.delta.cells.size())
                                .count()))
                .get();
        if (!min.isEmpty()) {
            LOGGER.debug("steps list with minimal number of delta cells: " + min);
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
     * * Finds a cell type(tomato or mushroom) with minimal cells numbers<br>
     * * Generates a list of one cell slices from them<br>
     * * Deletes the slices from the pizza<br>
     *
     * @param pizza given pizza
     * @return slices that are start positions for future slicing process
     */
    public static List<Slice> cutAllStartPositions(Pizza pizza) {
        //1.Finds a cell type(tomato or mushroom) with minimal cells numbers
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
            pizza.setCells(mushrooms);
        } else {
            startPositions = mushrooms.stream()
                    .map(Slice::new)
                    .collect(Collectors.toList());
            pizza.setCells(tomatoes);
        }
        LOGGER.debug("pizza with removed start positions:"
                + "\n" + pizza);
        return startPositions;
    }

}
