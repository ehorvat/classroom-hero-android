package com.ego.ch.exceptions;

public class StampNotAllowed extends Exception{
	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		//Parameterless Constructor
	   public StampNotAllowed() {}

	   //Constructor that accepts a message
	   public StampNotAllowed(String message)
	   {
	      super(message);
	   }
}
