package com.google.hashcode.utils;

import com.google.hashcode.entity.Cell;
import com.google.hashcode.entity.Ingredient;
import com.google.hashcode.entity.Slice;
import com.google.hashcode.entity.SliceInstruction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;

/**
 * @author Grigoriy Lyashenko (Grog).
 * @author github.com/VadimKlindukhov skype: kv_vadim
 */
public class IoUtils {
    private IoUtils() {
    }

    /**
     * Parses given input file to a 2d pizza cells array
     *
     * @param file input file
     * @return 2d array representing a pizza
     * @throws IOException parsing fail
     */
    
    //this method coud be rewrited and fill mashrooms and tomatos to difference list from start (now it's going in cutAllStartPositions() method) 
    public static List<Cell> parsePizza(String file, List<Slice> startPositions) throws IOException {
        try (FileReader fileReader = new FileReader(file)) {
        	long t1 = System.currentTimeMillis();
        	System.out.println("START parsePizza()");
            BufferedReader br = new BufferedReader(fileReader);
            //skip a line with slice instructions
            br.readLine();
            //declare a pizza cells array
            int row = 0;
            String fileLine;
            List<Cell> tomatoes = new ArrayList<Cell>();
            List<Cell> mushrooms = new ArrayList<Cell>();
            while ((fileLine = br.readLine()) != null) {
                for (int column = 0; column < fileLine.length(); column++) {
                    Character literal = fileLine.charAt(column);
                    if (literal.toString().equals(Ingredient.TOMATO.toString())) {
                    	tomatoes.add(new Cell(row, column, Ingredient.TOMATO));
                    } else {
                    	mushrooms.add(new Cell(row, column, Ingredient.MUSHROOM));
                    }
                }
                row++;
            }
            List<Slice> slice = new ArrayList<Slice>();
            System.out.println(mushrooms.size() +" "+ tomatoes.size());
            long t2 = System.currentTimeMillis() - t1;
            System.out.println("parsePizza() FINISHED in " + t2 + " milliseconds" );
            if(tomatoes.size() > mushrooms.size()){
            	for (Cell cell : mushrooms) {
            		slice.add(new Slice(cell));
    			}
            	startPositions.addAll(slice);
            	return tomatoes;
            }else{
            	for (Cell cell : tomatoes) {
            		slice.add(new Slice(cell));
    			}
            	startPositions.addAll(slice);
            	return mushrooms;
            }
        }
    }

    /**
     * Produces SliceInstructions based on given input data set
     *
     * @param file input data set
     * @return slice instructions
     * @throws IOException file reading error
     */
    public static SliceInstruction parseSliceInstructions(String file) throws IOException {
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fileReader);
            String[] headerTokens = br.readLine().split(" ");
            int minNumberOfIngredientPerSlice = Integer.parseInt(headerTokens[2]);
            int maxNumberOfCellsPerSlice = Integer.parseInt(headerTokens[3]);
            return new SliceInstruction(minNumberOfIngredientPerSlice, maxNumberOfCellsPerSlice);
        }
    }

    /**
     * Formats data from list of slices to the required output format
     *
     * @param list inner representation of pizza
     * @return String that contains output data
     */
    public static String parseSlices(List<Slice> list) {
        Comparator<Cell> cellComparator = (Cell c1, Cell c2) -> {
            if (c1.x != c2.x) {
                return Integer.compare(c1.x, c2.x);
            } else
                return Integer.compare(c1.y, c2.y);
        };
        StringBuilder sb = new StringBuilder();
        Formatter textFormatter = new Formatter(sb);
        textFormatter.format("%d%n", list.size());
        Cell min, max;
        for (Slice slice : list) {
            min = slice.cells.stream().min(cellComparator).get();
            max = slice.cells.stream().max(cellComparator).get();
            textFormatter.format("%d %d %d %d%n", min.y, min.x, max.y, max.x);
        }
        textFormatter.close();
        return sb.toString().trim();
    }

    public static void writeToFile(String fileName, String outputDate) throws IOException {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(outputDate);
        }
    }

    public static String readFromFile(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        StringBuilder stringBuilder = new StringBuilder();
        lines.forEach(
                line -> stringBuilder.append(line).append("\n")
        );
        return stringBuilder.toString();
    }
}

