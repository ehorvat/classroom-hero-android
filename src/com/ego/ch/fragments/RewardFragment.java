package com.ego.ch.fragments;

import java.util.ArrayList;
import java.util.Random;

import com.ego.ch.activities.StampActivity;
import com.ego.ch.exceptions.StampNotPaired;
import com.ego.ch.helper.RewardViewHelper;
import com.ego.ch.interfaces.RewardAnimationListener;
import com.ego.ch.model.Category;
import com.ego.ch.model.StudentUser;
import com.ego.ch.model.TeacherUser;
import com.ego.ch.singleton.Categories;
import com.ego.ch.singleton.Stamping;
import com.ego.ch.singleton.Teacher;
import com.ego.ch.singleton.TeacherStudents;
import com.ego.ch.util.Constants;
import com.ego.classhero.R;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;


public final class RewardFragment extends Fragment implements OnValueChangeListener{

	ProgressBar lvlProgress;
	ImageView bAwardHome, ivCoinSack, ivRewardInfo, ivStudentCoin1, ivStudentCoin2, ivStudentCoin3, ivLogoLvlUp, ivChest;
	
	TextView categoryName, name, currentScore, tvCurrentLvl, tvCategories, tvMsg, rewardMsg, tvXP;

	ArrayList<Category> categories;
	Category selectedCategory;

	RelativeLayout layout, achievement;
	RelativeLayout llPoints;
	FrameLayout lvlUpPanel;

	ObjectAnimator coinDrop;

	FragmentActivity listener;

	//DBAdapter adapter;
	//UserDetails details;

	Animation drop_lvl;

	Typeface amatic;

	String[] values;

	int lvlUpSound, coins, teacherStamp;

	NumberPicker picker;

	int current;

	private static final String KEY_CONTENT = "Reward";

	private String mContent = "";

	boolean loaded;
	SoundPool pool;

	StudentUser student;
	
	TeacherUser teacher;

	Context c;

	ObjectAnimator scaleUpX, scaleUpY, scaleDownX, scaleDownY, transitionX,
			transitionY;
	
	RelativeLayout.LayoutParams coin1, coin2, coin3;
	
	int numCoins;
	
	boolean didLvl;
	
	RewardAnimationListener ral = new RewardAnimationListener(){

		@Override
		public void onAnimationEnd() {
			
			hideStudentViews();
			
			fadeInPicker();
			
		}
		
	};

	public RewardFragment() {
		Stamping.canStamp(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.listener = (FragmentActivity) activity;

	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		int resId;
		if (StampActivity.class.isInstance(getActivity())) {
			
			resId = Constants.imageResIds[6];
			((StampActivity)getActivity()).loadBitmap(resId, ivChest, 400, 400); 
			resId = Constants.imageResIds[2];
			((StampActivity) getActivity()).loadBitmap(resId, ivCoinSack, 400,400);
			resId = Constants.imageResIds[3];
			((StampActivity) getActivity()).loadBitmap(resId, ivLogoLvlUp, 400,400);
			resId = Constants.imageResIds[5];
			((StampActivity) getActivity()).loadBitmap(resId, ivStudentCoin1,400, 400);
			resId = Constants.imageResIds[5];
			((StampActivity) getActivity()).loadBitmap(resId, ivStudentCoin2,400, 400);
			resId = Constants.imageResIds[5];
			((StampActivity) getActivity()).loadBitmap(resId, ivStudentCoin3,400, 400);
		}
		new AsyncRewardFrag().execute();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	

		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}

		c = getActivity().getApplicationContext();
		
		teacher = Teacher.getTeacher();

		//adapter.createDatabase();
		
		//Constants.createRewardSounds(this);
		

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		layout = (RelativeLayout) inflater.inflate(R.layout.rewardscreen,
				container, false);
		
	

		initFragmentViews();

		layout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return listener.onTouchEvent(event);
			}

		});

		return layout;

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}

	public void onPointAdded(final String serial) {

		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				rewardMsg.setVisibility(View.GONE);
			}

		});

		try {
			if(serial.equals(teacher.getStamp())){
				//Add one to all students
				
				if(loaded){
					pool.play(teacherStamp, 50, 100, 1, 0, 1f);
				}
				
				TeacherStudents.addOneToAll();
				
				hideDefaultViews();
				getActivity().runOnUiThread(new Runnable() {
				
				
				
					
					@Override
					public void run() {
						
						name.setText("+1 All Students!");
						name.setVisibility(View.VISIBLE);
						
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								name.setVisibility(View.GONE);
								fadeInPicker();
							}
						}, 1000);
						
					}
				});
				
				
				
				
			}else{
				//first check if student is in the teacher's class.
				student = TeacherStudents.getStudentBySerial(serial);
				
				//adapter.open();
				//If student not found in teacher's class, search sqlite to find student
				//student = adapter.findStudentBySerial(serial);
				
				didLvl = checkLvlUp();

				numCoins = determineReward();		
				
				hideDefaultViews();

				showStudentViews();

				startAnimations(numCoins);
			}
			
			

		} catch (StampNotPaired e) {
			Stamping.canStamp(true);
			e.printStackTrace();
		} catch (InterruptedException e) {
			Stamping.canStamp(true);
			e.printStackTrace();
		}
	}



	private void showStudentViews() {

		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Log.v("current coins2", student.getCurrentCoins()+"");

				// Set student view values
				name.setText(student.getFname().trim());
				currentScore.setText(String.valueOf(student.getCurrentCoins()));
				lvlProgress.setMax(student.getLvlUpAmount());
				tvCurrentLvl.setText(String.valueOf(student.getLvl()));

				// Show student views
				name.setVisibility(View.VISIBLE);
				llPoints.setVisibility(View.VISIBLE);
				lvlProgress.setVisibility(View.VISIBLE);
				tvCurrentLvl.setVisibility(View.VISIBLE);
				
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						lvlProgress.setProgress(student.getProgress());		
					}
				}, 500);
				

				
				AsyncRewardStudent addpoints = new AsyncRewardStudent(student, numCoins, didLvl);
				addpoints.execute();

			}
		});

	}
	
	private void hideStudentViews(){
		// Hide student views
		name.setVisibility(View.GONE);
		llPoints.setVisibility(View.GONE);
		lvlProgress.setVisibility(View.GONE);
		tvCurrentLvl.setVisibility(View.GONE);
	}

	private void hideDefaultViews() {

		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {

				picker.setVisibility(View.GONE);
			}
		});

	}

	private int determineReward() {

		int numCoins = 0;

		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((100 - 1) + 1) + 1;
		if (randomNum == 70) {
			// Award 3 points
			numCoins = 3;
		} else if (randomNum > 70) {
			// Award 2 points
			numCoins = 2;
		} else if (randomNum < 70) {
			// Award 1 point
			numCoins = 1;
		}

		return numCoins;
	}


	private void startAnimations(int numCoins) throws InterruptedException {

		switch (numCoins) {
		case 1:
			// Animations for one coin earned.
			animateOneCoin();
			break;
		case 2:
			// Animations for two coins earned.
			animateTwoCoins();
			break;
		case 3:
			// Three coins earned
			animateThreeCoins();
			break;
		}
	}

	private void animateOneCoin() {
		RewardViewHelper rvh = new RewardViewHelper(this, pool, ral);
		rvh.animateOneCoin(ivStudentCoin1, ivCoinSack, currentScore, tvXP, lvlProgress);
	}

	private void animateTwoCoins() throws InterruptedException {
		RewardViewHelper rvh = new RewardViewHelper(this, pool, ral);
		rvh.animateTwoCoins(ivStudentCoin2, ivStudentCoin3, ivCoinSack, currentScore, tvXP, lvlProgress);

	}

	private void animateThreeCoins() throws InterruptedException {
		RewardViewHelper rvh = new RewardViewHelper(this, pool, ral);
		rvh.animateThreeCoins(ivStudentCoin1, ivStudentCoin2, ivStudentCoin3, ivCoinSack, currentScore, tvXP, lvlProgress);
	}



	@Override
	public void onValueChange(NumberPicker arg0, int oldVal, int newVal) {
		current = newVal;
		String value = ((Category) categories.get(current)).getName();
		categoryName.setText(value);
	}

	private class AsyncRewardFrag extends
			AsyncTask<Integer, Void, ArrayList<Category>> {

		@Override
		protected ArrayList<Category> doInBackground(Integer... params) {

			pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
			pool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
				@Override
				public void onLoadComplete(SoundPool soundPool, int sampleId,
						int status) {
					loaded = true;
				}
			});
			lvlUpSound = pool.load(getActivity(), R.raw.achievement, 1);
			coins = pool.load(getActivity(), R.raw.coin_shake, 1);
			teacherStamp = pool.load(getActivity(), R.raw.teacherstamp, 1);

			//adapter.open();

			//categories = adapter.getEntities(DataBaseHelper.TABLE_CATEGORIES);
			categories = Categories.getCategories();

			//adapter.close();
			return categories;
		}

		@Override
		protected void onPostExecute(ArrayList<Category> categories) {
			// TODO Auto-generated method stub
			super.onPostExecute(categories);

			values = new String[categories.size()];
			for (int i = 0; i < categories.size(); i++) {
				values[i] = ((Category) categories.get(i)).getName();

			}

			picker.setMaxValue(values.length - 1);
			picker.setMinValue(0);
			picker.setDisplayedValues(values);
			picker.setOnValueChangedListener(RewardFragment.this);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			picker = (NumberPicker) layout.findViewById(R.id.categoryPicker);
			picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		}

	}

	private class AsyncRewardStudent extends AsyncTask<Void, Void, Void> {

		StudentUser student;
		int numPoints;
		boolean lvlUp;

		public AsyncRewardStudent(StudentUser student, int numPoints,
				boolean lvlUp) {
			this.student = student;
			this.numPoints = numPoints;
			this.lvlUp = lvlUp;
		}

		@Override
		protected Void doInBackground(Void... params) {
			
			
			
			//if(teacher.getCid() == student.getCid()){
				//Since student is in teacher's class, update singleton
				TeacherStudents.setGains(student.getUid(), numPoints, lvlUp);
			//}else{
				
				//Since student is not in the class, we have to iterate over a larger dataset and therefore must update the sqlite
				//adapter.addPoints(student, numPoints, lvlUp);
				
				//adapter.close();
			//}

			

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Stamping.canStamp(true);
			super.onPostExecute(result);
			
		}

	}
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("reward", "destroy");
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.v("reward", "detach");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("reward", "pause");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("reward", "stop");
	}
	

	public void initFragmentViews() {

		amatic = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/AmaticSC-Regular.ttf");

		tvCurrentLvl = (TextView) layout.findViewById(R.id.tvCurrentLvl);
		currentScore = (TextView) layout.findViewById(R.id.currentScore);
		rewardMsg = (TextView) layout.findViewById(R.id.tvRewardMsg);
		categoryName = (TextView) layout.findViewById(R.id.category_name);
		name = (TextView) layout.findViewById(R.id.fname);

		tvXP = (TextView) layout.findViewById(R.id.tvXP);

		name.setTypeface(amatic);
		currentScore.setTypeface(amatic);
		tvCurrentLvl.setTypeface(amatic);
		rewardMsg.setTypeface(amatic);
		categoryName.setTypeface(amatic);

		lvlProgress = (ProgressBar) layout.findViewById(R.id.myProgress);

		llPoints = (RelativeLayout) layout.findViewById(R.id.llPoints);

		achievement = (RelativeLayout) layout
				.findViewById(R.id.achievementLayout);
		tvMsg = (TextView) achievement.findViewById(R.id.tvMsg);
		
		ivChest = (ImageView) layout.findViewById(R.id.ivTreasureChest);
		
		ivCoinSack = (ImageView) layout.findViewById(R.id.ivCoinSack);

		ivStudentCoin1 = (ImageView) layout.findViewById(R.id.ivStudentCoin1);
		
		ivStudentCoin1.bringToFront();

		ivStudentCoin2 = (ImageView) layout.findViewById(R.id.ivStudentCoin2);

		ivStudentCoin3 = (ImageView) layout.findViewById(R.id.ivStudentCoin3);
		
		ivLogoLvlUp = (ImageView) layout.findViewById(R.id.ivLogoLvlUp);
		
		lvlUpPanel = (FrameLayout) layout.findViewById(R.id.flLvlUpPanel);
		
		drop_lvl = AnimationUtils.loadAnimation(getActivity(), R.anim.drop_lvl_panel);
		
		lvlUpPanel.setAnimation(drop_lvl);
	}
	
	private boolean checkLvlUp() {
		
		boolean lvlUp = false;
		
		int currentProgress = student.getProgress();
		

		int xpAfter = currentProgress + 1;
		if (xpAfter >= student.getLvlUpAmount()) {
			// Student lvl'd up set flag to true
			lvlUp = true;
			pool.play(lvlUpSound, 50, 100, 1, 0, 1f);
			
			getActivity().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					lvlUpPanel.startAnimation(drop_lvl);
					drop_lvl.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
							lvlUpPanel.setVisibility(View.VISIBLE);
							
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
						
									lvlUpPanel.setVisibility(View.GONE);

						}
					});
					
					lvlProgress.setProgress(0);
					lvlProgress.setMax(lvlProgress.getMax()+3);
					tvCurrentLvl.setText(String.valueOf(student.getLvl()+1));
				}
				
			});

			

		}
		return lvlUp;
	}

	public void fadeInPicker(){
		//Fade picker in
		AnimatorSet s = new AnimatorSet();
		s.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
	
				
				Stamping.canStamp(true);
				
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
		ObjectAnimator oa = ObjectAnimator.ofFloat(picker, "Alpha", .1f, 1f);
		s.play(oa);
		picker.setVisibility(View.VISIBLE);
		s.start();
		
	}

}
