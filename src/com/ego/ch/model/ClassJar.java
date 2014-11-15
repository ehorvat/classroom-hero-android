package com.ego.ch.model;

import java.io.Serializable;

public class ClassJar implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String jarName;
	int jarTotal;
	int jarProgress;
	int jid;
	
	String tstamp;
	//ctor
	public ClassJar(){
		
	}
	
	public ClassJar(int jid, String jarName, int jarProgress, int jarTotal){
		this.jarName = jarName;
		this.jarTotal = jarTotal;
		this.jarProgress = jarProgress;
		this.jid = jid;
	}
	
	//Getters
	public String getTstamp() {
		return tstamp;
	}

	public int getJid() {
		return jid;
	}


	public String getJarName() {
		return jarName;
	}

	public int getJarTotal() {
		return jarTotal;
	}

	public int getJarProgress() {
		return jarProgress;
	}
	

	//Setters
	public void setTstamp(String tstamp) {
		this.tstamp = tstamp;
	}

	public void setJid(int jid) {
		this.jid = jid;
	}
	
	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

	public void setJarTotal(int jarCost) {
		this.jarTotal = jarCost;
	}

	public void setJarProgress(int jarProgress) {
		this.jarProgress = jarProgress;
	}
	
	

}
