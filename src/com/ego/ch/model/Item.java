package com.ego.ch.model;

import java.io.Serializable;

public class Item implements Serializable {

	String item_name = new String();
	String item_description = new String();
	int item_cost;
	int iid;
	
	public Item(){
		
	}
	
	public Item(String item_name, int item_cost) {
		this.item_name = item_name;
		this.item_cost = item_cost;
	}
	
	public Item(int iid, int item_cost, String item_name) {
		this.iid = iid;
		this.item_name = item_name;
		this.item_cost = item_cost;
	}

	public int getIid() {
		return iid;
	}

	public String getItemName() {
		return item_name;
	}

	public String getItemDescription() {
		return item_description;
	}

	public int getItemCost() {
		return item_cost;
	}

	public void setItemName(String item_name) {
		this.item_name = item_name;
	}

	public void setItemDescription(String item_description) {
		this.item_description = item_description;
	}

	public void setItemCost(int item_cost) {
		this.item_cost = item_cost;
	}

	public void setIid(int iid) {
		this.iid = iid;
	}

}
