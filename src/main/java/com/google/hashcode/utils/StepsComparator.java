package com.google.hashcode.utils;

import com.google.hashcode.entity.Step;

import java.util.Comparator;
import java.util.List;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class StepsComparator implements Comparator<List<Step>> {

    @Override
    public int compare(List<Step> o1, List<Step> o2) {
        long o1CellsCount = o1.stream()
                .flatMap(step -> step.delta.cells.stream())
                .count();
        long o2CellsCount = o2.stream()
                .flatMap(step -> step.delta.cells.stream())
                .count();
        return Long.compare(o1CellsCount, o2CellsCount);
    }
}