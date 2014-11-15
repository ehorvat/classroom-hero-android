package com.ego.ch.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ego.ch.daoimpl.LoginImpl;
import com.ego.ch.exceptions.LoginFailed;
import com.ego.ch.interfaces.OnLogin;
import com.ego.ch.singleton.Stamping;
import com.ego.ch.tasks.AsyncTaskRunner;
import com.ego.ch.util.Constants;
import com.ego.classhero.R;



public class LoginActivity extends Activity implements OnClickListener{
	
	//Credential text fields
	EditText etUsername, etPassword;
	
	//Login Button
	ImageView ivLogin;
	
	//This activity's feedback view
	TextView feedback;
	
	//Holds the values for the credentials
	String username, password;
	

	
	OnLogin listener = new OnLogin(){

		@Override
		public void onAuth(String data) {
			//Process regular login.
			try {
				new LoginImpl(data, LoginActivity.this);
			} catch (JSONException e) {		
				e.printStackTrace();
			} catch (LoginFailed e) {
				e.printStackTrace();
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
					}
				});

			}
			
		}

		@Override
		public void onAuthStamp(String data) {
			//Process login with stamp
			
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		Stamping.canStamp(true);
		
		//Initialize this activity's views
		initViews();
		
		
	}

	private void initViews() {
		
		//View initialization
		
		feedback = (TextView) findViewById(R.id.tvFeedback);
		
		etUsername = (EditText) findViewById(R.id.etUser);
		etPassword = (EditText) findViewById(R.id.etPass);
		
		ivLogin = (ImageView) findViewById(R.id.ivLoginButton);
		ivLogin.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
	
		switch(v.getId()){
		
		
		case R.id.ivLoginButton:
			//Login button pressed
			
			Stamping.canStamp(false);
			
			//Hide the currently displayed feedback message
			feedback.setVisibility(View.GONE);
			
			username = etUsername.getText().toString();
			password = etPassword.getText().toString();
			
			//First check if edit texts contain anything
			if((username.length() != 0 && username != "")|| (password.length() != 0 && password.toString() != "")){
				
				//JSONObject for packing credentials to send to server
				JSONObject values;
				
				//Add edit text values to JSON object
				values = new JSONObject();
				try {
					values.put("username", username);
					values.put("password", password);
					
					JSONObject credentials = new JSONObject();
					credentials.put("credentials", values);
					
					Log.v("credentials", credentials.toString());
					
					//Async login task
					AsyncTaskRunner runner = new AsyncTaskRunner(AsyncTaskRunner.POST_TASK, getApplicationContext(), LoginActivity.this, Constants.ON_LOGIN, listener);
					runner.dataAsJsonString(credentials.toString());
					runner.execute(new String[] { Constants.AUTH_URL });

				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}else{
				//Show feed back
				feedback.setText("Please input a username and password.");
				feedback.setVisibility(View.VISIBLE);
			}
			
			
			break;
		}
		
	}

}
