package com.google.hashcode.entity;

import java.util.List;

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

    @Override
    public String toString() {
        return "Step{" +
                "startPosition=" + startPosition +
                ", delta=" + delta +
                '}';
    }

	public static boolean isValid(Pizza pizza, Slice startSlice, Slice checkedSlice){
		if( pizza.containsCells(checkedSlice) &&
				startSlice.cells.size() + checkedSlice.cells.size() <= pizza.getSliceInstruction().getMaxNumberOfCellsPerSlice()){
			return true;
		}

		return false;
	}

}
