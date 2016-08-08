package com.example.dell.asnavigation;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by dell on 8/3/2016.
 */
public class Aboutus extends ActionBarActivity implements BlankFragment.FragmentDrawerListener {
    GridView gv1;
    public static String [] prgmNameList1={"Preventive Care","Swift consumer switches","Game changing ROI","Improved outcomes"};
    public static int [] prgmImages1={R.drawable.preventive,R.drawable.swift,R.drawable.roi,R.drawable.improved};
    private Toolbar mToolbar;
    MenuItem item;
    // DbHelper dbHelper;
    private BlankFragment drawerFragment;
    TextView visit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        visit=(TextView)findViewById(R.id.abouttxt);
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.evivehealth.com/"));
                startActivity(browserIntent);
            }
        });
       /* Context context = this;*/
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (BlankFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        gv1=(GridView)findViewById(R.id.gridView11);
        gv1.setAdapter(new CustomAdaptera(this,prgmNameList1,prgmImages1));
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
            Intent intent2=new Intent(Aboutus.this,Druginfo.class);
            intent2.putExtra("Message","This is druginfo calculator");
            startActivity(intent2);
            break;
        case 1:
            Intent intent1=new Intent(Aboutus.this,BmiCalculator.class);
            intent1.putExtra("Message","This is bmicalculator activity");
            startActivity(intent1);
            break;
        case 2:
            Intent intent3=new Intent(Aboutus.this,PatientHistory.class);
            startActivity(intent3);
            break;
        case 3:
            Intent intent=new Intent(Aboutus.this,Home.class);
            intent.putExtra("Message","This is drug activity");
            startActivity(intent);
            break;
        case 4:
            Intent aboutus=new Intent(Aboutus.this,Aboutus.class);
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
