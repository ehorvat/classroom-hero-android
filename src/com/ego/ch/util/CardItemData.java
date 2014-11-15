package com.ego.ch.util;

/**
 * Created by Justin on 2/2/14.
 */
public class CardItemData
{
	private String name;
	private int lvl;
	private int currentCoins;

	public CardItemData(String name, int lvl, int currentCoins){
		this.name = name;
		this.lvl = lvl;
		this.currentCoins = currentCoins;
	}
	
	public String getName()
	{
		return name;
	}

	public int getLvl()
	{
		return lvl;
	}

	public int currentCoins()
	{
		return currentCoins;
	}
}
