package com.google.hashcode;

import com.google.hashcode.entity.Pizza;
import com.google.hashcode.entity.PizzaCell;
import com.google.hashcode.utils.IoUtils;

import java.io.File;
import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {
        String exampleInputFile = "inputDataSets/example.in";
        PizzaCell[][] pizzaCells = IoUtils.parsePizza(exampleInputFile);
        Pizza pizza = new Pizza(new File(exampleInputFile), pizzaCells, IoUtils.parseSliceInstructions(exampleInputFile));

        System.out.println("GoogleHashCode2017! Pizza task");
        System.out.print(pizza);

    }

}
