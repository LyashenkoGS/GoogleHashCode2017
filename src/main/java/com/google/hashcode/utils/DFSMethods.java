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
	 * For each slice find all available steps. We DON'T change the pizza on
	 * this stage
	 *
	 * @param pizza
	 *            given pizza
	 * @param startPositions
	 *            given slices in the pizza
	 * @return available steps
	 */
	static int counter = 0;
	public static Map<Slice, List<Step>> getAvailableSteps(Pizza pizza, List<Slice> startPositions,
			List<Slice> output) {
		long t1 = System.currentTimeMillis();
		System.out.println("getAvailableSteps() start " + ++counter + "-st time");
		Map<Slice, List<Step>> groupedSteps = new HashMap<>();
		Iterator<Slice> iter = startPositions.iterator();
		while (iter.hasNext()) {
			Slice startPosition = iter.next();
			List<Step> steps = new ArrayList<>();
			Step stepLeft = startPosition.generateStep(pizza, Direction.LEFT);
			Step stepRight = startPosition.generateStep(pizza, Direction.RIGHT);
			Step stepAbove = startPosition.generateStep(pizza, Direction.UP);
			Step stepBelow = startPosition.generateStep(pizza, Direction.DOWN);

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
		System.out.println("TOTAL STEPS = " + groupedSteps.values().size() + " STEPS");
		long t2 = System.currentTimeMillis() - t1;
		System.out.println("getAvailableSteps() finished in " + (t2) + " milliseconds");
//		LOGGER.info("availadle steps count = " + groupedSteps.size());
		return groupedSteps;
	}

	/**
	 * Pick-ups a step with a minimal cells delta number, execute it(cut it from
	 * the pizza, and add to a slice)
	 *
	 * @param pizza
	 *            given pizza
	 * @param step
	 *            step to perform
	 * @return formed slice that includes an original slice and delta from a
	 *         step
	 */
	public static Slice performStep(Pizza pizza, Step step) {
		// 1. Pick ups a steps list with minimal total cells number
//		LOGGER.info("STEP TO PERFORM " + step.size());
		// 2. Cut all the step delta cells from pizza
		pizza.getCells().removeAll(step.delta.cells);
//		LOGGER.info("PIZZA AFTER STEP:" + pizza.getCells().size());
		// 3. Add the step cells to an output slice
		Slice slice = new Slice(step.delta.cells);
		slice.cells.addAll(step.startPosition.cells);
		return slice;
	}

	/**
	 * Selects a step which start position has minimal delta in all the steps
	 *
	 * @param steps
	 * @return
	 */
	public static Step selectStep(Map<Slice, List<Step>> steps) {
		List<Step> min = steps.values().stream()
				.min(Comparator.comparingLong(value -> value.stream().map(step -> step.delta.cells.size()).count()))
				.get();
		if (!min.isEmpty()) {
			return min.get(0);
		} else {
			Optional<List<Step>> optionalStep = steps.values().stream().filter(steps1 -> !steps1.isEmpty()).findFirst();
			if (optionalStep.isPresent()) {
				final Step step = optionalStep.get().get(0);
				return step;
			} else
				return null;
		}
	}

	/**
	 * Finds a cells type with minimal cells numbers and generates one cell
	 * slices from them Delete the slices from the pizza
	 *
	 * @param pizza
	 *            given pizza
	 * @return slices that are start positions for future slicing
	 */
//	all logic was remowed to pizza cutting
//	public static List<Slice> cutAllStartPositions(Pizza pizza) {
//		long t1 = System.currentTimeMillis();
//		System.out.println("cutAllStartPositions() starts");
//		List<Cell> mushrooms = new ArrayList<Cell>();
//		List<Cell> tomatoes = new ArrayList<Cell>();
//		for (Cell cell : pizza.getCells()) {
//			if(cell.ingredient.equals(Ingredient.TOMATO)){
//				tomatoes.add(cell);
//			} else{
//				mushrooms.add(cell);
//			}
//		}
//		List<Slice> startPositions = new ArrayList<Slice>();
//		if (mushrooms.size() > tomatoes.size()) {
//			for (Cell cell : tomatoes) {
//				startPositions.add(new Slice(cell));
//			}
//			pizza.getCells().removeAll(tomatoes);
//		} else {
//			for (Cell cell : mushrooms) {
//				startPositions.add(new Slice(cell));
//			}
//			pizza.getCells().removeAll(mushrooms);
//		}
//		long t2 = System.currentTimeMillis();
//		System.out.println("method cutAllStartPositions() finished in " + (t2 - t1) + " miliseconds");
//		return startPositions;
//	}

}
