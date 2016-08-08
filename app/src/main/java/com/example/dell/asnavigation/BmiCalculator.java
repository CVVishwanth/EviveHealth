package com.example.dell.asnavigation;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import com.example.dell.asnavigation.R;
import android.widget.EditText;
import com.example.dell.asnavigation.FloatingHintEditText;
import android.widget.Toast;

/**
 * Created by dell on 7/30/2016.
 */
public class BmiCalculator extends ActionBarActivity implements BlankFragment.FragmentDrawerListener {
    private Toolbar mToolbar;
    MenuItem item;
    private BlankFragment drawerFragment;
    FloatingHintEditText weight,height;
    String h,w;
    Button ok;
    float he,we;
    double heightinmetre,bmi;
    protected void onCreate(Bundle savedInstanceState){
          super.onCreate(savedInstanceState);
          setContentView(R.layout.bmicalculator);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (BlankFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
         weight=(FloatingHintEditText)findViewById(R.id.weight);
         height=(FloatingHintEditText)findViewById(R.id.height);
        ok=(Button)findViewById(R.id.buttonok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                w=weight.getText().toString();
                h=height.getText().toString();
                he=Integer.parseInt(h);
                we=Integer.parseInt(w);

                if(w!=null&&h!=null) {
                    if (we < 160 && he < 200) {
                        heightinmetre = he / 100;
                        heightinmetre = heightinmetre * heightinmetre;
                        bmi = we / heightinmetre;
                        if (bmi < 18.5) {
                            Toast.makeText(BmiCalculator.this, "Underweight", Toast.LENGTH_LONG).show();
                        } else if (bmi >= 18.5 && bmi <= 24.9) {
                            Toast.makeText(BmiCalculator.this, "Normal weight", Toast.LENGTH_LONG).show();
                        } else if (bmi >= 25 && bmi <= 29.9) {
                            Toast.makeText(BmiCalculator.this, "OverWeight", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(BmiCalculator.this, "Obese", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(BmiCalculator.this, "Enter proper details", Toast.LENGTH_LONG).show();
                        weight.setError("Enter proper weight");
                        height.setError("Enter Proper height");
                    }
                }
                /*else {
                    weight.setError("Enter proper weight");
                    height.setError("Enter Proper height");
                }*/
                }
        });


    }
    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
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
                    Intent intent2=new Intent(BmiCalculator.this,Druginfo.class);
                    intent2.putExtra("Message","This is druginfo calculator");
                    startActivity(intent2);
                    break;
                case 1:
                    Intent intent1=new Intent(BmiCalculator.this,BmiCalculator.class);
                    intent1.putExtra("Message","This is bmicalculator activity");
                    startActivity(intent1);
                    break;
                case 2:
                    Intent intent3=new Intent(BmiCalculator.this,PatientHistory.class);
                    startActivity(intent3);
                    break;
                case 3:
                    Intent intent=new Intent(BmiCalculator.this,Home.class);
                    intent.putExtra("Message","This is drug activity");
                    startActivity(intent);
                    break;
                case 4:
                    Intent aboutus=new Intent(BmiCalculator.this,Aboutus.class);
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
