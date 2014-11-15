package com.ego.ch.exceptions;

public class LoginFailed extends Exception{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Parameterless Constructor
   public LoginFailed() {}

   //Constructor that accepts a message
   public LoginFailed(String message)
   {
      super(message);
   }

}
