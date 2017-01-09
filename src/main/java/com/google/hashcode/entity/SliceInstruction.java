package com.google.hashcode.entity;

/**
 * Instructions how to slice a pizza
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class SliceInstruction {
    private final Integer minNumberOfIngredientPerSlice;
    private final Integer maxNumberOfCellsPerSlice;

    public SliceInstruction(Integer minNumberOfIngredientPerSlice, Integer maxNumberOfIngredientPerSlice) {
        this.minNumberOfIngredientPerSlice = minNumberOfIngredientPerSlice;
        this.maxNumberOfCellsPerSlice = maxNumberOfIngredientPerSlice;
    }

    public Integer getMinNumberOfIngredientPerSlice() {
        return minNumberOfIngredientPerSlice;
    }

    public Integer getMaxNumberOfCellsPerSlice() {
        return maxNumberOfCellsPerSlice;
    }

    @Override
    public String toString() {
        return "SliceInstructions: \n" +
                "min " + minNumberOfIngredientPerSlice + " ingredient per slice, " +
                "max " + maxNumberOfCellsPerSlice + " cells per slice ";
    }
}
