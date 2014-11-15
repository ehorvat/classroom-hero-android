package com.ego.ch.dao;

import org.json.JSONException;

import com.ego.ch.exceptions.LoginFailed;

public interface LoginDao {
	public void auth() throws LoginFailed, JSONException;
	public void userType() throws JSONException;
	public void teacherLogin() throws JSONException;
	public void checkUserActivation() throws JSONException;
}
