package com.ego.ch.activities;

import org.json.JSONException;
import org.json.JSONObject;

import com.ego.ch.singleton.Stamping;
import com.ego.ch.tutorial.TutorialJarFragment;
import com.ego.ch.tutorial.TutorialMarketFragment;
import com.ego.ch.tutorial.TutorialRegisterFragment;
import com.ego.ch.tutorial.TutorialRewardFragment;
import com.ego.ch.tutorial.TutorialWelcomeFragment;
import com.ego.classhero.R;
import com.upDownInteractive.snowshoelibrary.SnowShoeActivity;
import com.viewpagerindicator.CirclePageIndicator;







import com.viewpagerindicator.IconPagerAdapter;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class TutorialActivity extends SnowShoeActivity{
	
	//public final static String updateURL = "http://162.224.188.176:8009/CHAndroidLocalServices/services/update/students";
	
	 protected static final String[] CONTENT = new String[] { "This", "Is", "A", "Test", "Testies"};
	   private static final int[] ICONS = new int[] {
	        R.drawable.perm_group_calendar,
	        R.drawable.perm_group_camera,
	        R.drawable.perm_group_device_alarms,
	        R.drawable.perm_group_location
	    };
   Fragment mFrag;
   
   int currentPosition;
   MenuItem item;

   FragmentPagerAdapter adapter;
   FragmentManager fm;
   //UserDetails details;
   //ClassroomHeroDB handler;

   ViewPager pager;

   
 
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
   	
       setContentView(R.layout.simple_circles);
       //details = (UserDetails) getApplicationContext();
       Stamping.canStamp(true);
       //handler = details.getDBHandler();

		this.YOUR_APP_KEY = "d12276c2fac976865fdc";
		this.YOUR_APP_SECRET = "bf700a42149799b02641b2c2d93dbd3c4f995db8";
	
		
		fm = getSupportFragmentManager();

       adapter = new ClassroomHeroAdapter(fm);
       
       pager = (ViewPager)findViewById(R.id.pager);
       pager.setAdapter(adapter);
       
       ActionBar actionBar = getActionBar();
       actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(17, 100, 180)));
       
      
       

       CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);
       indicator.setViewPager(pager);
       indicator.setSnap(true);
       indicator.setFillColor(Color.BLUE);
       indicator.setStrokeColor(Color.GREEN);
       indicator.setRadius(15);
       
       
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.main, menu);
       item = menu.findItem(R.id.skip);
       
     
       
       item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				pager.setCurrentItem(4);
				return true;
			}
		});
       return super.onCreateOptionsMenu(menu);
   }

   class ClassroomHeroAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
   	
   	Fragment[] fragments = new Fragment[5];
   
   	
       public ClassroomHeroAdapter(FragmentManager fm) {
           super(fm);   
          
           fragments[0] = new TutorialWelcomeFragment();
          
           fragments[1] = new TutorialRewardFragment();        
           
           fragments[2] = new TutorialMarketFragment();
                    
           fragments[3] = new TutorialJarFragment();
           
           fragments[4] = new TutorialRegisterFragment();
           
       }

       @Override
       public Fragment getItem(int position) {
       	return fragments[position];
       }

       @Override
       public CharSequence getPageTitle(int position) {
           return CONTENT[position % CONTENT.length].toUpperCase();
       	
       }

       @Override public int getIconResId(int index) {
         return ICONS[index];
      
       }

     @Override
       public int getCount() {
         return CONTENT.length;
 
       }
   }
   
	@Override
	public void onStampResult() {
		// TODO Auto-generated method stub
		
		super.onStampResult();

			boolean success = false;
			JSONObject result;
			
			int index = 0;
			String serial = null;
			if(Stamping.canStamp){
				Stamping.canStamp(false);
			try {
				result = new JSONObject(this.getStampResult());
				JSONObject serialObj = (JSONObject) result.get("stamp");
				serial = serialObj.getString("serial");
				
				index = pager.getCurrentItem();
				
				if(index == 4){
					//Last tutorial screen stamped		
					mFrag = (TutorialRegisterFragment) adapter.getItem(4);
					((TutorialRegisterFragment) mFrag).onRegister(serial);
				}
	
			} catch (JSONException e) {
				success = false;
				e.printStackTrace();
			}
		}
	}
	
}
