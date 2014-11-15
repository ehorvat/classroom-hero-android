package com.ego.ch.model;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String fname;
	String lname;
	String fullName;
	String pass;
	String confPass;
	int userType;
	String salt;
	String hashedPassword = null;
	int uid;
	boolean isLoggedIn = false;
	String email;
	String stamp;
	int isActivated;

	//////////////////
	//				//
	// ctor			//
	//				//
	//////////////////
	public User(){
		
	}
	
	public User(int uid){
		this.uid = uid;
	}
	
	public User(String serial){
		this.stamp = serial;
	}
	
	public User(int uid, String fname){
		this.uid = uid;
		this.fname = fname;
	}
	
	public User(int uid, String fname, String serial, int role){
		this.uid = uid;
		this.fname = fname;
		this.stamp = serial;
		this.userType = role;
	}
	
	public User(String fname, String lname, String pass, String confPass, int userType){
		
		//initialize class members
		this.fname = fname;
		this.lname = lname;
		this.pass = pass;
		this.confPass = confPass;
		this.userType = userType;
	}
	
	public User(int uid, String fname, String lname, String email, int isActivated, String stamp){
		this.uid = uid;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.isActivated = isActivated;
		this.stamp = stamp;
	}


	public User(int uid, String fname, String lname) {
		
		//initialize class members
		this.uid = uid;
		this.fname = fname;
		this.lname = lname;
		
	}
	
	public User(int uid, String fname, String lname, String email) {
		
		//initialize class members
		this.uid = uid;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
	}

	public User(String fname, String lname) {
		this.fname = fname;
		this.lname = lname;
	}

	//////////////////////////
	//						//
	// Getters				//
	//						//
	//////////////////////////
	public String getFullName(){
		return fname.trim() + " " + lname.trim();
	}
	
	public String getFname() {
		return fname;
	}
	public String getLname() {
		return lname;
	}
	public int getUserType() {
		return userType;
	}
	public int getUid(){
		return uid;
	}
	public String getHashedPassword() {
		return hashedPassword;
	}
	public String getConfPass() {
		return confPass;
	}
	public String getPass() {
		return pass;
	}
	public String getSalt() {
		return salt;
	}
	public boolean getIsLoggedIn() {
		return isLoggedIn;
	}
	public String getEmail() {
		return email;
	}

	public String getStamp() {
		return stamp;
	}

	public int getIsActivated() {
		return isActivated;
	}
	//////////////////////////
	//						//
	// Setters				//
	//						//
	//////////////////////////
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public void setFname(String fname) {
		
		this.fname = fname;
	}
	public void setLname(String lname) {
	
		this.lname = lname;
	}
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	public void setConfPass(String confPass) {
		this.confPass = confPass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public void setSalt(String salt){
		this.salt = salt;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public void setIsLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	public void setIsActivated(int isActivated) {
		this.isActivated = isActivated;
	}
}

