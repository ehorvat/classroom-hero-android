package com.ego.ch.activities;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ego.ch.exceptions.InvalidStamp;
import com.ego.ch.exceptions.StampRegistered;
import com.ego.ch.model.StudentUser;
import com.ego.ch.model.TeacherUser;
import com.ego.ch.singleton.Stamping;
import com.ego.ch.singleton.Teacher;
import com.ego.ch.singleton.TeacherStudents;
import com.ego.ch.tasks.BitmapWorkerTask;
import com.ego.ch.util.Constants;
import com.ego.classhero.R;
import com.upDownInteractive.snowshoelibrary.SnowShoeActivity;

public class RegisterStudents extends SnowShoeActivity implements
		OnMenuItemClickListener {

	ArrayList<StudentUser> students;

	TextView tvStudentName;

	TextView tvRegError;

	ImageView ivLogo, ivCheck;

	int i = 0;

	// DBAdapter adapter;

	TeacherUser teacher;

	public void loadBitmap(int resId, ImageView mImageView, int w, int h) {
		mImageView.setImageResource(resId);
		BitmapWorkerTask task = new BitmapWorkerTask(getApplicationContext(),
				mImageView, w, h);
		task.execute(resId);
	}

	@Override
	protected void onStart() {
		super.onStart();
		ivLogo = (ImageView) findViewById(R.id.ivLogo);
		ivCheck = (ImageView) findViewById(R.id.ivCheck);

		loadBitmap(Constants.imageResIds[3], ivLogo, 300, 300);
		loadBitmap(Constants.imageResIds[4], ivCheck, 300, 300);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.YOUR_APP_KEY = "d12276c2fac976865fdc";
		this.YOUR_APP_SECRET = "bf700a42149799b02641b2c2d93dbd3c4f995db8";
		setContentView(R.layout.register_students);

		tvStudentName = (TextView) findViewById(R.id.tvStudentName);
		tvRegError = (TextView) findViewById(R.id.tvRegError);

		// adapter = new DBAdapter(getApplicationContext());
		// adapter.createDatabase();
		// adapter.open();

		teacher = Teacher.getTeacher();

		// details = (UserDetails) getApplicationContext();

		new AsyncGetStudents().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_register, menu);

		getActionBar().setDisplayShowHomeEnabled(false);

		MenuItem item = menu.findItem(R.id.next);
		MenuItem back = menu.findItem(R.id.toStudentList);

		item.setOnMenuItemClickListener(this);
		back.setOnMenuItemClickListener(this);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onStampResult() {
		super.onStampResult();
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				tvRegError.setText("");
			}
		});
		if (Stamping.canStamp) {
			Stamping.canStamp(false);

			JSONObject result;

			try {
				result = new JSONObject(this.getStampResult());
				JSONObject serialObj = (JSONObject) result.get("stamp");
				String serial = serialObj.getString("serial");
				
				//First check if teacher stamp
				Teacher.compareStamp(serial);
				
				//Next check if registered to a student
				TeacherStudents.checkSerial(serial);
				
				//When I add sqllite check serial against all students in the school

				//If passed both checks, Get currently displayed student name
				StudentUser student = students.get(i);

				// Remove newly registered student from array list
				students.remove(i);

				// Register student into database on a different thread
				AsyncRegister register = new AsyncRegister(student, serial);
				register.execute();

				// Prepare animation
				final Animation pulseReg = AnimationUtils.loadAnimation(
						RegisterStudents.this, R.anim.pulse);

				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						// Pulse logo
						ivLogo.startAnimation(pulseReg);

						// Change student name color to green
						tvStudentName.setTextColor(RegisterStudents.this
								.getResources().getColor(R.color.green));

						// Show check mark
						ivCheck.setVisibility(View.VISIBLE);

						// Instantiate a new handler
						Handler handler = new Handler();

						// Call post delayed function on new handler
						handler.postDelayed(new Runnable() {

							@Override
							public void run() {

								// Set student name text view color to black
								tvStudentName.setTextColor(Color.BLACK);

								// After half a second set name to next student
								// in array
								tvStudentName.setText(students.get(i)
										.getFname());

								// Hide the check mark
								ivCheck.setVisibility(View.GONE);

							}

						}, 1000);

					}
				});

			} catch (JSONException e) {
				Stamping.canStamp(true);
				e.printStackTrace();
			} catch (final StampRegistered e) {
				Stamping.canStamp(true);
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						tvRegError.setText(e.getMessage());
						tvRegError.setVisibility(View.VISIBLE);						
					}
				});
		
				e.printStackTrace();
			}

			// adapter.open();
			// Check if serial is already registered
			// adapter.checkSerial(serial, teacher.getStamp());
		}

	}

	private StudentUser nextStudent() {
		i++;

		if (i >= students.size()) {
			i = 0;
		}

		return students.get(i);
	}

	private class AsyncGetStudents extends
			AsyncTask<Void, Void, ArrayList<StudentUser>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected ArrayList<StudentUser> doInBackground(Void... params) {

			students = TeacherStudents.getUnregisteredStudents();
			return students;
		}

		@Override
		protected void onPostExecute(ArrayList<StudentUser> students) {
			super.onPostExecute(students);
			tvStudentName.setText(students.get(0).getFname().trim());
		}

	}

	@Override
	protected void onPause() {
		super.onPause();

		// adapter.close();

	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();

		// adapter.open();

	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.next:

			// Step to next student in array list

			final StudentUser next = nextStudent();

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					tvStudentName.setText(next.getFname().trim());
				}
			});
			break;

		case R.id.toStudentList:

			// Direct to student list
			Intent i = new Intent(RegisterStudents.this,
					StudentCardsActivity.class);
			startActivity(i);

		}
		return false;
	}

	private class AsyncRegister extends AsyncTask<Void, Void, Void> {

		StudentUser student;
		String serial;

		AsyncRegister(StudentUser student, String serial) {
			this.student = student;
			this.serial = serial;
		}

		@Override
		protected Void doInBackground(Void... params) {

			// if(teacher.getCid() == student.getCid()){
			// Register student to serial in singleton
			TeacherStudents.setRegistered(student.getUid(), serial);
			// }

			/*
			 * else{
			 * 
			 * // Register student to serial in sqlite
			 * //adapter.register(serial, student); }
			 */

			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			super.onPostExecute(v);
			// adapter.close();
			// Allow user to stamp the screen
			Stamping.canStamp(true);

		}

	}

}
