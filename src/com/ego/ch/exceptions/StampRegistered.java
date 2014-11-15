package com.ego.ch.exceptions;

public class StampRegistered extends Exception {
	 //Parameterless Constructor
    public StampRegistered() {}

    //Constructor that accepts a message
    public StampRegistered(String message)
    {
       super(message);
    }
}
