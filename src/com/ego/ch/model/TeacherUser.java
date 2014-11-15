package com.ego.ch.model;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherUser extends User {
	
	String email;
	
	String serial;
	
	int cid;
	
	int grade;
	
	int isActivated;
	
	int coins;
	
	int classLvl;
	
	int classProgress;
	
	int classNextLvl;
	
	int leagueId;
	
	boolean hasStamps;
	
	boolean success = false;

	//////////////////
	//				//
	// ctor			//
	//				//
	//////////////////
	public TeacherUser(){
		
	}
	
	public TeacherUser(int uid){
		this.uid = uid;
	}
	
	public TeacherUser(int uid, int cid){
		this.uid = uid;
		this.cid = cid;
	}
	
	public TeacherUser(int uid, int cid, String fname, int coins, int classLvl, int classProgress, int nextLvl, String serial, int role){
		super(uid, fname, serial, role);
		this.cid = cid;
		this.coins = coins;
		this.classLvl = classLvl;
		this.classProgress = classProgress;
		this.classNextLvl = nextLvl;
	}
	
	public TeacherUser(String fname, String lname, String pass, String confPass, String email, int userType) throws NoSuchAlgorithmException {
		super(fname, lname, pass, confPass, userType);
		
		
	}
	
	public TeacherUser(int uid, String fname, String lname, String email, int isActivated, String serial, int coins, int leagueId, boolean hasStamps, int cid) throws NoSuchAlgorithmException {
		super(uid, fname, lname);
		
		this.email = email;
		this.isActivated = isActivated;
		this.serial = serial;
		this.coins = coins;
		this.leagueId = leagueId;
		this.hasStamps = hasStamps;
		this.cid = cid;
		
	}	
	
	public TeacherUser(int uid, String fname, String lname, String email,int isActivated, String serial, int coins, int leagueId, boolean hasStamps) {
		super(uid, fname, lname, email, isActivated, serial);

		this.coins = coins;
		this.leagueId = leagueId;
		this.hasStamps = hasStamps;
	}
	
	public TeacherUser(int cid, String serial) {
		super(serial);

		this.cid = cid;
	}
	
	//////////////////////////
	//						//
	// Setters				//
	//						//
	//////////////////////////
	public void setEmail(String email){
		this.email = email;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public void setGrade(int grade){
		this.grade = grade;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public void setIsActivated(int isActivated) {
		this.isActivated = isActivated;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}
	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}
	public void setHasStamps(boolean hasStamps) {
		this.hasStamps = hasStamps;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public void setClassLvl(int classLvl) {
		this.classLvl = classLvl;
	}

	public void setClassProgress(int classProgress) {
		this.classProgress = classProgress;
	}

	public void setClassNextLvl(int classNextLvl) {
		this.classNextLvl = classNextLvl;
	}

	
	//////////////////////////
	//						//
	// Getters				//
	//						//
	//////////////////////////
	public String getEmail(){
		return email;
	}
	public int getCid() {
		return cid;
	}
	public int getGrade(){
		return grade;
	}

	public String getSerial() {
		return serial;
	}
	public int getIsActivated() {
		return isActivated;
	}
	public int getCoins() {
		return coins;
	}
	public int getLeagueId() {
		return leagueId;
	}

	public boolean isHasStamps() {
		return hasStamps;
	}

	public boolean isSuccess() {
		return success;
	}

	public int getClassLvl() {
		return classLvl;
	}

	public int getClassProgress() {
		return classProgress;
	}

	public int getClassNextLvl() {
		return classNextLvl;
	}


	
	

}
