package com.google.hashcode;

public class DFSMethods {
	
    public boolean stepUp(List<Cell> slice, List<Cell> pizza, int maxCellsInSlice){
    	// empty list difference X coordinates in slice for using it further. 
    	List<Integer> differenceXCoordinates = new ArrayList<Integer>();
    	// finding min y coordinate in slice and fill values to differenceXCoordinates.
    	Integer minYCoordInSlice = findMinYAndFillAllDifferenceX(slice, differenceXCoordinates);
    	
    	//prevent this method for cutting too big slice.
    	if(slice.size() + differenceXCoordinates.size() > maxCellsInSlice) return false;
    	
    	//our pizza still have all needed cells for this step? lets check it.
    	boolean pizzaContainsAllNeededCells = didPizzaContainsAllNeededHorizontalCells(pizza,differenceXCoordinates, minYCoordInSlice -1);
    	
    	if(pizzaContainsAllNeededCells){
    		//Cut all needed cells from pizza and add their to our slice.
    		for(int xCoord: differenceXCoordinates){
    			//use .remove(index) to get object from pizza (with it's Ingradient)
    			//but for getting those index use .indexOf(Object o)
    			slice.add(pizza.remove(pizza.indexOf(new Cell(xCoord, minYCoordInSlice))))
    		}
    		return true;
    	}else{
    		//oups, pizza doesn't contain one of needed cells, step blocked.
    		return false;
    	}
    }
    
    public boolean stepRight(List<Cell> slice, List<Cell> pizza, int maxCellsInSlice){
    	
    	List<Integer> differenceYCoordinates = new ArrayList<Integer>();
    	Integer maxXCoordInSlice = findMaxXAndFillAllDifferenceY(slice, differenceYCoordinates);
    	
    	if(slice.size() + differenceYCoordinates.size() > maxCellsInSlice) return false;
    	
    	boolean pizzaContainsAllNeededCells = didPizzaContainsAllNeededVerticalCells(pizza, differenceYCoordinates, maxXCoordInSlice + 1);
    	
    	if(pizzaContainsAllNeededCells){
    		for(int yCoord: differenceYCoordinates){
    			slice.add(pizza.remove(pizza.indexOf(new Cell(minXCoordInSlice, yCoord))))
    		}
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    
    public boolean stepDown(List<Cell> slice, List<Cell> pizza, int maxCellsInSlice){
    	
    	List<Integer> differenceXCoordinates = new ArrayList<Integer>();
    	Integer maxYCoordInSlice = findMaxYAndFillAllDifferenceX(slice, differenceXCoordinates);
    	
    	if(slice.size() + differenceXCoordinates.size() > maxCellsInSlice) return false;
    	
    	boolean pizzaContainsAllNeededCells = didPizzaContainsAllNeededVerticalCells(pizza,differenceXCoordinates, maxYCoordInSlice + 1);
    	
    	if(pizzaContainsAllNeededCells){
    		for(int xCoord: differenceXCoordinates){
    			slice.add(pizza.remove(pizza.indexOf(new Cell(xCoord, maxYCoordInSlice))))
    		}
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    
    public boolean stepLeft(List<Cell> slice, List<Cell> pizza, int maxCellsInSlice){
    	
    	List<Integer> differenceYCoordinates = new ArrayList<Integer>();
    	Integer minXCoordInSlice = findMinXAndFillAllDifferenceY(slice, differenceYCoordinates);
    	
    	if(slice.size() + differenceYCoordinates.size() > maxCellsInSlice) return false;
    	
    	boolean pizzaContainsAllNeededCells = didPizzaContainsAllNeededVerticalCells(pizza, differenceYCoordinates, minXCoordInSlice -1 1);
    	
    	if(pizzaContainsAllNeededCells){
    		for(int yCoord: differenceYCoordinates){
    			slice.add(pizza.remove(pizza.indexOf(new Cell(minXCoordInSlice, yCoord))))
    		}
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    
    private int findMinYAndFillAllDifferenceX(List<Cell> slice, List<Integer> diffX){
    	Integer minYCoord = null;
    	for(Cell cell: slice){
    		if(!diffX.contains(cell.x)){
    			diffX.add(cell.x);
    		}
    		if(minYCoord != null){
    			if(cell.y < minYCoord){
    				minYCoord = cell.y; 
    			}
    		}else{
    			minYCoord = cell.y;
    		}
    	}
    	return minYCoord;
    }
    
    private int findMaxYAndFillAllDifferenceX(List<Cell> slice, List<Integer> diffX){
    	Integer maxYCoord = null;
    	for(Cell cell: slice){
    		if(!diffX.contains(cell.x)){
    			diffX.add(cell.x);
    		}
    		if(maxYCoord != null){
    			if(cell.y > minYCoord){
    				maxYCoord = cell.y; 
    			}
    		}else{
    			maxYCoord = cell.y;
    		}
    	}
    	return maxYCoord;
    }
    
    private int findMinXAndFillAllDifferenceY(List<Cell> slice, List<Integer> diffY){
    	Integer minXCoord = null;
    	for(Cell cell: slice){
    		if(!diffY.contains(cell.y)){
    			diffY.add(cell.y);
    		}
    		if(minXCoord != null){
    			if(cell.x < minXCoord){
    				minXCoord = cell.x; 
    			}
    		}else{
    			minXCoord = cell.x;
    		}
    	}
    	return minXCoord;
    }
    
    private int findMaxXAndFillAllDifferenceY(List<Cell> slice, List<Integer> diffY){
    	Integer maxXCoord = null;
    	for(Cell cell: slice){
    		if(!diffY.contains(cell.y)){
    			diffY.add(cell.y);
    		}
    		if(maxXCoord != null){
    			if(cell.x > minXCoord){
    				maxXCoord = cell.x; 
    			}
    		}else{
    			maxXCoord = cell.x;
    		}
    	}
    	return maxXCoord;
    }
    
    private boolean didPizzaContainsAllNeededHorizontalCells(List<Cell> pizza, List<Integer> differenceXCoordinate, Integer yCoord){
    	boolean returnValue = true;
    	for(Integer xCoord: differenceXCoordinate){
    		if(!pizza.contains(new Cell(xCoord, yCoord))){
    			returnValue = false;
    		}
    	}
    	return returnValue;
    }
    
    private boolean didPizzaContainsAllNeededVerticalCells(List<Cell> pizza, List<Integer> differenceYCoordinate, Integer minXCoordinate){
    	boolean returnValue = true;
    	for(Integer yCoord: differenceYCoordinate){
    		if(!pizza.contains(new Cell(minXCoordinate, yCoord))){
    			returnValue = false;
    		}
    	}
    	return returnValue;
    }
}
