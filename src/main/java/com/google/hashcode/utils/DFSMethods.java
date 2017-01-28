package com.google.hashcode.utils;

import com.google.hashcode.entity.Cell;
import com.google.hashcode.entity.Ingredient;
import com.google.hashcode.entity.Pizza;
import com.google.hashcode.entity.Slice;
import com.google.hashcode.entity.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
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
     * Step as an action it's a process, when a slice adding to itself a subset of a pizza cells and remains rectangular
     * and valid.
     *
     * @param pizza  given pizza
     * @param output given slice in the pizza
     * @return available steps
     */
    List<Step> getAvailableSteps(Pizza pizza, List<Slice> output) {
        //TODO implement.For each slice find all available steps. We DON'T change the pizza on this stage
        return new ArrayList<>();
    }

    Slice performStep(Pizza pizza, List<Step> steps) {
        //TODO pick-ups a step with a minimal steps number, execute it(cut it from the pizza, and a slice)
        return null;
    }

    List<Slice> cutAllStartPositions(Pizza pizza) {
        //TODO pick-ups a step with a minimal steps number, execute it(cut it from the pizza, and a slice). The pizza is a mutable object
       	List<Cell> currentPizza = pizza.getCells();
       	List<Slice> starts = new ArrayList<Slice>();
       	Iterator<Cell> iter = currentPizza.iterator();
       	while(iter.hasNext()){
       		Cell cell = iter.next();
       		if(cell.ingredient == Ingredient.MUSHROOM){
       			Slice slice = new Slice();
       			slice.cells.add(cell);
       			starts.add(slice);
       			iter.remove();
       		}
       	}
        return starts;
    }

}
