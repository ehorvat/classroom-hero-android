package com.ego.ch.model;

import java.io.Serializable;

public class Category implements Serializable {

	int id;
	String name;

	public Category(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public Category() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
