package com.example.dell.asnavigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements BlankFragment.FragmentDrawerListener,LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    private static String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;
    MenuItem item;
   // DbHelper dbHelper;
    private BlankFragment drawerFragment;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String phno ="phno";
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    SharedPreferences sharedPreferences;
    final static private String PREF_KEY_SHORTCUT_ADDED = "PREF_KEY_SHORTCUT_ADDED";

    public String phnum="mummy";
final Context context = this;
EditText result;
    GridView gv;
    Button emergency;
    ArrayList prgmName;
    public static String [] prgmNameList={"Nearby Hospitals","BMI Calculator","Patient History","Medicine Remainder"};
    public static int [] prgmImages={R.drawable.nearby,R.drawable.bmi,R.drawable.patienthistory,R.drawable.remainder};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createShortcutIcon();
        //Toast.makeText(MainActivity.this,"Shortcut icon created",Toast.LENGTH_LONG).show();
        Context context=this;
        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        result=(EditText)findViewById(R.id.editTextResult);
result.setVisibility(View.INVISIBLE);

emergency=(Button)findViewById(R.id.emer);
        emergency.setVisibility(View.VISIBLE);
emergency.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        phnum=result.getText().toString();

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(phno,phnum);
        editor.commit();

    //    Toast.makeText(MainActivity.this,phnum,Toast.LENGTH_LONG).show();
        if(!phnum.equalsIgnoreCase("mummy")) {
            Intent timer = new Intent(MainActivity.this, Timer.class);

            startActivity(timer);
        }
        else {
            Toast.makeText(MainActivity.this,"First Add Number",Toast.LENGTH_LONG).show();
        }
    }
});
         boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {


            final AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
            builder.setTitle("MyAppName");
            builder.setMessage("The location service is off. Do you want to turn it on?");
            builder.setPositiveButton("Enable location",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(
                                final DialogInterface dialogInterface,
                                final int i) {
                            startActivity(new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                        }
                    });
            builder.setNegativeButton("No", null);
            builder.create().show();
        }
if(!isOnline())
{
    showNoConnectionDialog(this);
}
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                        //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        //  dbHelper.onCreate();
       // dbHelper.insertdummy();
      //  gridView=(GridView)findViewById(R.id.grid);
        //gridView.setAdapter(new ImageAdapter(this));
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

       // item.setVisible(false);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (BlankFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
      gv=(GridView)findViewById(R.id.gridView1);
      gv.setAdapter(new CustomAdapter(this,prgmNameList,prgmImages));

    }

    public boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }
    public static void showNoConnectionDialog(Context ctx1)
    {
        final Context ctx = ctx1;
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setCancelable(true);
        builder.setMessage("Turn on Internet Connection");
        builder.setTitle("MyAPPName");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                ctx.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                return;
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            public void onCancel(DialogInterface dialog) {
                return;
            }
        });

        builder.show();
    }
    private void createShortcutIcon(){

        // Checking if ShortCut was already added
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean shortCutWasAlreadyAdded = sharedPreferences.getBoolean(PREF_KEY_SHORTCUT_ADDED, false);
        if (shortCutWasAlreadyAdded) return;

        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Evive Health");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.eviveicon));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);

        // Remembering that ShortCut was already added
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_KEY_SHORTCUT_ADDED, true);
        editor.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item=menu.findItem(R.id.addnew);
        item.setVisible(true);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);
               // final String myphno;
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                 final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);
               /* EditText userInput=(EditText)findViewById(R.id.editTextDialogUserInput);*/
             //     myphno= String.valueOf(userInput.getText());
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        result.setText(userInput.getText());
                                        /*SharedPreferences.Editor editor=sharedPreferences.edit();
                                        editor.putString(phno,phnum);
                                        editor.commit();*/
                                      //  Toast.makeText(MainActivity.this,myphno + "",Toast.LENGTH_LONG).show();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


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
            Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
          displayview(position);

    }
    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
    private void displayview(int position) {
        Fragment fragment=null;
        String title=getString(R.string.app_name);
        switch (position){
            case 0:
                Intent intent2=new Intent(MainActivity.this,Druginfo.class);
                intent2.putExtra("Message","This is druginfo calculator");
                startActivity(intent2);
                break;
            case 1:
                Intent intent1=new Intent(MainActivity.this,BmiCalculator.class);
                intent1.putExtra("Message","This is bmicalculator activity");
                startActivity(intent1);
                break;
            case 2:
                Intent intent3=new Intent(MainActivity.this,PatientHistory.class);
                startActivity(intent3);
                break;
            case 3:
                Intent intent=new Intent(MainActivity.this,Home.class);
                intent.putExtra("Message","This is drug activity");
                startActivity(intent);
                break;
            case 4:
                Intent aboutus=new Intent(MainActivity.this,Aboutus.class);
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

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            Toast.makeText(MainActivity.this,currentLatitude+"onconnected",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
Toast.makeText(MainActivity.this,currentLatitude+"",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}