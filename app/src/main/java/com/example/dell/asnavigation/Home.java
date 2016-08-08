package com.example.dell.asnavigation;

import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.dell.asnavigation.FloatingHintEditText;

import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.app.AlarmManager;
import java.util.Calendar;

/**
 * Created by dell on 7/30/2016.
 */
public class Home extends ActionBarActivity implements BlankFragment.FragmentDrawerListener {
    private Toolbar mToolbar;
    MenuItem item;
    // DbHelper dbHelper;
    private BlankFragment drawerFragment;
  EditText personname,age,medicine;
   Button alert;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static Home inst;
    private TextView alarmTextView;
    int hour,min;
    String medinename,ampm,hour_s,min_s;
    DbHelper dbhelper;
    public static Home instance() {
        return inst;
    }
    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        dbhelper=new DbHelper(this);
        dbhelper.onCreateMedicine();
       // personname=(EditText)findViewById(R.id.personname);
        //age=(EditText)findViewById(R.id.age);
        medicine=(EditText)findViewById(R.id.medicinenamee);

        //alert=(Button)findViewById(R.id.alert);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        // item.setVisible(false);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (BlankFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmTextView = (TextView) findViewById(R.id.alarmText);
        ToggleButton alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);

        //set alarm
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,18);
        cal.set(Calendar.MINUTE,32);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent );
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
       // Toast.makeText(this, "Alarm SET.", Toast.LENGTH_LONG).show();



        /*alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            sendSMS("9952168508","Alert");
            }
        });*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.addnew);
        item.setIcon(R.drawable.medicine);
        item.setVisible(true);
      //  item.setIcon(R.drawable.)
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
              Intent viewremainder=new Intent(Home.this,Viewreminder.class);
                startActivity(viewremainder);

                return true;

            }
        });
        return true;
    }
    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Log.d("MyActivity", "Alarm On");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            hour=alarmTimePicker.getCurrentHour();
            min=alarmTimePicker.getCurrentMinute();
            hour_s=String.valueOf(hour);
            min_s=String.valueOf(min);
            medinename=medicine.getText().toString();

            /*Toast.makeText(getApplicationContext(),hour_s,Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),min_s,Toast.LENGTH_LONG).show();*/
          //  Toast.makeText(getApplicationContext()," "+hour,Toast.LENGTH_LONG).show();
               if(dbhelper.insertmedinevalues(medinename,hour_s,min_s)){

                //   Toast.makeText(getApplicationContext(),medinename,Toast.LENGTH_LONG).show();
                   Toast.makeText(getApplicationContext(), "Remainder successfully inserted", Toast.LENGTH_LONG).show();
               }
            Intent myIntent = new Intent(Home.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(Home.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        } else {
            Intent myIntent = new Intent(Home.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(Home.this, 0, myIntent, 0);
            //alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
            alarmManager.cancel(pendingIntent);
            setAlarmText("");
            Log.d("MyActivity", "Alarm Off");
        }
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
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
                Intent intent2=new Intent(Home.this,Druginfo.class);
                intent2.putExtra("Message","This is druginfo calculator");
                startActivity(intent2);
                break;
            case 1:
                Intent intent1=new Intent(Home.this,BmiCalculator.class);
                intent1.putExtra("Message","This is bmicalculator activity");
                startActivity(intent1);
                break;
            case 2:
                Intent intent3=new Intent(Home.this,PatientHistory.class);
                startActivity(intent3);
                break;
            case 3:
                Intent intent=new Intent(Home.this,Home.class);
                intent.putExtra("Message","This is drug activity");
                startActivity(intent);
                break;
            case 4:
                Intent aboutus=new Intent(Home.this,Aboutus.class);
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

