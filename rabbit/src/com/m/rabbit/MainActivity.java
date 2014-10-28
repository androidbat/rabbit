package com.m.rabbit;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import com.todddavies.components.progressbar.ProgressBar;

public class MainActivity extends ActionBarActivity implements TabListener {
	private ActionBar mActionBar;

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
		
		String path = getIntent().getStringExtra("com.m.Path");
        
        if (path == null) {
            path = "";
        }
		
//		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		mActionBar.addTab(mActionBar.newTab().setText("娱乐")  
//				.setTabListener(this));
//		mActionBar.addTab(mActionBar.newTab().setText("科技")  
//                .setTabListener(this));
//		mActionBar.addTab(mActionBar.newTab().setText("体育")  
//                .setTabListener(this));  
//		mActionBar.addTab(mActionBar.newTab().setText("搞笑")  
//				.setTabListener(this));  
//		mActionBar.addTab(mActionBar.newTab().setText("订阅")  
//				.setTabListener(this));  
//		mActionBar.addTab(mActionBar.newTab().setText("天地沙龙")  
//				.setTabListener(this));  

        listview.setAdapter(new SimpleAdapter(this, getData(path),
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
		
	}
	
	protected List<Map<String, Object>> getData(String prefix) {
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory("com.m.rabbit.example");

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        String[] prefixPath;
        String prefixWithSlash = prefix;
        
        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
            prefixWithSlash = prefix + "/";
        }
        
        int len = list.size();
        
        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null
                    ? labelSeq.toString()
                    : info.activityInfo.name;
            
            if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {
                
                String[] labelPath = label.split("/");

                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(myData, nextLabel, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }

        Collections.sort(myData, sDisplayNameComparator);
        
        return myData;
    }

    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
        new Comparator<Map<String, Object>>() {
        private final Collator   collator = Collator.getInstance();

        public int compare(Map<String, Object> map1, Map<String, Object> map2) {
            return collator.compare(map1.get("title"), map2.get("title"));
        }
    };

    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }
    
    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, MainActivity.class);
        result.putExtra("com.m.Path", path);
        return result;
    }

    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

}
