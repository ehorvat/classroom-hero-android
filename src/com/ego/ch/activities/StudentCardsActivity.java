package com.ego.ch.activities;

import java.util.ArrayList;








import com.ego.ch.model.StudentUser;
import com.ego.ch.singleton.TeacherStudents;
import com.ego.ch.util.BaseInflaterAdapter;
import com.ego.ch.util.CardInflater;
import com.ego.ch.util.CardItemData;
import com.ego.classhero.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ListView;

public class StudentCardsActivity extends Activity implements
		OnMenuItemClickListener {
	/**
	 * Called when the activity is first created.
	 */

	//DBAdapter adapter;

	ArrayList<StudentUser> students;
	
	ListView list;

	@Override
	protected void onStart() {
		

		//adapter = new DBAdapter(getApplicationContext());
		//adapter.createDatabase();
		//adapter.open();
		super.onStart();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.listview);

		list = (ListView) findViewById(R.id.list_view);
	
		

		new AsyncGetStudents().execute();
	
	}

	/**
	 * Action Bar
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.student_list, menu);

		MenuItem registration = menu.findItem(R.id.register_students);
		MenuItem back = menu.findItem(R.id.backToProfile);

		registration.setOnMenuItemClickListener(this);
		back.setOnMenuItemClickListener(this);
		return true;
	}

	/**
	 * Action Bar button click listeners
	 */

	@Override
	public boolean onMenuItemClick(MenuItem item) {

		Intent i = null;

		switch (item.getItemId()) {

		case R.id.register_students:

			// Direct to registration activity
			i = new Intent(StudentCardsActivity.this, RegisterStudents.class);
			startActivity(i);

			break;

		case R.id.backToProfile:

			// Direct to teacher profile
			i = new Intent(StudentCardsActivity.this, HomeActivity.class);
			startActivity(i);

			break;
		}
		return false;
	}

	private class AsyncGetStudents extends
			AsyncTask<Void, Void, ArrayList<StudentUser>> {

		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//adapter.open();
		}

		@Override
		protected ArrayList<StudentUser> doInBackground(Void... params) {

			//students = adapter.getAllStudents();
			
			students = TeacherStudents.getStudents();
			

			return students;
		}

		@Override
		protected void onPostExecute(ArrayList<StudentUser> students) {
			super.onPostExecute(students);
			//adapter.close();
			list.addHeaderView(new View(StudentCardsActivity.this));
			list.addFooterView(new View(StudentCardsActivity.this));

			BaseInflaterAdapter<CardItemData> adapter = new BaseInflaterAdapter<CardItemData>(new CardInflater(getApplicationContext()));
			for (int i = 0; i < students.size(); i++) {
				CardItemData data = new CardItemData(students.get(i).getFname(),students.get(i).getLvl(), students.get(i).getCurrentCoins());
				adapter.addItem(data, false);
			}

			list.setAdapter(adapter);
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		finish();
		super.onStop();
	}

	@Override
	protected void onPause() {
		//adapter.close();
		super.onPause();
	}
	
	
	
	

}
