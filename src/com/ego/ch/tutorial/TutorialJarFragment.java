package com.ego.ch.tutorial;


import com.ego.classhero.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TutorialJarFragment extends Fragment{
private static final String KEY_CONTENT = "TutorialJar";
	
	private String mContent = "???";

	TextView tvJarInstructions;
	
	RelativeLayout rLayout;
	
	Typeface amatic;

	public TutorialJarFragment() {
	
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
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
		
		tvJarInstructions = (TextView) rLayout.findViewById(R.id.tvJarInstructions);
		tvJarInstructions.setTypeface(amatic);
	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);

		rLayout = (RelativeLayout) inflater.inflate(R.layout.tutorial_jar,
				container, false);

		initFragmentViews();

		return rLayout;

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
}
