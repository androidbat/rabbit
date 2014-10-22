package com.m.rabbit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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

import com.example.appmsg.AppMsgActivity;
import com.example.drag.DragMainActivity;
import com.example.drawerarrowdrawable.DrawerArrowSample;
import com.example.messagebar.SampleActivity;
import com.example.styleddialog.MyActivity;
import com.jakewharton.nineoldandroids.sample.Demos;

public class MainActivity extends ActionBarActivity implements TabListener {
	private ActionBar mActionBar;
	
	private String[] mActionLiStrings={"ȫ��","������Ϣ"};

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
		mActionBar.addTab(mActionBar.newTab().setText("搞笑")  
				.setTabListener(this));  
		mActionBar.addTab(mActionBar.newTab().setText("订阅")  
				.setTabListener(this));  
		mActionBar.addTab(mActionBar.newTab().setText("天地沙龙")  
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
        addToMap(myData,"MessageBar",SampleActivity.class);
        addToMap(myData,"Styled_Dialog",MyActivity.class);
        addToMap(myData,"Nifty_Dialog_Effect",com.gitonway.lee.niftymodaldialogeffects.MainActivity.class);
        addToMap(myData,"Material_Dialog",com.example.materialdialog.MyActivity.class);
        

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
