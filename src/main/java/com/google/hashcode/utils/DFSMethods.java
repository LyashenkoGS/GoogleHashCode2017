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
