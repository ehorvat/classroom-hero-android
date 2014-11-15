package com.ego.ch.fragments;


import com.ego.ch.tasks.BitmapWorkerTask;
import com.ego.classhero.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ClassStats extends Fragment{

	LinearLayout lLayout;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		lLayout = (LinearLayout) inflater.inflate(R.layout.class_stats,
				container, false);
	
		 return lLayout;

	}
	
    public void loadBitmap(int resId, ImageView mImageView, int w, int h) {
   
        mImageView.setImageResource(resId);
        BitmapWorkerTask task = new BitmapWorkerTask(getActivity().getApplicationContext(),mImageView, w, h);
        task.execute(resId);
    }
}
