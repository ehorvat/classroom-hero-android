package com.ego.ch.singleton;

import java.util.ArrayList;

import com.ego.ch.model.Item;


public class Items{
	
	static ArrayList<Item> items = null;
	
	public static Items instance = new Items();

	private Items() {
		items = new ArrayList<Item>();
	}

	public static Items getInstance() {
		return instance;
	}


	public static void addItem(Item item) {
		items.add(item);
	}

	
	public ArrayList<Item> getItems() {
		return items;
	}

	
	public static Item getItem(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
