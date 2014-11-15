package com.ego.ch.fragments;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import com.ego.ch.activities.StampActivity;
import com.ego.ch.exceptions.StampNotAllowed;
import com.ego.ch.helper.JarViewHelper;
import com.ego.ch.model.ClassJar;
import com.ego.ch.singleton.Jar;
import com.ego.ch.singleton.Stamping;
import com.ego.ch.singleton.Teacher;
import com.ego.ch.singleton.TeacherStudents;
import com.ego.ch.util.Constants;
import com.ego.classhero.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
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
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public final class JarFragment extends Fragment implements OnClickListener {
	private static final String KEY_CONTENT = "Jar";
	// DBAdapter adapter;
	// UserDetails details;

	ImageView ivAddCoins, ivSubtractCoins, coin, cork, ivJar, ivJarCoins,
			ivJarBack;
	TextView tvJar, tvAddAmount, tvJarError, tvComplete, plus, minus;
	RelativeLayout layout, achievement;
	FragmentActivity listener;

	RelativeLayout.LayoutParams params;

	Typeface amatic;
	MediaPlayer success, achieve, fail, coins, award;

	ProgressBar jarProgress;

	ArrayList<ObjectAnimator> as;
	ObjectAnimator coinDrop, corkDrop;

	Animation shake;

	int amountToAdd;
	int progress;
	int runningTotal;

	int newProgress, soundID, soundID2, soundID3, soundID4, soundID5;
	boolean loaded;

	ArrayList<ImageView> coinsToDrop;

	private String mContent = "???";

	Animation pulse;
	Animation dropCoin;

	SoundPool pool;

	public JarFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		amatic = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/AmaticSC-Regular.ttf");

		achieve = MediaPlayer.create(getActivity(), R.raw.achievement);
		success = MediaPlayer.create(getActivity(), R.raw.correct);
		fail = MediaPlayer.create(getActivity(), R.raw.register_fail);
		coins = MediaPlayer.create(getActivity(), R.raw.coin_shake);
		award = MediaPlayer.create(getActivity(), R.raw.award);

		pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		pool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				loaded = true;
			}
		});
		soundID = pool.load(getActivity(), R.raw.coin_shake, 1);
		soundID2 = pool.load(getActivity(), R.raw.achievement, 1);
		soundID3 = pool.load(getActivity(), R.raw.bloop, 1);
		soundID4 = pool.load(getActivity(), R.raw.correct, 1);
		soundID5 = pool.load(getActivity(), R.raw.jarsuccess, 1);
		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
		
		Jar.jarVals(Jar.j.getJarProgress(), Jar.j.getJarTotal(), Jar.j.getJarName());

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		Stamping.canStamp(true);
		int resId;
		if (StampActivity.class.isInstance(getActivity())) {

			resId = Constants.imageResIds[7];
			((StampActivity) getActivity()).loadBitmap(resId, ivJar, 400, 400);
			resId = Constants.imageResIds[8];
			((StampActivity) getActivity()).loadBitmap(resId, ivJarBack, 400,
					400);
			resId = Constants.imageResIds[9];
			((StampActivity) getActivity()).loadBitmap(resId, ivJarCoins, 400,
					400);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		Jar.isFilled(false);
		tvAddAmount.setText(String.valueOf(amountToAdd));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		amountToAdd = 1;
		layout = (RelativeLayout) inflater.inflate(R.layout.jarscreen,
				container, false);

		layout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return listener.onTouchEvent(event);
			}

		});

		initJarViews();

		// adapter.open();
		// jar = (ClassJar) adapter.getEntity(DataBaseHelper.TABLE_CLASSJAR, 0);
		// adapter.close();

		jarProgress.setMax(Jar.jarTotal);
		jarProgress.setProgress(Jar.jarProgress);
		jarProgress.bringToFront();

		/*
		 * if(jar.getJarName().equals("None")){ ivJar.setVisibility(View.GONE);
		 * jarProgress.setVisibility(View.GONE);
		 * ivAddCoins.setVisibility(View.GONE);
		 * ivSubtractCoins.setVisibility(View.GONE);
		 * ivJarBack.setVisibility(View.GONE); plus.setVisibility(View.GONE);
		 * minus.setVisibility(View.GONE); tvAddAmount.setVisibility(View.GONE);
		 * tvJarError.setVisibility(View.VISIBLE); tvJarError.setText(
		 * "You don't have a jar! Create one at www.classroom-hero.com!");
		 * }else{ LoadJar loadJar = new LoadJar(ivJar);
		 * loadJar.execute(R.id.ivClassJar);
		 * 
		 * LoadAdd loadAdd = new LoadAdd(ivAddCoins);
		 * loadAdd.execute(R.id.addCoins);
		 * 
		 * LoadMinus loadMinus = new LoadMinus(ivSubtractCoins);
		 * loadMinus.execute(R.id.subtractCoins);
		 * 
		 * 
		 * }
		 */

		ivAddCoins.setOnClickListener(this);
		ivSubtractCoins.setOnClickListener(this);

		pulse = AnimationUtils.loadAnimation(getActivity(), R.anim.pulse);
		dropCoin = AnimationUtils.loadAnimation(getActivity(),
				R.anim.falling_coins);

		return layout;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}

	public void onAddToJar(String serial) {

		shake = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_shake);

		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {

				tvJarError.setVisibility(View.GONE);
			}

		});

		// Check if jar is full
		if (!Jar.isFilled) {

			// Since jar is not filled, check if student stamped
			try {
				TeacherStudents.checkIfStudentStamped(serial);

				// Check if teacher has created a jar.
				if (!Jar.jarName.equals("None")) {

					// Make sure its the teacher stamping
					if (Teacher.isTeacherStamp(serial)) {

						// Initialize jar view helper with current fragment and
						// fragment layout
						final JarViewHelper helper = new JarViewHelper(this,
								layout);

						// Run animation set-up and animations on UI thread
						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {

								// Build an array of coins depending on value
								// chosen by user
								helper.buildCoinArrayList(amountToAdd);

								// Get the arraylist of coins from jar view
								// helper
								final ArrayList<ImageView> coins = helper
										.getCoinsToDrop();

								// Get the arraylist of object animators from
								// jar view helper
								ArrayList<ObjectAnimator> animators = helper
										.getAnimators();

								// Declare animator set
								AnimatorSet animSetXY = null;

								// Create an array of object animators that we
								// will loop through
								final ObjectAnimator[] objectAnimators = animators
										.toArray(new ObjectAnimator[animators
												.size()]);

								// Loop through the object animators, animating
								// one at a time
								for (int i = 0; i < objectAnimators.length; i++) {

									if (Jar.jarProgress >= Jar.jarTotal) {
										jarFilled();
										break;
									} else {

										Jar.addOneToJar();

										// Counter
										final int current = i;

										// Instantiate animator set
										animSetXY = new AnimatorSet();

										// Start animation
										animSetXY
												.play(objectAnimators[current]);
										animSetXY.setDuration(1000);

										// Add an animation listener to each
										// animating coin
										objectAnimators[current]
												.addListener(new AnimatorListenerAdapter() {

													@Override
													public void onAnimationCancel(
															Animator animation) {

														super.onAnimationCancel(animation);
													}

													@Override
													public void onAnimationEnd(
															Animator animation) {

														super.onAnimationEnd(animation);
														// If this is the last
														// coin, set the good to
														// stamp flag to true
														if (coins.size() - 1 == current)
															Stamping.canStamp(true);

														// Hide the coin that
														// finished its
														// animation
														coins.get(current)
																.setVisibility(
																		View.INVISIBLE);

														// Asyncronously update
														// progress bar
														new UpdateBarTask()
																.execute();

													}

													@Override
													public void onAnimationRepeat(
															Animator animation) {
														// TODO Auto-generated
														// method stub
														super.onAnimationRepeat(animation);
													}

													@Override
													public void onAnimationStart(
															Animator animation) {

														super.onAnimationStart(animation);

														// Make the current
														// dropping coin visible
														coins.get(current)
																.setVisibility(
																		View.VISIBLE);
														jarProgress
																.bringToFront();

													}
												});
										animSetXY.start();
									}

								}

								AsyncUpdateJar jarUpdate = new AsyncUpdateJar(
										amountToAdd);
								jarUpdate.execute();
							}
						});

					}
				}

			} catch (StampNotAllowed e) {
				tvJarError.setVisibility(View.VISIBLE);
				tvJarError.setText(e.getMessage());
				Stamping.canStamp(true);
				e.printStackTrace();
			}
		} else {

			// Jar is filled. Both students and teachers can stamp the screen
			Jar.isFilled(false);
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					cork.setVisibility(View.GONE);
					jarProgress.setProgress(0);
					Jar.resetJar();
					achievement.setVisibility(View.GONE);
					Stamping.canStamp(true);
				}
			});
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.subtractCoins:
			if (amountToAdd > 1) {
				ivSubtractCoins.startAnimation(pulse);
				amountToAdd = amountToAdd - 1;
				tvAddAmount.setText(String.valueOf(amountToAdd));
			}

			break;

		case R.id.addCoins:

			if (amountToAdd < 10) {
				ivAddCoins.startAnimation(pulse);
				amountToAdd = amountToAdd + 1;
				tvAddAmount.setText(String.valueOf(amountToAdd));
			}

			break;
		}
	}

	class LoadJar extends AsyncTask<Integer, Void, Bitmap> {
		private final WeakReference<ImageView> imageViewReference;
		private int data = 0;

		public LoadJar(ImageView imageView) {
			// Use a WeakReference to ensure the ImageView can be garbage
			// collected
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		// Decode image in background.
		@Override
		protected Bitmap doInBackground(Integer... params) {
			data = params[0];

			// Get imageview width and height
			BitmapFactory.Options options = new BitmapFactory.Options();
			BitmapFactory.decodeResource(getResources(), data, options);

			return decodeSampledBitmapFromResource(getResources(),
					R.drawable.jar, 20, 20);
		}

		// Once complete, see if ImageView is still around and set bitmap.
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (imageViewReference != null && bitmap != null) {
				final ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}
	}

	class AsyncUpdateJar extends AsyncTask<Void, Void, Void> {

		int added;

		public AsyncUpdateJar(int added) {
			this.added = added;
		}

		// Decode image in background.
		@Override
		protected Void doInBackground(Void... params) {

			Jar.toAdd(added);
			Jar.addToJar();

			return null;

		}

		// Once complete, see if ImageView is still around and set bitmap.
		@Override
		protected void onPostExecute(Void v) {

		}
	}

	class LoadLid extends AsyncTask<Integer, Void, Bitmap> {
		private final WeakReference<ImageView> imageViewReference;
		private int data = 0;

		public LoadLid(ImageView imageView) {
			// Use a WeakReference to ensure the ImageView can be garbage
			// collected
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		// Decode image in background.
		@Override
		protected Bitmap doInBackground(Integer... params) {
			data = params[0];

			// Get imageview width and height
			BitmapFactory.Options options = new BitmapFactory.Options();
			BitmapFactory.decodeResource(getResources(), data, options);

			return decodeSampledBitmapFromResource(getResources(),
					R.drawable.lid, 400, 400);
		}

		// Once complete, see if ImageView is still around and set bitmap.
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (imageViewReference != null && bitmap != null) {
				final ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}
	}

	public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	private void jarFilled() {

		Jar.isFilled(true);

		params = new RelativeLayout.LayoutParams(680, 700);
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

		cork = new ImageView(getActivity().getApplicationContext());

		LoadLid loadLid = new LoadLid(cork);
		loadLid.execute(R.drawable.lid);

		cork.setVisibility(View.INVISIBLE);

		cork.setImageResource(R.drawable.lid);

		cork.setLayoutParams(params);

		layout.addView(cork);

		corkDrop = ObjectAnimator.ofFloat(cork, "TranslationY", -225, 55);
		corkDrop.setDuration(200);

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				corkDrop.start();
			}

		}, 3000);

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				achievement.bringToFront();
				achievement.setVisibility(View.VISIBLE);

			}

		}, 5000);

		corkDrop.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				super.onAnimationStart(animation);
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						cork.setVisibility(View.VISIBLE);
						jarProgress.bringToFront();

					}

				});
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				super.onAnimationEnd(animation);
				if (loaded) {
					pool.play(soundID3, 50, 150, 1, 0, 1f);
				}
				Stamping.canStamp(true);
			}

		});

	}

	private class UpdateBarTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			publishProgress(++runningTotal);

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

			if (loaded) {
				pool.play(soundID, 50, 100, 1, 0, 1f);
			}

			// progress bar
			jarProgress.setProgress(Jar.jarProgress + 1);

		}
	}

	// ///////////////////////////////////
	//
	// Jar fragment view initialization
	//
	// ///////////////////////////////////

	private void initJarViews() {
		tvComplete = (TextView) layout.findViewById(R.id.tvComplete);

		achievement = (RelativeLayout) layout
				.findViewById(R.id.achievementLayout);

		tvJar = (TextView) layout.findViewById(R.id.tvClassJar);
		tvAddAmount = (TextView) layout.findViewById(R.id.currentAmount);
		tvJarError = (TextView) layout.findViewById(R.id.tvJarErr);
		plus = (TextView) layout.findViewById(R.id.plus);
		minus = (TextView) layout.findViewById(R.id.minus);

		tvJar.setTypeface(amatic);
		tvAddAmount.setTypeface(amatic);
		tvJarError.setTypeface(amatic);

		jarProgress = (ProgressBar) layout.findViewById(R.id.jarProgress);
		jarProgress.setVisibility(View.VISIBLE);

		ivAddCoins = (ImageView) layout.findViewById(R.id.addCoins);
		ivSubtractCoins = (ImageView) layout.findViewById(R.id.subtractCoins);
		ivJarBack = (ImageView) layout.findViewById(R.id.ivBackLayer);

		ivJar = (ImageView) layout.findViewById(R.id.ivClassJar);

	}

}
