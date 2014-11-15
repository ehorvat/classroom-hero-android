package com.ego.ch.helper;

import java.util.ArrayList;

import com.ego.classhero.R;

import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/*
 * 
 * This class will produce an arraylist of coins and arraylist of object animators
 * based on the passed in number determined by user
 * 
 */

public class JarViewHelper {

	ArrayList<ImageView> coins;

	ImageView coin;

	ObjectAnimator animator;

	ArrayList<ObjectAnimator> animators;

	Fragment f;

	RelativeLayout layout;

	RelativeLayout.LayoutParams params;

	public JarViewHelper(Fragment f, RelativeLayout layout) {
		this.f = f;
		this.layout = layout;
	}

	public ArrayList<ImageView> getCoinsToDrop() {
		return this.coins;
	}

	public ArrayList<ObjectAnimator> getAnimators() {
		return this.animators;
	}

	public void buildCoinArrayList(int num) {

		//Intialize start delay to 0
		int startDelay = 0;

		//Define layout params for one coin
		params = new RelativeLayout.LayoutParams(160, 160);
		params.addRule(RelativeLayout.CENTER_IN_PARENT,
				RelativeLayout.TRUE);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP,
				RelativeLayout.TRUE);
		
		coins = new ArrayList<ImageView>();
		
		animators = new ArrayList<ObjectAnimator>();

		//Create new image view until reach passed in number
		for (int i = 0; i < num; i++) {

			//Create image view
			coin = new ImageView(f.getActivity().getApplicationContext());

			//Make coin invisible
			coin.setVisibility(View.INVISIBLE);

			//Set the coin's image resource
			coin.setImageResource(R.drawable.ch_coin);

			//Add the layout parameters to the coin
			coin.setLayoutParams(params);

			//Add coin to the parent layout
			layout.addView(coin);

			//Add coin to array list of coins
			coins.add(coin);

			//Create an animator for this coin
			animator = ObjectAnimator.ofFloat(coin, "TranslationY", -60, 300);
			
			//Set the start delay for this coin
			animator.setStartDelay(startDelay);
			
			//Increase the start delay by 200th of a second
			startDelay = startDelay + 200;
			
			//Add animator to arraylist of animators
			animators.add(animator);
			
		

		}
	}

}
