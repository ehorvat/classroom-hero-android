package com.ego.ch.util;

import com.ego.classhero.R;


public class Constants {
	
	//Image IDs
    public final static Integer[] imageResIds = new Integer[] { R.drawable.teacher_coin,
    															R.drawable.ch_coin_flare_blue,
    															R.drawable.coin_sack,
    															R.drawable.ch_logo3,
    															R.drawable.check,
    															R.drawable.ch_coin,
    															R.drawable.treasure_chest,
    															R.drawable.jar,
    															R.drawable.back_layer,
    															R.drawable.coins};

	
	//Server URLS
	public final static String AUTH_URL = "http://192.168.1.111:8009/LocalAndroidServices/services/login/auth";
	
	public final static String AUTH_URL_STAMP = "http://192.168.1.111:8009/LocalAndroidServices/services/login/authStamp";
	
	public final static String REGISTER_URL = "http://192.168.1.111:8009/LocalAndroidServices/services/register/user";
	
	//Async task methods
	public final static String ON_LOGIN = "onLogin";
	
	public final static String ON_STAMP_LOGIN = "onStampLogin";
	
	public final static String ON_REGISTER = "onRegister";
}
