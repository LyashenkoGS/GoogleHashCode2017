package com.google.hashcode.entity;

import java.util.ArrayList;

/**
 * Step as an entity is a slice tha can be added to a particular slice inside a particular pizza, considering
 * pizza's slice instructions
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Step {

    public Slice startPosition;
    public Slice delta;

    public Step(Slice startPosition, Slice delta) {
        super();
        this.startPosition = startPosition;
        this.delta = delta;
    }

    public boolean isValid(Pizza pizza) {
        Slice slice = new Slice(new ArrayList<>(startPosition.cells));
        slice.cells.addAll(delta.cells);
        return slice.isValid(pizza) ||
                slice.cells.size() < pizza.getSliceInstruction().getMaxNumberOfCellsPerSlice();
    }

    public int size() {
        return startPosition.cells.size() + delta.cells.size();
    }

    @Override
    public String toString() {
        return "\nStep{" +
                "\nstartPosition=" + startPosition.toString() +
                "\ndelta=" + delta.toString() +
                "\n}";
    }


}
