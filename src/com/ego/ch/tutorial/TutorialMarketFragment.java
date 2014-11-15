package com.ego.ch.tutorial;


import com.ego.classhero.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TutorialMarketFragment extends Fragment {
private static final String KEY_CONTENT = "TutorialMarket";
	
	private String mContent = "???";

	TextView tvMarketInstructions;
	
	RelativeLayout rLayout;
	
	Typeface amatic;

	public TutorialMarketFragment() {
	
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
		
		tvMarketInstructions = (TextView) rLayout.findViewById(R.id.tvMarketInstructions);
		tvMarketInstructions.setTypeface(amatic);
		
		NumberPicker np= (NumberPicker) rLayout.findViewById(R.id.itemPicker);
		String[] values=new String[3];
		values[0]="Homework Pass (40)";
		values[1]="Lunch with the teacher (50)";
		values[2]="Mechanical Pencil (20)";
		np.setMaxValue(values.length-1);
		np.setMinValue(0);
		np.setDisplayedValues(values);
		
		np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);

		rLayout = (RelativeLayout) inflater.inflate(R.layout.tutorial_market,
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
