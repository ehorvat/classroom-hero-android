package com.ego.ch.singleton;

import com.ego.ch.exceptions.StampRegistered;
import com.ego.ch.model.TeacherUser;


public class Teacher{

	static TeacherUser t = null;
	
	// create an object of SingleObject
	private static Teacher instance = new Teacher();

	// make the constructor private so that this class cannot be
	// instantiated
	private Teacher() {
	}

	public static Teacher getInstance() {
		return instance;
	}


	public static void addTeacher(TeacherUser teacher) {
		t = teacher;
	}


	public static TeacherUser getTeacher() {
		return t;
	}

	
	public String getTeacherStamp() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void registerTeacher(String serial){
		t.setStamp(serial);
		t.setIsActivated(1);
	}
	
	public static void compareStamp(String serial) throws StampRegistered{
		if(serial.equals(t.getStamp())){
			throw new StampRegistered("Can't register teacher stamp to a student.");
		}
	}
	
	public static boolean isTeacherStamp(String serial){
		
		boolean isTeacherStamp = false;
		
		if(serial.equals(t.getStamp())){
			isTeacherStamp = true;
		}
		
		return isTeacherStamp;
	}

}
