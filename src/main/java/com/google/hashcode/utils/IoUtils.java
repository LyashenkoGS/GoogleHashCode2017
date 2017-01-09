package com.google.hashcode.utils;

import com.google.hashcode.entity.PizzaCell;
import com.google.hashcode.entity.SliceInstruction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class IoUtils {

    /**
     * Parses given input file to a 2d pizza cells array
     *
     * @param file input file
     * @return 2d array representing a pizza
     * @throws IOException parsing fail
     */
    public static PizzaCell[][] parsePizza(String file) throws IOException {
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fileReader);
            //parse the first line
            String[] headerTokens = br.readLine().split(" ");
            int rowsCount = Integer.parseInt(headerTokens[0]);
            int columnsCount = Integer.parseInt(headerTokens[1]);
            //declare a pizza cells array
            PizzaCell[][] pizzaCells = new PizzaCell[rowsCount][columnsCount];
            int row = 0;
            String fileLine;
            while ((fileLine = br.readLine()) != null) {
                for (int i = 0; i < fileLine.length(); i++) {
                    Character literal = fileLine.charAt(i);
                    if (literal.toString().equals(PizzaCell.TOMATO.toString())) {
                        pizzaCells[row][i] = PizzaCell.TOMATO;
                    } else if (literal.toString().equals(PizzaCell.MUSHROOM.toString())) {
                        pizzaCells[row][i] = PizzaCell.MUSHROOM;
                    }
                }
                row++;
            }
            return pizzaCells;
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
     * Converts given pizza cells 2d array to human readable string representation
     *
     * @param pizzaCells given array
     * @return table like String representation
     */
    public static String convertToHumanReadableTable(PizzaCell[][] pizzaCells) {
        StringBuilder output = new StringBuilder();
        for (Enum[] row : pizzaCells) {
            for (Enum cell : row) {
                output.append(cell).append(" ");
            }
            output.append("\n");
        }
        return output.toString();
    }

}
