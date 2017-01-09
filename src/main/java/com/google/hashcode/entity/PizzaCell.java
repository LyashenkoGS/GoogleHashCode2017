package com.google.hashcode.entity;

/**
 * Represents possible pizza cell types
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public enum PizzaCell {
    MUSHROOM("M"), TOMATO("T");
    private final String type;

    PizzaCell(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}