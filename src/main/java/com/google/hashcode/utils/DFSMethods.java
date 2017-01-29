package com.google.hashcode.utils;

import com.google.hashcode.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class DFSMethods {
    private static final Logger LOGGER = LoggerFactory.getLogger(DFSMethods.class);

    private DFSMethods() {
    }

    /**
     * Step as an entity is an amount of a pizza cells, that can be added to a slice of a pizza or a pizza cell.<br>
     * Step as an action is a process of:<br>
     * <p>
     * * adding cells to a start cell or a start slice<br>
     * * validate generated slice according to the pizza slicing instructions<br>
     * * if validation passed ->cutting the start cell with added cells from the pizza
     *
     * @param pizza (mutable) a pizza to perform step on
     * @param slice start position for a step
     * @return cutted slice from the pizza
     */
    public static Optional<Slice> rightStep(Pizza pizza, Slice slice) {
        List<Cell> slicesRightBorder = slice.cells.stream()
                .filter(cell -> (cell.x == slice.maxX()) && (cell.y >= slice.minY()) && (cell.y <= slice.maxY()))
                .collect(Collectors.toList());
        //each cell should have a cell right side of it in the pizza
        try {
            Slice step = new Slice(slicesRightBorder.stream()
                    .map(slicesRightBorderCell -> pizza.getCell(slicesRightBorderCell.y, slicesRightBorderCell.x + 1))
                    .collect(Collectors.toList()));
            //check is step is valid
            Slice sliceAndStep = new Slice(new ArrayList<>(slice.cells));
            sliceAndStep.cells.addAll(step.cells);
            if (!slice.cells.isEmpty() && sliceAndStep.isValid(pizza)) {
                //remove the slice and step from the pizza
                pizza.getCells().removeAll(sliceAndStep.cells);
                return Optional.of(sliceAndStep);
            } else {
                return Optional.empty();
            }
        } catch (IllegalArgumentException e) {
            //if can't add at least one neccessary cell - > return an empty step
            LOGGER.info("Can't perform a step right !");
            return Optional.empty();
        }
    }

    /**
     * For each slice find all available steps. We DON'T change the pizza on this stage
     *
     * @param pizza  given pizza
     * @param output given slices in the pizza
     * @return available steps
     */
    public static List<Step> getAvailableSteps(Pizza pizza, List<Slice> output) {
        List<Step> steps = new ArrayList<>();
        for (Slice slice : output) {
            Slice stepLeftDelta = slice.generateStepDeltaLeft();
            Slice stepRightDelta = slice.generateStepRight();
            Slice stepAboveDelta = slice.generateStepDeltaAbove();
            Slice stepBelowDelta = slice.generateStepDeltaBelow();
            if (pizza.containsCells(stepLeftDelta)) steps.add(new Step(slice, stepLeftDelta));
            if (pizza.containsCells(stepRightDelta)) steps.add(new Step(slice, stepRightDelta));
            if (pizza.containsCells(stepAboveDelta)) steps.add(new Step(slice, stepAboveDelta));
            if (pizza.containsCells(stepBelowDelta)) steps.add(new Step(slice, stepBelowDelta));
        }
        LOGGER.info("available steps for" +
                "\npizza: " + pizza
                + "\nslices: " + output
                + "\nsteps: " + steps);
        return steps;
    }

    public static Slice performStep(Pizza pizza, List<Step> steps) {
        //TODO pick-ups a step with a minimal steps number, execute it(cut it from the pizza, and a slice)
        return null;
    }

    /**
     * Finds a cells type with minimal cells numbers and generates one cell slices from them
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
        if (mushrooms.size() > tomatoes.size()) {
            return tomatoes.stream()
                    .map(Slice::new)
                    .collect(Collectors.toList());
        } else {
            return mushrooms.stream()
                    .map(Slice::new)
                    .collect(Collectors.toList());
        }
    }

}
