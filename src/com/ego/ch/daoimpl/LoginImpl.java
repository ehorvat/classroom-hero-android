package com.ego.ch.daoimpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ego.ch.activities.StampActivity;
import com.ego.ch.activities.TutorialActivity;
import com.ego.ch.dao.LoginDao;
import com.ego.ch.exceptions.LoginFailed;
import com.ego.ch.model.Category;
import com.ego.ch.model.ClassJar;
import com.ego.ch.model.Item;
import com.ego.ch.model.StudentUser;
import com.ego.ch.model.TeacherUser;
import com.ego.ch.singleton.Categories;
import com.ego.ch.singleton.Items;
import com.ego.ch.singleton.Jar;
import com.ego.ch.singleton.Stamping;
import com.ego.ch.singleton.Teacher;
import com.ego.ch.singleton.TeacherStudents;

public class LoginImpl implements LoginDao{
	
	Context c = null;

	JSONObject parent = null;
	
	JSONObject userObj = null;
	
	JSONArray entities = null;
	
	Intent i = null;
	
	Activity a = null;
	
	public LoginImpl(String data, Activity a) throws JSONException, LoginFailed{
		Log.v("DATA", data);
		
		this.a = a;
		
		// Instantiate JSONObject containing all data from server
		parent = new JSONObject(data);

		c = a.getApplicationContext();

		auth();
	}

	@Override
	public void auth() throws LoginFailed, JSONException{
		
		if (parent.isNull("success")) {

			// Get login JSONObject
			userObj = parent.getJSONObject("login");

			// Get success field
			String success = userObj.getString("success");
			if (Boolean.parseBoolean(success) == true) {

				// Login successful. Instantiate global class
				Stamping.canStamp(true);


				userType();
			} else {
				// Login failed
				throw new LoginFailed("Invalid email or password");
			}

		}

		
	}

	@Override
	public void userType() throws JSONException {
		if (userObj.getInt("role") == 3) {

			// Admin is logging in
			//createAdmin();

		} else if (userObj.getInt("role") == 1) {

			// Teacher is logging in
			teacherLogin();
		}
		
	}

	@Override
	public void teacherLogin() throws JSONException {
		
		//Declare empty variables for students, items, categories, and jar
				JSONObject studentArr = null;
				JSONObject itemsArr = null;
				JSONObject classJar = null;
				JSONObject categoryArr = null;
				
				ClassJar jar = null;

				//Instantiate database adapter
				//DBAdapter mDbHelper = new DBAdapter(a.getApplicationContext());
				
				//Create and open sqlite database from assets
				//mDbHelper.createDatabase();
				//mDbHelper.open();
				
				int cid = userObj.getInt("cid");
				
				
				TeacherUser teacher = new TeacherUser(userObj.getInt("uid"), userObj.getInt("cid"),
						userObj.getString("fname"), userObj.getInt("coins"),
						userObj.getInt("classLvl"), userObj.getInt("classProgress"),
						userObj.getInt("nextLvl"), userObj.getString("stamp"),
						userObj.getInt("role"));
				
				//Store teacher in database
				//mDbHelper.addTeacher(teacher);

				//Store teacher in singleton object
				
				Teacher.addTeacher(teacher);
				
				//Get entire JSON String from server
				entities = parent.getJSONArray("entities");

				//Get students from json
				studentArr = entities.getJSONObject(3);
			

				JSONArray students = studentArr.getJSONArray("students");

				for (int i = 0; i < students.length(); i++) {
					JSONObject studentJson = students.getJSONObject(i);
					
					StudentUser student = new StudentUser(studentJson.getInt("uid"),
							studentJson.getString("name"),
							studentJson.getInt("currentCoins"),
							studentJson.getInt("totalCoins"),
							studentJson.getInt("lvl"), studentJson.getInt("progress"),
							studentJson.getInt("lvlUpAmount"),
							studentJson.getString("stamp").trim(),
							studentJson.getInt("isActivated"), 0, 0, 0, 0,
							studentJson.getInt("cid"), 0);

					//Add all students to DB
					//mDbHelper.addStudent(student);
					
					//If student cid is equal to cid of teacher, store in singleton class
					if(cid == student.getCid()){
						TeacherStudents.addStudent(student);
					}
					

				}

				
				//Get jar from json
				classJar = entities.getJSONObject(2);

				//If jar exists, store in sqlite
				if (entities.getJSONObject(2) != null || entities.getJSONObject(2).toString() != "") {

					//mDbHelper.addJar(classJar.getInt("jid"),classJar.getString("name"), classJar.getInt("progress"),classJar.getInt("total"), 0);

					jar = new ClassJar(classJar.getInt("jid"), classJar.getString("name"), classJar.getInt("progress"), classJar.getInt("total"));
				
				}
				
				//Also store jar in singleton class
			
				Jar.addJar(jar);

				
				//Get category from json
				categoryArr = entities.getJSONObject(1);

				JSONArray categories = categoryArr.getJSONArray("categories");
				for (int i = 0; i < categories.length(); i++) {
					
					JSONObject categoryJson = categories.getJSONObject(i);
					
					Category category = new Category(categoryJson.getString("name"),categoryJson.getInt("id"));

					//Add category to sqlite
					//mDbHelper.addCategory(category);
					
					//Add category to singleton object
					Categories.addCategory(category);
					

				}
				
				//Get items from json
				itemsArr = entities.getJSONObject(0);
				
				JSONArray items = itemsArr.getJSONArray("items");

				for (int i = 0; i < items.length(); i++) {

					JSONObject itemJson = items.getJSONObject(i);
					
					Item item = new Item(itemJson.getInt("id"), itemJson.getInt("cost"), itemJson.getString("name"));
					
					//Add item to sqlite
					//mDbHelper.addItem(item);

					//Add item to singleton class
					Items.addItem(item);
					
				}
				
				//Close the database
				//mDbHelper.close();
				
				//Check user's activation field and redirect to the according activity
				checkUserActivation();
		
		
	}

	@Override
	public void checkUserActivation() throws JSONException {
		int isActivated = userObj.getInt("isactivated");

		if (isActivated == 0) {

			// Teacher or Admin is not registered. Redirect to registration
			// screen
			i = new Intent(a, TutorialActivity.class);
			a.startActivity(i);

		} else if (isActivated == 1) {

			// User is registered. Determine user instance and redirect
			// accordingly
			if (userObj.getInt("role") == 1) {
				// Redirect to teacher views

				Intent i = new Intent(a, StampActivity.class);
				a.startActivity(i);

			} else if (userObj.getInt("role") == 3) {
				// Redirect to admin views
			}

		}
		
	}

}
