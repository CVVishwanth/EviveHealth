package com.example.dell.asnavigation;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dell on 8/6/2016.
 */
public class Viewreminder extends ActionBarActivity implements BlankFragment.FragmentDrawerListener {
    private Toolbar mToolbar;
    MenuItem item;
    private BlankFragment drawerFragment;
    ArrayList<HashMap<String, Object>> arrayList1;
    ListView listViewm;
    DbHelper dbHelper;
    DisplayAdapterm displayAdapterm;
    int arraysize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewreminder);
        listViewm = (ListView) findViewById(R.id.listm);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (BlankFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        //  dbHelper.onCreate();
        dbHelper = new DbHelper(this);
        dbHelper.onCreateMedicine();
        arrayList1 = dbHelper.getAllProfilem();
        arraysize = arrayList1.size();
        // Toast.makeText(getApplicationContext()," "+arraysize,Toast.LENGTH_LONG).show();
        displayAdapterm = new DisplayAdapterm(this, R.layout.displayadaptermedicine, arrayList1);
        listViewm.setAdapter(displayAdapterm);

    }


    @Override
    public void onDrawerItemSelected(View view, int position) {

        displayview(position);
    }

    private void displayview(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                Intent intent2 = new Intent(Viewreminder.this, Druginfo.class);
                intent2.putExtra("Message", "This is druginfo calculator");
                startActivity(intent2);
                break;
            case 1:
                Intent intent1 = new Intent(Viewreminder.this, BmiCalculator.class);
                intent1.putExtra("Message", "This is bmicalculator activity");
                startActivity(intent1);
                break;
            case 2:
                Intent intent3 = new Intent(Viewreminder.this, PatientHistory.class);
                startActivity(intent3);
                break;
            case 3:
                Intent intent = new Intent(Viewreminder.this, Home.class);
                intent.putExtra("Message", "This is drug activity");
                startActivity(intent);
                break;
            case 4:
                Intent aboutus = new Intent(Viewreminder.this, Aboutus.class);
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