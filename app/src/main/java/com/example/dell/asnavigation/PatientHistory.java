package com.example.dell.asnavigation;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dell on 7/31/2016.
 */
public class PatientHistory extends ActionBarActivity implements BlankFragment.FragmentDrawerListener {
    private Toolbar mToolbar;
    MenuItem item;
    private BlankFragment drawerFragment;
    ArrayList<HashMap<String,Object>> arrayList;
    ListView listView;
    DbHelper dbHelper;
    DisplayAdapter displayAdapter;
    int arraysize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_healthhis);
        LinearLayout ll=(LinearLayout)findViewById(R.id.parentq);
        listView=(ListView)findViewById(R.id.list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (BlankFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
      //  dbHelper.onCreate();
        dbHelper=new DbHelper(this);
        dbHelper.onCreate();

        arrayList=dbHelper.getAllProfile();
        arraysize=arrayList.size();
       // Toast.makeText(PatientHistory.this,arraysize+"size",Toast.LENGTH_LONG).show();

            displayAdapter = new DisplayAdapter(this, R.layout.displayadaptertext, arrayList);
            listView.setAdapter(displayAdapter);
    }
  @Override
  public void onBackPressed()
  {
      finish();
      super.onBackPressed();
  }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
       MenuItem item;
        item=menu.findItem(R.id.addnew);
        item.setVisible(true);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent=new Intent(PatientHistory.this,AddNew.class);
                startActivity(intent);
                finish();
                return true;
            }
        });
        //   MenuItem item= (MenuItem)findViewById(R.id.addnew);
        //item.setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayview(position);
    }
    private void displayview(int position) {
        Fragment fragment=null;
        String title=getString(R.string.app_name);
        switch (position){
            case 0:
                Intent intent2=new Intent(PatientHistory.this,Druginfo.class);
                intent2.putExtra("Message","This is druginfo calculator");
                startActivity(intent2);
                break;
            case 1:
                Intent intent1=new Intent(PatientHistory.this,BmiCalculator.class);
                intent1.putExtra("Message","This is bmicalculator activity");
                startActivity(intent1);
                break;
            case 2:
                Intent intent3=new Intent(PatientHistory.this,PatientHistory.class);
                startActivity(intent3);
                break;
            case 3:
                Intent intent=new Intent(PatientHistory.this,Home.class);
                intent.putExtra("Message","This is drug activity");
                startActivity(intent);
                break;
            case 4:
                Intent aboutus=new Intent(PatientHistory.this,Aboutus.class);
                startActivity(aboutus);
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.commit();
            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}
