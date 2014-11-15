package com.ego.ch.activities;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ImageView;

import com.ego.ch.fragments.JarFragment;
import com.ego.ch.fragments.MarketFragment;
import com.ego.ch.fragments.ProfileFragment;
import com.ego.ch.fragments.RewardFragment;
import com.ego.ch.singleton.Stamping;
import com.ego.ch.tasks.BitmapWorkerTask;
import com.ego.classhero.R;

import com.upDownInteractive.snowshoelibrary.SnowShoeActivity;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class StampActivity extends SnowShoeActivity {

	Fragment mFrag;

	int currentPosition;

	FragmentPagerAdapter adapter;
	FragmentManager fm;

	ViewPager pager;

	private static Context c;

	// public final static String updateURL =
	// "http://androidenv-tpm32kxnqk.elasticbeanstalk.com/services/update/students";
	public final static String updateURL = "http://162.224.188.176:8009/CHAndroidLocalServices/services/update/students";

	private static final String[] CONTENT = new String[] { "Reward",
			"Class Jar", "Market", "Attendance" };
	private static final int[] ICONS = new int[] {
			R.drawable.perm_group_camera, R.drawable.perm_group_device_alarms,
			R.drawable.perm_group_location, R.drawable.perm_group_calendar };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_tabs);

		this.YOUR_APP_KEY = "d12276c2fac976865fdc";
		this.YOUR_APP_SECRET = "bf700a42149799b02641b2c2d93dbd3c4f995db8";

		fm = getSupportFragmentManager();

		adapter = new ClassroomHeroAdapter(fm);

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		indicator.setBackgroundColor(Color.rgb(17, 100, 180));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_fragment, menu);

		MenuItem item = menu.findItem(R.id.classProfile);

		item.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {

				Intent i = new Intent(StampActivity.this, HomeActivity.class);
				startActivity(i);
				return true;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}

	class ClassroomHeroAdapter extends FragmentPagerAdapter implements
			IconPagerAdapter {

		Fragment[] fragments = new Fragment[4];

		public ClassroomHeroAdapter(FragmentManager fm) {
			super(fm);

			fragments[0] = new RewardFragment();

			fragments[1] = new JarFragment();

			fragments[2] = new MarketFragment();

			fragments[3] = new ProfileFragment();

		}

		@Override
		public Fragment getItem(int position) {
			return fragments[position];
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length].toUpperCase();
		}

		@Override
		public int getIconResId(int index) {
			return ICONS[index];
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
		// AsyncPush changed = new AsyncPush();
		// changed.execute();

	}

	@Override
	protected void onPause() {
		super.onPause();

		// AsyncPushPause changed = new AsyncPushPause();
		// changed.execute();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public void loadBitmap(int resId, ImageView mImageView, int w, int h) {
		mImageView.setImageResource(resId);
		BitmapWorkerTask task = new BitmapWorkerTask(getApplicationContext(),
				mImageView, w, h);
		task.execute(resId);
	}

	@Override
	public void onStampResult() {
		super.onStampResult();

		//boolean success = false;
		JSONObject result;

		int index = 0;
		String serial = null;
		if (Stamping.canStamp) {
			Stamping.canStamp(false);
			try {
				result = new JSONObject(this.getStampResult());
				if(result.isNull("stamp")){
					Stamping.canStamp(true);
				}else{
					JSONObject serialObj = (JSONObject) result.get("stamp");
					serial = serialObj.getString("serial");
				}
		

				if (serial != null) {

					index = pager.getCurrentItem();

					switch (index) {

					case 0:

						//success = true;

						mFrag = (RewardFragment) adapter.getItem(index);
						((RewardFragment) mFrag).onPointAdded(serial);

						break;

					case 1:

						//success = true;

						//mFrag = (JarFragment) adapter.getItem(index);
						//((JarFragment) mFrag).onAddToJar(serial);

						break;

					case 2:

						break;

					case 3:

						//success = true;
						//mFrag = (MarketFragment) adapter.getItem(index);
						//((MarketFragment) mFrag).onItemSale(serial);

						break;
					}

				} else {
					Stamping.canStamp(true);
				}

			} catch (JSONException e) {
				//success = false;
				switch (index) {
				case 0:

					break;

				case 1:

					mFrag = (RewardFragment) adapter.getItem(index);
					((RewardFragment) mFrag).onPointAdded(serial);

					break;

				case 2:

					//mFrag = (JarFragment) adapter.getItem(index);
					//((JarFragment) mFrag).onAddToJar(serial);

					break;

				case 3:

					//mFrag = (MarketFragment) adapter.getItem(index);
					//((MarketFragment) mFrag).onItemSale(serial);

					break;
				}

			}
		}
	}

}
