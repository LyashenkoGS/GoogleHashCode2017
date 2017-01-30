package com.google.hashcode.utils;

import com.google.hashcode.entity.Step;

import java.util.Comparator;
import java.util.List;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
//TODO examine this class work by parameterized tests
public class StepsComparator implements Comparator<List<Step>> {

    @Override
    public int compare(List<Step> o1, List<Step> o2) {
        long o1CellsCount = o1.stream()
                .mapToLong(step -> step.delta.cells.size())
                .sum();
        long o2CellsCount = o2.stream()
                .mapToLong(step -> step.delta.cells.size())
                .sum();
        return Long.compare(o1CellsCount, o2CellsCount);
    }
}