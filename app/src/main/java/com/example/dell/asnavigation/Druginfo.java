package com.example.dell.asnavigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;
import android.location.LocationListener;
import android.location.LocationManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


/**
 * Created by dell on 7/30/2016.
 */
public class Druginfo extends ActionBarActivity implements LocationListener,GoogleApiClient.ConnectionCallbacks,OnConnectionFailedListener{
    private GoogleMap googleMap;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static String scurrentlatitute="scurrentlatitude";
    public static String scurrentlongitude="scurrentlongitude";
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final String GOOGLE_API_KEY = "AIzaSyDg_fOrjnRKY5AFIxMrmkvCivWM1PNtgxQ";
    private int PROXIMITY_RADIUS = 1000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    private String[] placeName;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.druginfo);
        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        Toast.makeText(Druginfo.this,"Hai",Toast.LENGTH_LONG).show();

        /*googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map)).getMap();*/

       /* googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);*/

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                        .addOnConnectionFailedListener(this)
                        //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(1 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(10 * 1000); // 1 second, in milliseconds

        mGoogleApiClient.connect();


        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

       /* if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            initilizeMap();}*/
             //    initilizeMap();

    }

    /**
     * function to load map. If map is not created it will create it for you
     * */

    /*@Override
    public void onLocationChanged(Location location)
    {

    }
    */@Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
        mGoogleApiClient.connect();
    }
    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
    }
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        //Toast.makeText(this,"Inside on laocation change",Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
     //   Log.e("Connected?", String.valueOf(mGoogleApiClient.isConnected()));
        //new Thread(new GetContent()).start();
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
    public void onConnected(Bundle bundle) {
        try {


            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

            } else {
                //If everything went fine lets get latitude and longitude
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();

                String dcurrentlongitude = "" + currentLongitude;
                String dcurrentlatitute = "" + currentLatitude;
                //    Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(scurrentlatitute, dcurrentlatitute);
                editor.putString(scurrentlongitude, dcurrentlongitude);
                editor.commit();
                initilizeMap();
                LatLng coordinate = new LatLng(currentLatitude, currentLongitude);
                CameraUpdate yourloc = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
                googleMap.animateCamera(yourloc);
                Marker marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Your location"));
                marker.setVisible(true);
                String type = "hospital";
                StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                googlePlacesUrl.append("location=" + currentLatitude + "," + currentLongitude);
                googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
                googlePlacesUrl.append("&types=" + type);
                googlePlacesUrl.append("&sensor=true");
                googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);
                GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
                Object[] toPass = new Object[2];
                toPass[0] = googleMap;
                toPass[1] = googlePlacesUrl.toString();
                googlePlacesReadTask.execute(toPass);

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void initilizeMap() {
//mGoogleApiClient.connect();

        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            Location location;
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getMyLocation();
        /*    PlacesService service = new PlacesService("AIzaSyDg_fOrjnRKY5AFIxMrmkvCivWM1PNtgxQ");
            List<Place> findPlaces = service.findPlaces(currentLatitude,currentLongitude,"hospital");  // hospiral for hospital
            // atm for ATM

            placeName = new String[findPlaces.size()];
            for (int i = 0; i < findPlaces.size(); i++) {

                Place placeDetail = findPlaces.get(i);
                placeDetail.getIcon();

                System.out.println(  placeDetail.getName());
                placeName[i] =placeDetail.getName();
                Toast.makeText(Druginfo.this,placeName[i],Toast.LENGTH_LONG).show();


            }
        */    if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }

        }
        else{

            //Toast.makeText(this,""+googleMap.getMyLocation(),Toast.LENGTH_LONG).show();


        }
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}
