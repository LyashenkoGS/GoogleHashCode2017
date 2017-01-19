package com.google.hashcode.utils;

import com.google.hashcode.entity.Cell;
import com.google.hashcode.entity.Ingredient;
import org.junit.*;
import static org.junit.Assert.*;


public class FormatterTest {
	private Formatter outFormatter = new Formatter();
	
	@Test
	public void testOutputForOneSlice(){
		String out = outFormatter.getCoordsLineForSlice(new Cell(0, 0, Ingredient.MUSHROOM), new Cell(1,1, Ingredient.MUSHROOM));
		assertEquals("0 0 1 1", out);
	}
	
}
