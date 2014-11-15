package com.ego.ch.tutorial;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ego.ch.activities.LoginActivity;
import com.ego.ch.activities.StampActivity;
import com.ego.ch.interfaces.OnRegister;
import com.ego.ch.model.TeacherUser;
import com.ego.ch.model.User;
import com.ego.ch.singleton.Stamping;
import com.ego.ch.singleton.Teacher;
import com.ego.ch.tasks.AsyncTaskRunner;
import com.ego.ch.util.Constants;
import com.ego.classhero.R;


public class TutorialRegisterFragment extends Fragment{
private static final String KEY_CONTENT = "TutorialRegister";
	
	private String mContent = "???";

	TextView tvRegisterInstructions;
	
	RelativeLayout rLayout;
	
	Typeface amatic;
	
	FragmentActivity listener;
	
	//UserDetails details;
	
	TeacherUser teacher;
    
    private OnRegister regListener = new OnRegister() {

		@Override
		public void onRegister(String result) {
			
			Log.v("Registering", "Register result: " + result);
			// If success direct to homepage
			JSONObject parent;
			
			try {
				
				parent = new JSONObject(result);
				
				String success = parent.getString("success");
				String serial = parent.getString("serial");
				
				Intent i = null;
				
				if (Boolean.parseBoolean(success) == true) {
					
					
					Teacher.registerTeacher(serial);
					
					if(teacher.getUserType()==1){
						//Redirect to teacher views
						Stamping.canStamp(true);
						
						i = new Intent(listener, StampActivity.class);
						startActivity(i);
						
					}else if(teacher.getUserType()==3){
						//Redirect to admin views
					}
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		
		

		}

	};

	public TutorialRegisterFragment() {

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.listener = (FragmentActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	private void initFragmentViews() {
		
		amatic = Typeface.createFromAsset(getActivity().getAssets(),"fonts/AmaticSC-Regular.ttf");
		
		tvRegisterInstructions = (TextView) rLayout.findViewById(R.id.tvRegisterInstructions);
		tvRegisterInstructions.setTypeface(amatic);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);

		rLayout = (RelativeLayout) inflater.inflate(R.layout.tutorial_register,
				container, false);

		initFragmentViews();
		
		rLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return listener.onTouchEvent(event);
			}

		});

		return rLayout;

	}

	@Override
	public void onStart() {
		super.onStart();
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStop() {
		super.onStop();
	}
	
	
	public void onRegister(String serial){
		
		teacher = Teacher.getTeacher();
		
		//Add edit text values to JSON object
		JSONObject values = new JSONObject();
		try {
			values.put("serial", serial);
			values.put("uid", teacher.getUid());
			
			JSONObject reg = new JSONObject();
			reg.put("registration", values);
			
			//Async login task
			AsyncTaskRunner runner = new AsyncTaskRunner(AsyncTaskRunner.POST_TASK, getActivity().getApplicationContext(), getActivity(), Constants.ON_REGISTER, regListener);
			runner.dataAsJsonString(reg.toString());
			runner.execute(new String[] { Constants.REGISTER_URL });

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
}
