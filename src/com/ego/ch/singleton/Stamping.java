package com.ego.ch.singleton;

import android.util.Log;


public class Stamping {

	public static boolean canStamp;
	
	// create an object of SingleObject
	private static Stamping instance = new Stamping();

	// make the constructor private so that this class cannot be
	// instantiated
	private Stamping() {
		canStamp=true;
	}

	public static Stamping getInstance() {
		return instance;
	}

	public static void canStamp(boolean good){
		Log.v("Can Stamp:", good+"");
		canStamp = good;
	}
}
