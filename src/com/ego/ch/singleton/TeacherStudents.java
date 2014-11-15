package com.ego.ch.singleton;

import java.util.ArrayList;

import com.ego.ch.exceptions.StampNotAllowed;
import com.ego.ch.exceptions.StampNotPaired;
import com.ego.ch.exceptions.StampRegistered;
import com.ego.ch.model.StudentUser;


public class TeacherStudents {

	static ArrayList<StudentUser> students;
	

	private static TeacherStudents instance = new TeacherStudents();


	private TeacherStudents() {
		students = new ArrayList<StudentUser>();
	}

	public static TeacherStudents getInstance() {
		return instance;
	}

	
	public static void addStudent(StudentUser student) {
		students.add(student);
	}
	
	public static ArrayList<StudentUser> getStudents() {
		return students;
	}
	
	public static ArrayList<StudentUser> getUnregisteredStudents(){
		
		ArrayList<StudentUser> unregistered = new ArrayList<StudentUser>();
		for(StudentUser student : students){
			if(student.getIsActivated()==0){
				unregistered.add(student);
			}
		}
		
		return unregistered;
		
	}
	
	public static void setRegistered(int uid, String serial){
		for(StudentUser student : students){
			if(uid==student.getUid()){
				student.setIsActivated(1);
				student.setStamp(serial);
			}
		}
	}
	
	public static void setGains(int uid, int numCoins, boolean lvlUp){
		for(StudentUser student : students){
			if(uid==student.getUid()){
				student.setCurrentCoins(student.getCurrentCoins()+numCoins);
				if(lvlUp){
					student.setProgress(0);
					student.setLvl(student.getLvl()+1);
					student.setLvlUpAmount(student.getLvlUpAmount()+3);
				}else{
					student.setProgress(student.getProgress()+1);
				}
			}
		}
	}

	
	public static StudentUser getStudentBySerial(String serial) throws StampNotPaired{
		StudentUser selected = null;
		for(StudentUser student : students){
			if(student.getStamp().equals(serial)){
				selected = student;
				break;
			}
		}
		if(selected == null){
			throw new StampNotPaired("Stamp is not registered to a studnet.");
		}
		return selected;
	}
	
	public static void checkSerial(String serial) throws StampRegistered{
		for(StudentUser student : students){
			if(student.getStamp().equals(serial)){
				throw new StampRegistered("Stamp is already registered to a student.");
			}
		}
	}
	
	public static void addOneToAll(){
		for(StudentUser student : students){
			student.setCurrentCoins(student.getCurrentCoins()+1);	
		}
	}
	
	public static void checkIfStudentStamped(String serial) throws StampNotAllowed{
		for(StudentUser student : students){
			if(student.getStamp().equals(serial)){
				throw new StampNotAllowed("Stamp is already registered to a student.");
			}
		}
	}

	
}
