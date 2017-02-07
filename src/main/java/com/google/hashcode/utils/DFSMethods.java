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
     * If used start position hasn't any steps and is invalid for a pizza ->
     * remove this slice from startPositions and add all it's cells to the pizza.
     *
     * @param pizza          given pizza(mutable)
     * @param startPositions given slices in the pizza(mutable)
     * @param output         list of valid slices(mutable)
     * @return available steps
     */
    public static Map<Slice, List<Step>> getAvailableSteps(Pizza pizza, List<Slice> startPositions, List<Slice> output) {
        Map<Slice, List<Step>> groupedSteps = new HashMap<>();
        Iterator iter;
        //optimization for big arrays
        if (startPositions.size() > 1_000) {
            List<Slice> startPositionsSubset = startPositions.subList(0, 20);
            iter = startPositionsSubset.iterator();
        }
        //iterate over all the start positions
        else {
            iter = startPositions.iterator();
        }
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

            if (steps.isEmpty()) {
                if (startPosition.isValid(pizza)) {
                    // if slice is valid and haven't any steps -> cut it from
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
        LOGGER.debug("available steps for" +
                "\npizza: " + pizza
                + "\nsteps: " + groupedSteps);
        return groupedSteps;
    }

    /**
     * Pick-ups a step with a minimal cells delta number,
     * execute it(cut it from the pizza, and add to a slice)
     *
     * @param pizza          given pizza(mutable)
     * @param step           step to perform
     * @param startPositions given start positions(mutable_
     * @param output         given list of output slices
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
     * @param steps given steps
     * @return optimal step
     */
    public static Step selectStep(Map<Slice, List<Step>> steps) {
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
            Optional<List<Step>> optionalStep = steps.values()
                    .stream()
                    .filter(steps1 -> !steps1.isEmpty())
                    .findFirst();
            if (optionalStep.isPresent()) {
                final Step step = optionalStep.get().get(0);
                LOGGER.debug("Selected step to perform:" + step);
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
        Profiler profiler = new Profiler();
        System.out.println("cutAllStartPosition start");
        List<Cell> mushrooms = pizza.getCells().stream()
                .filter(cell -> cell.ingredient.equals(Ingredient.MUSHROOM))
                .collect(Collectors.toList());
        System.out.println("m size = " + mushrooms.size());
        List<Cell> tomatoes = pizza.getCells().stream()
                .filter(cell -> cell.ingredient.equals(Ingredient.TOMATO))
                .collect(Collectors.toList());
        System.out.println("t size = " + tomatoes.size());
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
        LOGGER.info("pizza without start positions:"
                + "\n" + pizza);
        System.out.println(profiler.measure("cut All ss ends in "));
        return startPositions;
    }

}
