package com.ego.ch.activities;

import com.ego.classhero.R;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeActivity extends FragmentActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		// Inflate your custom layout
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_home, null);

		// Set up your ActionBar
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);

		// You customization
		final int actionBarColor = getResources().getColor(R.color.action_bar);
		actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

		final Button abBack = (Button) findViewById(R.id.action_bar_back);
		abBack.setText("Back");

		final Button abStudents = (Button) findViewById(R.id.action_bar_students);
		abStudents.setText("My Students");

		abBack.setOnClickListener(this);
		abStudents.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}

	@Override
	protected void onPause() {

		super.onPause();
	}

	@Override
	protected void onRestart() {

		super.onRestart();
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	@Override
	protected void onStart() {

		super.onStart();
	}

	@Override
	protected void onStop() {
		finish();
		super.onStop();
	}

	@Override
	public void onClick(View v) {

		Intent i = null;
		switch (v.getId()) {
		case R.id.action_bar_back:

			// Direct user back to stamp screens
			i = new Intent(this, StampActivity.class);

			startActivity(i);

			break;

		case R.id.action_bar_students:

			i = new Intent(HomeActivity.this, StudentCardsActivity.class);

			startActivity(i);

			break;
		}

	}

}
