package com.ego.ch.model;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;



import android.os.Parcel;
import android.os.Parcelable;

public class StudentUser extends User {

	int totalCoins;
	int currentCoins;
	int totalStars;
	int lvl;
	int progress;
	int lvlUpAmount;
	int isActivated;
	int spent;
	int earned;
	int gainedXp;
	int gainedLvls;
	int isUpdated;

	int cid;



	public int getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(int isUpdated) {
		this.isUpdated = isUpdated;
	}

	String stamp;
	String tstamp;

	

	// ////////////////
	// //
	// ctor //
	// //
	// ////////////////
	public StudentUser() {

	}

	public StudentUser(String fname, String lname, String pass,
			String confPass, int userType) throws NoSuchAlgorithmException {
		super(fname, lname, pass, confPass, userType);

	}

	public StudentUser(int uid, String fname, int currentCoins, int lvl,
			int progress, int lvlUpAmount, int isActivated) {
		super(uid, fname);
		this.currentCoins = currentCoins;
		this.lvl = lvl;
		this.progress = progress;
		this.lvlUpAmount = lvlUpAmount;
		this.isActivated = isActivated;
	}

	public StudentUser(Parcel in) {
		// TODO Auto-generated constructor stub
	}

	public StudentUser(int uid, String name, int currentCoins, int totalCoins, int lvl, int progress, int lvlUpAmount, String stamp, int isActivated, int earned, int spent, int gxp, int glvl, int cid, int isUpdated) {
		super(uid, name);
		this.currentCoins=currentCoins;
		this.totalCoins=totalCoins;
		this.lvl=lvl;
		this.progress=progress;
		this.lvlUpAmount=lvlUpAmount;
		this.stamp=stamp;
		this.isActivated=isActivated;
		this.earned=earned;
		this.spent=spent;
		this.gainedXp=gxp;
		this.gainedLvls=glvl;
		this.cid=cid;
		this.isUpdated=isUpdated;
	}

	// ////////////////////////
	// //
	// Getters //
	// //
	// ////////////////////////
	public int getTotalCoins() {
		return totalCoins;
	}

	public int getCurrentCoins() {
		return currentCoins;
	}

	public int getTotalStars() {
		return totalStars;
	}

	public int getLvl() {
		return lvl;
	}

	public int getProgress() {
		return progress;
	}

	public int getLvlUpAmount() {
		return lvlUpAmount;
	}

	public int getIsActivated() {
		return isActivated;
	}

	public int getPreviousScore() {
		return currentCoins - 1;
	}

	public String getStamp() {
		return stamp;
	}

	public String getLastUpdate() {
		return tstamp;
	}

	public int getSpent() {
		return spent;
	}

	public int getEarned() {
		return earned;
	}

	public int getCid() {
		return cid;
	}

	public int getGainedXp() {
		return gainedXp;
	}

	public int getGainedLvls() {
		return gainedLvls;
	}

	// ////////////////////////
	// //
	// Setters //
	// //
	// ////////////////////////
	public void setTotalCoins(int totalCoins) {
		this.totalCoins = totalCoins;
	}

	public void setCurrentCoins(int currentCoins) {
		this.currentCoins = currentCoins;
	}

	public void setTotalStars(int totalStars) {
		this.totalStars = totalStars;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public void setLvlUpAmount(int lvlUpAmount) {
		this.lvlUpAmount = lvlUpAmount;
	}

	public void setIsActivated(int activate) {
		isActivated = activate;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;

	}

	public void setLastUpdate(String tstamp) {
		this.tstamp = tstamp;
	}

	public void setSpent(int spent) {
		this.spent = spent;
	}

	public void setEarned(int earned) {
		this.earned = earned;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public void setGainedXp(int gainedXp) {
		this.gainedXp = gainedXp;
	}

	public void setGainedLvls(int gainedLvls) {
		this.gainedLvls = gainedLvls;
	}

	public int incrementXP() {
		this.gainedXp++;
		setGainedXp(gainedXp);
		return gainedXp;
	}

	public int incrementLvl() {
		this.gainedLvls++;
		setGainedLvls(gainedLvls);
		return gainedLvls;
	}

	public int incrementEarned() {
		this.earned++;
		setEarned(earned);
		return earned;
	}

	public int incrementSpent(int cost) {
		this.spent = this.spent + cost;
		setSpent(spent);
		return spent;
	}

	public int lvlUp(int previousLvl) {
		this.lvl = previousLvl + 1;
		this.progress = 0;
		this.lvlUpAmount = lvlUpAmount + 2;
		return lvl;
	}


}
