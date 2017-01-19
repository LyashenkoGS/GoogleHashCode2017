package com.google.hashcode.utils;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.hashcode.entity.Cell;
import com.google.hashcode.entity.Ingredient;

/**
 * 
 * @author Tuzhanskyi (jarc0der)
 *
 */

public class Formatter {
	private List<List<Cell>> cellList;
	private final Comparator<Cell> comp = ((thisCell, thatCell) -> {
		if(thisCell.x != thatCell.x){
			return Integer.compare(thisCell.x, thatCell.x);
		}else{
			return Integer.compare(thisCell.y, thatCell.y);
		}
	});

	public Formatter(List<List<Cell>> list) {
		this.cellList = list;
	}

	public Formatter() {
		this(hardInit());
		// this.cellList = hardInit();
	}

	public void writeResultsToFile(String fileName) throws IOException {

		try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
			out.write("" + cellList.size());
			out.newLine();
			for (List<Cell> slice : cellList) {
				Cell maxCell = slice.stream().max(comp).get();
				Cell minCell = slice.stream().min(comp).get();
				
				String coords = getCoordsLineForSlice(minCell, maxCell);
				out.write(coords);
				out.newLine();
			}
			
			//check: autoflush?
			out.flush();
		}

	}

	private String getCoordsLineForSlice(Cell min, Cell max) {
		StringBuilder sb = new StringBuilder();
		sb.append(min.y).append(" " + min.x).append(" " + max.y).append(" " + max.x);
		return sb.toString();
	}

	private static List<List<Cell>> hardInit() {
		List<List<Cell>> resultList = new ArrayList<>();
		List<Cell> slice0 = new ArrayList<>();
		List<Cell> slice1 = new ArrayList<>();
		List<Cell> slice2 = new ArrayList<>();

		slice0.add(new Cell(0, 0, Ingredient.TOMATO));
		slice0.add(new Cell(0, 1, Ingredient.TOMATO));
		slice0.add(new Cell(0, 2, Ingredient.TOMATO));
		slice0.add(new Cell(1, 0, Ingredient.TOMATO));
		slice0.add(new Cell(1, 1, Ingredient.MUSHROOM));
		slice0.add(new Cell(1, 2, Ingredient.TOMATO));

		slice1.add(new Cell(2, 0, Ingredient.TOMATO));
		slice1.add(new Cell(2, 1, Ingredient.MUSHROOM));
		slice1.add(new Cell(2, 2, Ingredient.TOMATO));

		slice2.add(new Cell(3, 0, Ingredient.TOMATO));
		slice2.add(new Cell(3, 1, Ingredient.MUSHROOM));
		slice2.add(new Cell(3, 2, Ingredient.TOMATO));
		slice2.add(new Cell(4, 0, Ingredient.TOMATO));
		slice2.add(new Cell(4, 1, Ingredient.TOMATO));
		slice2.add(new Cell(4, 2, Ingredient.TOMATO));

		resultList.add(slice0);
		resultList.add(slice1);
		resultList.add(slice2);

		return resultList;
	}
}
