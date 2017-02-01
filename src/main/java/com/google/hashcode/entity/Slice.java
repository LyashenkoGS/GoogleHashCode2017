package com.google.hashcode.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.StandardSocketOptions;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A rectangle piece of a pizza
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Slice {
    private static final Logger LOGGER = LoggerFactory.getLogger(Slice.class);

    public List<Cell> cells = new ArrayList<>();

    public Slice() {
    }

    public Slice(Cell... cell) {
        this.cells = new ArrayList<>(Arrays.asList(cell));
    }

    public Slice(List<Cell> cells) {
        this.cells = cells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Slice)) return false;
        Slice slice = (Slice) o;
        return Objects.equals(cells, slice.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells);
    }

    public int minX() {
        return Collections.min(cells, Comparator.comparingInt(Cell::getX)).x;
    }

    public int minY() {
        return Collections.min(cells, Comparator.comparingInt(Cell::getY)).y;
    }

    public int maxX() {
        return Collections.max(cells, Comparator.comparingInt(Cell::getX)).x;
    }

    public int maxY() {
        return Collections.max(cells, Comparator.comparingInt(Cell::getY)).y;
    }


    /**
     * Coordinates are like in a 2D array
     *
     * @param y - row number, 0..max row number
     * @param x - column number,0..max column number
     * @return a pizza cell with specified coordinated
     */
    public Optional<Cell> getCell(int y, int x) {
        return cells.stream().filter(cell -> cell.x == x && cell.y == y).findFirst();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("slice : \n");
        //output coordinates
        int columnsCount = cells.stream().max(Comparator.comparingInt(Cell::getX)).get().getX();
        int rowsCount = cells.stream().max(Comparator.comparingInt(Cell::getY)).get().getY();
        //output columns coordinates
        stringBuilder.append(" ");
        for (int column = 0; column < columnsCount + 1; column++) {
            stringBuilder.append(" ").append(column);
        }
        stringBuilder.append("\n");
        for (int row = 0; row < rowsCount + 1; row++) {
            //output rows coordinates
            stringBuilder.append(row).append(" ");
            for (int column = 0; column < columnsCount + 1; column++) {
                if (this.getCell(row, column).isPresent()) {
                    stringBuilder.append(this.getCell(row, column).get().toString()).append(" ");
                } else {
                    stringBuilder.append(" ").append(" ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString().trim();
    }

    /**
     * check if slice valid for current pizza.
     *
     * @param pizza
     * @return
     */
    public boolean isValid(Pizza pizza) {
        //TODO check rectangularity
        int mushroomsNumber = this.cells.stream()
                .filter(cell -> cell.ingredient.equals(Ingredient.MUSHROOM))
                .collect(Collectors.toList())
                .size();
        int tomatoesNumber = this.cells.stream()
                .filter(cell -> cell.ingredient.equals(Ingredient.TOMATO))
                .collect(Collectors.toList())
                .size();
        boolean isPassedSliceInstructions = this.cells.size() <= pizza.getSliceInstruction().getMaxNumberOfCellsPerSlice()
                && tomatoesNumber >= pizza.getSliceInstruction().getMinNumberOfIngredientPerSlice()
                && mushroomsNumber >= pizza.getSliceInstruction().getMinNumberOfIngredientPerSlice();
        return isPassedSliceInstructions;
    }
    
    int counter = 0;
    //single method for generating all steps
    public Step generateStep(Pizza pizza, Direction direction){
    	Slice delta = new Slice();
    	int maxSliseSize = pizza.getSliceInstruction().getMaxNumberOfCellsPerSlice();
    	switch(direction){
    		case UP:
//    			validation on max slice size
    			if( (this.cells.size() + (this.maxX()-this.minX())) > maxSliseSize ) return null;
    			for(int x = this.minX(); x <= this.maxX(); x++){
    				Optional<Cell> cell = pizza.getCell(minY()-1, x);
//    				validation on containing step cells in pizza
    				if(cell.isPresent()){
    					delta.cells.add(cell.get());
    				}else{
//    					Pizza doesn't contain one of step cells. This step is blocked
    					return null;
    				}
    			}
    			break;
    		case RIGHT:
//    			validation on max slice size
    			if( (this.cells.size() + (this.maxY()-this.minY())) > maxSliseSize ) return null;
    			for (int y = this.minY(); y <= this.maxY(); y++) {
    				Optional<Cell> cell = pizza.getCell(y, maxX() + 1);
//    				validation on containing step cells in pizza
    				if(cell.isPresent()){
    					delta.cells.add(cell.get());
   				 	}else{
//   				 	Pizza doesn't contain one of step cells. This step is blocked;
   				 		return null;
   				 	}
    			}
    			break;
    		case DOWN:
//    			validation on max slice size
    			if( (this.cells.size() + (this.maxX()-this.minX())) > maxSliseSize ) return null;
    			for(int x = this.minX(); x <= this.maxX(); x++){
    				Optional<Cell> cell = pizza.getCell(maxY()+1, x);
//    				validation on containing step cells in pizza
    				if(cell.isPresent()){
    					delta.cells.add(cell.get());
    				}else{
//    					Pizza doesn't contain one of step cells. This step is blocked;
    					return null;
    				}
    			}
    			break;
    		case LEFT:
//    			validation on max slice size
    			if( (this.cells.size() + (this.maxY()-this.minY())) > maxSliseSize ) return null;
    			for (int y = this.minY(); y <= this.maxY(); y++) {
    				Optional<Cell> cell = pizza.getCell(y, minX() - 1);
//    				validation on containing step cells in pizza
    				if(cell.isPresent()){
    					delta.cells.add(cell.get());
   				 	}else{
//   				 	Pizza doesn't contain one of step cells. This step is blocked;
   				 		return null;
   				 	}
    			}
    			break;
    		}
//    	LOGGER.info("return " + counter++ + " availadle step") ;
    	return new Step(this, delta);
    }
    
}

