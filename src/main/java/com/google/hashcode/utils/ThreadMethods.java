package com.google.hashcode.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.hashcode.entity.Pizza;
import com.google.hashcode.entity.Slice;
import com.google.hashcode.entity.Step;

public class ThreadMethods {

	static Map<Slice, List<Step>> firstMap = new HashMap<>();
	static Map<Slice, List<Step>> secondMap = new HashMap<>();
	static Map<Slice, List<Step>> thirdMap = new HashMap<>();
	static Map<Slice, List<Step>> forthMap = new HashMap<>();
	
	public static Map<Slice, List<Step>> threadAvailableSteps(Pizza pizza, List<Slice> startPositions,
			List<Slice> output){
		int counter = startPositions.size()/4;
		List<Slice> firstList = startPositions.subList(0, counter);
		List<Slice> secondList = startPositions.subList(counter, counter*2);
		List<Slice> thirdList = startPositions.subList(counter*2, counter*3);
		List<Slice> forthList = startPositions.subList(counter*3, startPositions.size());
		
		Pizza p1 = new Pizza(pizza.getInput(), pizza.getCells(), pizza.getSliceInstruction());
		Pizza p2 = new Pizza(pizza.getInput(), pizza.getCells(), pizza.getSliceInstruction());
		Pizza p3 = new Pizza(pizza.getInput(), pizza.getCells(), pizza.getSliceInstruction());
		Pizza p4 = new Pizza(pizza.getInput(), pizza.getCells(), pizza.getSliceInstruction());
		
		List<Slice> o1 = new ArrayList<Slice>();
		List<Slice> o2 = new ArrayList<Slice>();
		List<Slice> o3 = new ArrayList<Slice>();
		List<Slice> o4 = new ArrayList<Slice>();
		//ConcurrentModificationException
		Thread thread1 = new Thread("first"){
			@Override
			public void run(){
				firstMap = DFSMethods.getAvailableSteps(p1, firstList, o1);
			}
		};
		Thread thread2 = new Thread("second"){
			@Override
			public void run(){
				secondMap = DFSMethods.getAvailableSteps(p2, secondList, o2);
			}
		};
		Thread thread3 = new Thread("third"){
			@Override
			public void run(){
				thirdMap = DFSMethods.getAvailableSteps(p3, thirdList, o3);
			}
		};
		Thread thread4 = new Thread("forth"){
			@Override
			public void run(){
				forthMap = DFSMethods.getAvailableSteps(p4, forthList, o4);
			}
		};
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		Map<Slice, List<Step>> groupedSteps = new HashMap<>();
		groupedSteps.putAll(firstMap);
		groupedSteps.putAll(secondMap);
		groupedSteps.putAll(thirdMap);
		groupedSteps.putAll(forthMap);
		output.addAll(o1);
		output.addAll(o2);
		output.addAll(o3);
		output.addAll(o4);
		return groupedSteps;
	}
	
}
