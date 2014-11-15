package com.ego.ch.singleton;

import com.ego.ch.model.ClassJar;

public class Jar {
	
	public static ClassJar j = null;
	
	public static boolean isFilled;

	public static int toAdd;
	
	public static int jarProgress;
	
	public static int jarTotal;
	
	public static String jarName;
	
	// create an object of SingleObject
	private static Jar instance = new Jar();

	// make the constructor private so that this class cannot be
	// instantiated
	private Jar() {
	}

	public static Jar getInstance() {
		return instance;
	}

	
	public static void addJar(ClassJar jar) {
		j = jar;
	}

	public static ClassJar getJar() {
		return j;
	}
	
	public static void isFilled(boolean filled){
		isFilled = filled;
	}

	public static void toAdd(int add){
		toAdd = add;
	}
	
	public static void jarVals(int progress, int total, String name){
		jarProgress = progress;
		jarTotal = total;
		jarName = name;
	}

	public static void resetJar() {
		jarProgress = 0;
	}
	
	public static void addToJar(){
		jarProgress += toAdd;
	}
	
	public static void addOneToJar(){
		jarProgress += 1;
	}

}
