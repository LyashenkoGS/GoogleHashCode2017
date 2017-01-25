package com.google.hashcode.utils;

import com.google.hashcode.entity.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DFSMethods {

    public int CalculateNumberOfFreeCellsAroundSlice(List<Cell> slice, List<Cell> pizza) {
        int cellsAroundSlice = 0;
        //found coordinates of slice angles.
        int minX = findMinX(slice);
        int minY = findMinY(slice);
        int maxX = findMaxX(slice);
        int maxY = findMaxY(slice);
        //generate arrays of x and y coordinates for this slice.
        List<Integer> differenceXCoordinates = fillArrayWithIntValues(minX, maxX);
        List<Integer> differenceYCoordinates = fillArrayWithIntValues(minY, maxY);
        //checking top cells.
        if (didPizzaContainsAllNeededHorizontalCells(pizza, differenceXCoordinates, minY - 1))
            cellsAroundSlice += differenceXCoordinates.size();
        //checking left cells.
        if (didPizzaContainsAllNeededVerticalCells(pizza, differenceYCoordinates, maxX + 1))
            cellsAroundSlice += differenceYCoordinates.size();
        //checking bottom cells.
        if (didPizzaContainsAllNeededHorizontalCells(pizza, differenceXCoordinates, maxY + 1))
            cellsAroundSlice += differenceXCoordinates.size();
        //checking right cells.
        if (didPizzaContainsAllNeededVerticalCells(pizza, differenceYCoordinates, minX - 1))
            cellsAroundSlice += differenceYCoordinates.size();
        return cellsAroundSlice;
    }

    //region steps

    public boolean stepUp(List<Cell> slice, List<Cell> pizza, int maxCellsInSlice) {
        // finding min y coordinate in slice.
        Integer minYCoordInSlice = findMinY(slice);
        // find all difference x coordinates.
        List<Integer> differenceXCoordinates = fillAllDifferenceX(slice);
        //prevent this method from cutting too big slice.
        if (slice.size() + differenceXCoordinates.size() > maxCellsInSlice) return false;
        //our pizza still have all needed cells for this step? lets check it.
        boolean pizzaContainsAllNeededCells = didPizzaContainsAllNeededHorizontalCells(pizza, differenceXCoordinates, minYCoordInSlice - 1);
        if (pizzaContainsAllNeededCells) {
            //Cut all needed cells from pizza and add their to our slice.
            for (int xCoord : differenceXCoordinates) {
                //use .remove(index) to get object from pizza (with it's Ingradient)
                //but for getting those index use .indexOf(Object o)
                //TODO fix a compile time error. slice.add(pizza.remove(pizza.indexOf(new Cell(xCoord, minYCoordInSlice))))
            }
            return true;
        } else {
            //oups, pizza doesn't contain one of needed cells, step blocked.
            return false;
        }
    }

    public boolean stepRight(List<Cell> slice, List<Cell> pizza, int maxCellsInSlice) {
        //TODO fix a compile time error
        /*
        Integer maxXCoordInSlice = findMaxX(slice);
        List<Integer> differenceYCoordinates = fillAllDifferenceY(slice);
        ;
        if (slice.size() + differenceYCoordinates.size() > maxCellsInSlice) return false;
        boolean pizzaContainsAllNeededCells = didPizzaContainsAllNeededVerticalCells(pizza, differenceYCoordinates, maxXCoordInSlice + 1);
        if (pizzaContainsAllNeededCells) {
            for (int yCoord : differenceYCoordinates) {
                slice.add(pizza.remove(pizza.indexOf(new Cell(minXCoordInSlice, yCoord))))
            }
            return true;
        } else {
            return false;
        }*/
        return true;
    }

    public boolean stepDown(List<Cell> slice, List<Cell> pizza, int maxCellsInSlice) {
        //TODO fix a compile time error
        /*     Integer maxYCoordInSlice = findMaxY(slice);
        List<Integer> differenceXCoordinates = fillAllDifferenceX(slice);
        if (slice.size() + differenceXCoordinates.size() > maxCellsInSlice) return false;
        boolean pizzaContainsAllNeededCells = didPizzaContainsAllNeededVerticalCells(pizza, differenceXCoordinates, maxYCoordInSlice + 1);
        if (pizzaContainsAllNeededCells) {
            for (int xCoord : differenceXCoordinates) {
                slice.add(pizza.remove(pizza.indexOf(new Cell(xCoord, maxYCoordInSlice))))
            }
            return true;
        } else {
            return false;
        }*/
        return true;
    }

    public boolean stepLeft(List<Cell> slice, List<Cell> pizza, int maxCellsInSlice) {
        //TODO fix a compile time error
      /*  Integer minXCoordInSlice = findMinX(slice);
        List<Integer> differenceYCoordinates = fillAllDifferenceY(slice);
        if (slice.size() + differenceYCoordinates.size() > maxCellsInSlice) return false;
        boolean pizzaContainsAllNeededCells = didPizzaContainsAllNeededVerticalCells(pizza, differenceYCoordinates, minXCoordInSlice - 1);
        if (pizzaContainsAllNeededCells) {
            for (int yCoord : differenceYCoordinates) {
                slice.add(pizza.remove(pizza.indexOf(new Cell(minXCoordInSlice, yCoord))))
            }
            return true;
        } else {
            return false;
        }*/
        return true;
    }
    //endregion

    //region utils

    private List<Integer> fillAllDifferenceX(List<Cell> slice) {
        List<Integer> diffX = new ArrayList<Integer>();
        for (Cell cell : slice) {
            if (!diffX.contains(cell.x)) {
                diffX.add(cell.x);
            }
        }
        return diffX;
    }

    private List<Integer> fillAllDifferenceY(List<Cell> slice) {
        List<Integer> diffY = new ArrayList<Integer>();
        for (Cell cell : slice) {
            if (!diffY.contains(cell.y)) {
                diffY.add(cell.y);
            }
        }
        return diffY;
    }

    private List<Integer> fillArrayWithIntValues(int start, int end) {
        List<Integer> returnedList = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            returnedList.add((Integer) i);
        }
        return returnedList;
    }

    private int findMinY(List<Cell> slice) {
        return Collections.min(slice, Comparator.comparingInt(Cell::getX)).y;
    }

    private int findMaxY(List<Cell> slice) {
        return Collections.max(slice, Comparator.comparingInt(Cell::getX)).y;
    }

    private int findMinX(List<Cell> slice) {
        return Collections.min(slice, Comparator.comparingInt(Cell::getX)).x;
    }

    private int findMaxX(List<Cell> slice) {
        return Collections.max(slice, Comparator.comparingInt(Cell::getX)).x;
    }

    private boolean didPizzaContainsAllNeededHorizontalCells(List<Cell> pizza, List<Integer> differenceXCoordinate, Integer yCoord) {
        //TODO fix a compile time error
       /* boolean returnValue = true;
        for (Integer xCoord : differenceXCoordinate) {
            if (!pizza.contains(new Cell(xCoord, yCoord))) {
                returnValue = false;
            }
        }
        return returnValue;*/
        return true;
    }

    private boolean didPizzaContainsAllNeededVerticalCells(List<Cell> pizza, List<Integer> differenceYCoordinate, Integer xCoord) {
        //TODO fix a compile time error
     /*   boolean returnValue = true;
        for (Integer yCoord : differenceYCoordinate) {
            if (!pizza.contains(new Cell(xCoord, yCoord))) {
                returnValue = false;
            }
        }
        return returnValue;*/
        return true;
    }

    //endregion
}
