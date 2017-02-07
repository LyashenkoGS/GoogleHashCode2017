package com.google.hashcode.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.hashcode.entity.Cell;
import com.google.hashcode.entity.Pizza;
import com.google.hashcode.entity.Slice;
import com.google.hashcode.entity.Step;

public class GaasMultitheread {

	static Map<Slice, List<Step>> firstMap = new HashMap<>();
	static Map<Slice, List<Step>> secondMap = new HashMap<>();
	static Map<Slice, List<Step>> thirdMap = new HashMap<>();
	static Map<Slice, List<Step>> forthMap = new HashMap<>();
	
	public static Map<Slice, List<Step>> gAAS( Pizza pizza, List<Slice> startPositions,	List<Slice> output){

		//create and calculate counter.
		int counter = startPositions.size()/4;

		//advertisement lists for split data from startPositions
		List<Slice> firstList = new ArrayList<>();
		List<Slice> secondList = new ArrayList<>();
		List<Slice> thirdList = new ArrayList<>();
		List<Slice> forthList = new ArrayList<>();
		
		//split start positions to difference lists
		//Comment from author: subList return only view for part of list but not new one list,
		//so using it we get ConcurrentModificationException on iterator in getAllAvailableSteps( gass )
		Iterator<Slice> iterator = startPositions.iterator();
		while(iterator.hasNext()){
			Slice slice = iterator.next();
			int index = startPositions.indexOf(slice);
			if(index >= 0 && index < counter){
				firstList.add(slice);
			} else if(index >= counter && index < counter*2){
				secondList.add(slice);
			} else if(index >= counter*2 && index < counter*3){
				thirdList.add(slice);
			} else if( index >= counter && index < startPositions.size()){
				forthList.add(slice);
			}
		}
		
		//creating clone cells of pizza
		List<Cell> l1 = pizza.cloneCells();
		List<Cell> l2 = pizza.cloneCells();
		List<Cell> l3 = pizza.cloneCells();
		List<Cell> l4 = pizza.cloneCells();
		
		//creating 4 Pizza objects with all difference inner objects but with same primitive values 
		Pizza p1 = new Pizza(pizza.getInput(), l1, pizza.getSliceInstruction());
		Pizza p2 = new Pizza(pizza.getInput(), l2, pizza.getSliceInstruction());
		Pizza p3 = new Pizza(pizza.getInput(), l3, pizza.getSliceInstruction());
		Pizza p4 = new Pizza(pizza.getInput(), l4, pizza.getSliceInstruction());
		
		//create four output lists
		List<Slice> o1 = new ArrayList<Slice>();
		List<Slice> o2 = new ArrayList<Slice>();
		List<Slice> o3 = new ArrayList<Slice>();
		List<Slice> o4 = new ArrayList<Slice>();
		
		//create four threads for GAAS
		Thread thread1 = new Thread("first"){
			@Override
			public void run(){
				//each thread has it's own pizza, startPositions output and resulted map
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
		
		//start threads
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		//creating result map
		Map<Slice, List<Step>> groupedSteps = new HashMap<>();
		
		//set values from all maps to resulted map
		groupedSteps.putAll(firstMap);
		groupedSteps.putAll(secondMap);
		groupedSteps.putAll(thirdMap);
		groupedSteps.putAll(forthMap);
		
		//set values from all output list to resulted list
		output.addAll(o1);
		output.addAll(o2);
		output.addAll(o3);
		output.addAll(o4);
		
		return groupedSteps;
	}
}
