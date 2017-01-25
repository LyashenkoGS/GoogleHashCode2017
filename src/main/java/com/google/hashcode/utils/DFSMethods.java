package com.google.hashcode;

public class DFSMethods {
	
	public int amoluntOfValidCellsAroundSlice(List<Cell> slice, List<Cell> pizza){
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
		if(didPizzaContainsAllNeededHorizontalCells(pizza, differenceXCoordinates, minY - 1)) cellsAroundSlice += differenceXCoordinates.size();
		
		//checking left cells.
		if(didPizzaContainsAllNeededVerticalCells(pizza, differenceYCoordinates, maxX + 1)) cellsAroundSlice += differenceYCoordinates.size();
		
		//checking bottom cells.
		if(didPizzaContainsAllNeededHorizontalCells(pizza, differenceXCoordinates, maxY + 1)) cellsAroundSlice += differenceXCoordinates.size();
		
		//checking right cells.
		if(didPizzaContainsAllNeededVerticalCells(pizza, differenceYCoordinates, minX - 1)) cellsAroundSlice += differenceYCoordinates.size();
		
		return cellsAroundSlice;
	}
	
    public boolean stepUp(List<Cell> slice, List<Cell> pizza, int maxCellsInSlice){
    	
    	// finding min y coordinate in slice.
    	Integer minYCoordInSlice = findMinY(slice);
    	// find all difference x coordinates.
    	List<Integer> differenceXCoordinates = fillAllDifferenceX(slice);
    	
    	//prevent this method from cutting too big slice.
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
    	
    	Integer maxXCoordInSlice = findMaxX(slice);
    	List<Integer> differenceYCoordinates = fillAllDifferenceY(slice);;
    	
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
    	
    	Integer maxYCoordInSlice = findMaxY(slice);
    	List<Integer> differenceXCoordinates = fillAllDifferenceX(slice);
    	
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
    	
    	Integer minXCoordInSlice = findMinX(slice);
    	List<Integer> differenceYCoordinates = fillAllDifferenceY(slice);
    	
    	if(slice.size() + differenceYCoordinates.size() > maxCellsInSlice) return false;
    	
    	boolean pizzaContainsAllNeededCells = didPizzaContainsAllNeededVerticalCells(pizza, differenceYCoordinates, minXCoordInSlice -1);
    	
    	if(pizzaContainsAllNeededCells){
    		for(int yCoord: differenceYCoordinates){
    			slice.add(pizza.remove(pizza.indexOf(new Cell(minXCoordInSlice, yCoord))))
    		}
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    
    private List<Integer> fillAllDifferenceX(List<Cell> slice){
    	List<Integer> diffX = new ArrayList<Integer>();
    	for(Cell cell: slice){
    		if(!diffX.contains(cell.x)){
    			diffX.add(cell.x);
    		}
    	}
    	return diffX;
    }
    
    private List<integer> fillAllDifferenceY(List<Cell> slice){
    	List<Integer> diffY = new ArrayList<Integer>();
    	for(Cell cell: slice){
    		if(!diffY.contains(cell.y)){
    			diffY.add(cell.y);
    		}
    	}
    	return diffY;
    }
    
    private int findMinY(List<Cell> slice){
    	Integer minYCoord = null;
    	for(Cell cell: slice){
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
    
    private int findMaxY(List<Cell> slice){
    	Integer maxYCoord = null;
    	for(Cell cell: slice){
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
    
    private int findMinX(List<Cell> slice){
    	Integer minXCoord = null;
    	for(Cell cell: slice){
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
    
    private int findMaxX(List<Cell> slice){
    	Integer maxXCoord = null;
    	for(Cell cell: slice){
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
    
    private boolean didPizzaContainsAllNeededVerticalCells(List<Cell> pizza, List<Integer> differenceYCoordinate, Integer xCoord){
    	boolean returnValue = true;
    	for(Integer yCoord: differenceYCoordinate){
    		if(!pizza.contains(new Cell(xCoord, yCoord))){
    			returnValue = false;
    		}
    	}
    	return returnValue;
    }
    
    private List<Integer> fillArrayWithIntValues(int start, int end){
    	List<Integer> returnedList = new ArrayList<Integer>();
    	for(int i = start; i < end + 1; i++){
    		returnedList.add((Integer)i);
    	}
    	return returnedList;
    }
}
