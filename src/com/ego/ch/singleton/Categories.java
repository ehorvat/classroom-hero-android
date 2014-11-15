package com.ego.ch.singleton;

import java.util.ArrayList;

import com.ego.ch.model.Category;


public class Categories{
	
	static ArrayList<Category> categories;
	

	private static Categories instance = new Categories();


	private Categories() {
		categories = new ArrayList<Category>();
	}

	public static Categories getInstance() {
		return instance;
	}

	
	public static void addCategory(Category category) {
		// TODO Auto-generated method stub
		categories.add(category);
	}


	public Category getCategory(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static ArrayList<Category> getCategories() {
		// TODO Auto-generated method stub
		return categories;
	}

}
