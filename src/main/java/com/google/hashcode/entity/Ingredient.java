package com.google.hashcode.entity;

/**
 * Represents possible pizza cell types
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public enum Ingredient {
    MUSHROOM("M"), TOMATO("T");
    private final String type;

    Ingredient(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}