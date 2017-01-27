package com.google.hashcode.utils;

import com.google.hashcode.entity.Cell;
import com.google.hashcode.entity.Pizza;
import com.google.hashcode.entity.Slice;
import com.google.hashcode.entity.SliceInstruction;
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
     * Calculates a number of cells around the given slice. Available cells is cells that can be merged
     * into the slice, so it will remain a rectangle, so only up,down,left,right around an each slice outside cell.
     * No on the diagonal !
     *
     * @param slice given pizza
     * @param pizza given slice
     * @return number of cells available to merge into the slice
     */
    public static int calculateNumberOfFreeCellsAroundSlice(Slice slice, Pizza pizza) {

        return 0;
    }

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
     * Step as an action it's a process, when a slice adding to itself a subset of a pizza cells and remains rectangular.
     * Step as an entity is a Slice tha can be added to a particular slice inside a particalur pizza
     * considering slice instructions
     *
     * @param pizza            given pizza
     * @param slice            given slice in the pizza
     * @param sliceInstruction restrictions for a steps
     * @return available steps
     */
    List<Slice> getAvailableSteps(Pizza pizza, Slice slice, SliceInstruction sliceInstruction) {
        return new ArrayList<>();
    }

}
