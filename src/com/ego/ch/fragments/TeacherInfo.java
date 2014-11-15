package com.ego.ch.fragments;

import com.ego.ch.activities.HomeActivity;
import com.ego.ch.tasks.BitmapWorkerTask;
import com.ego.ch.util.Constants;
import com.ego.classhero.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class TeacherInfo extends Fragment{

	ImageView teacherCoin;
	
	LinearLayout lLayout;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (HomeActivity.class.isInstance(getActivity())) {
			final int resId = Constants.imageResIds[0];
		
			loadBitmap(resId, teacherCoin, 400, 400);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		lLayout = (LinearLayout) inflater.inflate(R.layout.teacher_info,
				container, false);
		teacherCoin = (ImageView) lLayout.findViewById(R.id.ivTeacherCoin);
		 return lLayout;

	}
	
    public void loadBitmap(int resId, ImageView mImageView, int w, int h) {
    	Log.v("inside load butmap","inside");
        mImageView.setImageResource(resId);
        BitmapWorkerTask task = new BitmapWorkerTask(getActivity().getApplicationContext(),mImageView, w, h);
        task.execute(resId);
    }

}
