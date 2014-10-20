package com.mm.rabbit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chrisrenke.drawerarrowdrawable.DrawerArrowSample;

import com.devspark.appmsg.sample.AppMsgActivity;
import com.flavienlaurent.vdh.DragMainActivity;
import com.jakewharton.nineoldandroids.sample.Demos;
import com.jakewharton.nineoldandroids.sample.Toggles;
import com.jakewharton.nineoldandroids.sample.VPADemo;
import com.jakewharton.nineoldandroids.sample.apidemos.AnimationCloning;
import com.jakewharton.nineoldandroids.sample.apidemos.AnimationLoading;
import com.jakewharton.nineoldandroids.sample.apidemos.AnimationSeeking;
import com.jakewharton.nineoldandroids.sample.apidemos.AnimatorEvents;
import com.jakewharton.nineoldandroids.sample.apidemos.BouncingBalls;
import com.jakewharton.nineoldandroids.sample.apidemos.CustomEvaluator;
import com.jakewharton.nineoldandroids.sample.apidemos.MultiPropertyAnimation;
import com.jakewharton.nineoldandroids.sample.apidemos.ReversingAnimation;
import com.jakewharton.nineoldandroids.sample.droidflakes.Droidflakes;
import com.jakewharton.nineoldandroids.sample.pathanimation.PathAnimationActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends ActionBarActivity implements TabListener {
	private ActionBar mActionBar;
	
	private String[] mActionLiStrings={"全部","热门信息"};

	private ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setIcon(null);
		
		listview = (ListView) findViewById(R.id.listview);
		
		setViewEvent();
		
//		SpinnerAdapter mSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mActionLiStrings);
//		
//		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//		mActionBar.setListNavigationCallbacks(mSpinnerAdapter, new OnNavigationListener() {
//			
//			@Override
//			public boolean onNavigationItemSelected(int arg0, long arg1) {
//				Toast.makeText(getApplicationContext(), " "+arg1, 0).show();
//				return false;
//			}
//		});
		
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.addTab(mActionBar.newTab().setText("娱乐")  
				.setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText("科技")  
                .setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText("体育")  
                .setTabListener(this));  

        listview.setAdapter(new SimpleAdapter(this, getData(),
                android.R.layout.simple_list_item_1, new String[] { "title" },
                new int[] { android.R.id.text1 }));
        listview.setTextFilterEnabled(true);
    }

    private void setViewEvent() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String, Object> map = (Map<String, Object>)parent.getItemAtPosition(position);
		        Intent intent = (Intent) map.get("intent");
		        startActivity(intent);
			}
		});
	}

	protected List<Map<String, Object>> getData() {
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();
        
        addToMap(myData,"NineOldAndroids",Demos.class);
        addToMap(myData,"ViewDraerLayout_YouTuBe",DragMainActivity.class);
        addToMap(myData,"App_Msg",AppMsgActivity.class);
        addToMap(myData,"Drawer_Arrow",DrawerArrowSample.class);
        

        return myData;
    }

    private void addToMap(List<Map<String, Object>> myData,String title,Class class1) {
    	  Map<String, Object> temp = new HashMap<String, Object>();
          temp.put("title", title);
          Intent intent = new Intent(getApplicationContext(), class1);
          temp.put("intent", intent);
          myData.add(temp);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return false;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

}
