package com.example.mahmoud_ashraf.turismoapp.activities;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud_ashraf.turismoapp.R;
import com.example.mahmoud_ashraf.turismoapp.async.Connector;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.R.attr.fragment;
import static android.location.GpsStatus.GPS_EVENT_STARTED;
import static android.location.GpsStatus.GPS_EVENT_STOPPED;

public class HomeMapsActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        NavigationView.OnNavigationItemSelectedListener {


    private GoogleMap mMap;

    Connector connector;

    private EditText postEditText;
    FloatingActionButton fab;
    Context context;

    ArrayList<String> results;
    String data = "no posts until now!";
    MarkerOptions currentUserLocation;
    String longitude, latitude;

    //preferences to save data
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String  EMAIL="", PASSWORD="", TYPE="", ID="", TOKKEN="";



    // this to define my location
    private GoogleApiClient googleApiClient;

    private TextView nameOnBox;
    private TextView typeOnBox;

    // to init multiDex
    @Override
    public void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        context = this;

        // Retrieve Date from Shared Preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        EMAIL = preferences.getString("email","");
        PASSWORD = preferences.getString("password","");
        TYPE = preferences.getString("type","");
        ID = preferences.getString("user_id","");
        TOKKEN = preferences.getString("TOKEN","");


        // views on box
        nameOnBox = (TextView)findViewById(R.id.nameonbox);
        typeOnBox = (TextView)findViewById(R.id.typeonbox);

        // init views
        nameOnBox.setText(EMAIL);
        typeOnBox.setText(TYPE);



        // fab
        fab = (FloatingActionButton) findViewById(R.id.fab);


        postEditText = (EditText) findViewById(R.id.postEdittxt);


        // hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);





        // nav
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);







        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) headerView.findViewById(R.id.name_txtv);
        nav_user.setText(EMAIL);
        TextView nav_TYPE = (TextView) headerView.findViewById(R.id.textView_type);
        if(TYPE.equals("carOwner")){
            nav_TYPE.setText("Car Owner");
        }
         else if(TYPE.equals("tourist")){
            nav_TYPE.setText("Tourist");
        }
        else {
            nav_TYPE.setText("Tour Guide");
        }

        // add options programmatically
        Menu m = navigationView.getMenu();

        if(TYPE.equals("carOwner")){
           m.add("Tourist");
            m.add("Tour Guide");
            m.add("Log out!");

            // *************************************************************************************
            MenuItem mi = m.getItem(m.size()-1);
            mi.setTitle(mi.getTitle());
            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) { // Log Out
                    Toast.makeText(context,"Good Bye :)",Toast.LENGTH_LONG).show();
                    Intent back = new Intent(context, LoginActivity.class);
                    back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(back);
                    return false;
                }
            });
            // *************************************************************************************
            mi = m.getItem(m.size()-2);
            mi.setTitle(mi.getTitle());
            final String temp = (String) mi.getTitle();
            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                   if(temp.equals("Tour Guide")){
                      // Toast.makeText(context,"tourguide",Toast.LENGTH_LONG).show();
                       Intent intent = new Intent(context,TourGuideActivity.class);
                       intent.putExtra("type","Tour Guide");
                       startActivity(intent);
                   }
                    return false;
                }
            });
            // *************************************************************************************
            mi = m.getItem(m.size()-3);
            mi.setTitle(mi.getTitle());
            final String temp2 = (String) mi.getTitle();
            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(temp2.equals("Tourist")){
                       // Toast.makeText(context,"tourist",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,TourGuideActivity.class);
                        intent.putExtra("type","Tourist");
                        startActivity(intent);
                    }
                    return false;
                }
            });
           // **************************************************************************************
        }
        else if(TYPE.equals("tourist")){
            m.add("Tour Guide");
            m.add("Car Owner");
            m.add("Log out!");

            //***************************************************************************************
            MenuItem mi = m.getItem(m.size()-1);
            mi.setTitle(mi.getTitle());
            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) { // Log Out
                    Toast.makeText(context,"Good Bye :)",Toast.LENGTH_LONG).show();
                    Intent back = new Intent(context, LoginActivity.class);
                    back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(back);
                    return false;
                }
            });
            //***************************************************************************************
            mi = m.getItem(m.size()-2);
            mi.setTitle(mi.getTitle());
            final String temp2 = (String) mi.getTitle();
            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(temp2.equals("Car Owner")){
                       // Toast.makeText(context,"Car Owner",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,TourGuideActivity.class);
                        intent.putExtra("type","Car Owner");
                        startActivity(intent);
                    }
                    return false;
                }
            });
            //**************************************************************************************
            mi = m.getItem(m.size()-3);
            mi.setTitle(mi.getTitle());
            final String temp = (String) mi.getTitle();
            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(temp.equals("Tour Guide")){
                       // Toast.makeText(context,"tourguide",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,TourGuideActivity.class);
                        intent.putExtra("type","Tour Guide");
                        startActivity(intent);
                    }
                    return false;
                }
            });
            //**************************************************************************************
        }
        else { //tour_guide
            m.add("Tourist");
            m.add("Car Owner");
            m.add("Log out!");
            //**************************************************************************************
            MenuItem mi = m.getItem(m.size()-1);
            mi.setTitle(mi.getTitle());
            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) { // Log Out
                    Toast.makeText(context,"Good Bye :)",Toast.LENGTH_LONG).show();
                    Intent back = new Intent(context, LoginActivity.class);
                    back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(back);
                    return false;
                }
            });
            //**************************************************************************************
            mi = m.getItem(m.size()-2);
            mi.setTitle(mi.getTitle());
            final String temp2 = (String) mi.getTitle();
            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(temp2.equals("Car Owner")){
                       // Toast.makeText(context,"Car Owner",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,TourGuideActivity.class);
                        intent.putExtra("type","Car Owner");
                        startActivity(intent);
                    }
                    return false;
                }
            });
            //**************************************************************************************
            mi = m.getItem(m.size()-3);
            mi.setTitle(mi.getTitle());
            final String temp3 = (String) mi.getTitle();
            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(temp3.equals("Tourist")){
                       // Toast.makeText(context,"tourist",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,TourGuideActivity.class);
                        intent.putExtra("type","Tourist");
                        startActivity(intent);
                    }
                    return false;
                }
            });
        }


        navigationView.setNavigationItemSelectedListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        //init the GoogleApiClient Object
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected void onDestroy() {
        if(mMap!=null){
            mMap.clear();
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onConnected(null);
        } else {
            Toast.makeText(HomeMapsActivity.this, "No Permissions Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
                startActivityForResult(intent, 2017);
            }
        });



        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(provider != null&& provider.length()>=1){
            Toast.makeText(this, "GPS is Enabled in your device :)", Toast.LENGTH_SHORT).show();
        }else{
            // Notify users and show settings if they want to enable GPS
            showGPSDisabledAlertToUser();
        }
    }

    // to show dialog and change the setting to enable
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("make sure GPS is Enabled in your device. Would you like to enable it ?")
                .setCancelable(false)
                //add event to left button
                .setNegativeButton("Enable",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                               startActivity(callGPSSettingIntent);
                                // close
                                finish();

                            }
                        });
        //add event to right button
        alertDialogBuilder.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent d) {
        // retrieves data from the VoiceRecognizer
        if (requestCode == 2017 && resultCode == RESULT_OK) {
            results = d.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String res = (String) results.get(0);
            data = res;
            Toast toast = Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG);
            toast.show();
            //add this post to shared pref
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                editor = preferences.edit();
                editor.putString("MessageOnMap",data);
                editor.apply();

            connector = new Connector(context);
            connector.addMemory(ID,data, latitude,longitude);
            // now we edit this message on server
           /// connector.EditInfo("message",res.toString());

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            } else {

                // Refresh
                finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);
            }

        }
        super.onActivityResult(requestCode,resultCode, d);

    }
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myProfile) {
            startActivity(new Intent(this, MyProfileActivity.class));
        } else if (id == R.id.nav_category) {
            startActivity(new Intent(this, CategoriesActivity.class));
        }

        /*else if (id == R.id.nav_logout) {
            Intent back = new Intent(this, LoginActivity.class);
            back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(back);
        }*/

        /*else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onConnectionSuspended(int i) {
        mMap.clear();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mMap.clear();
    }

    @Override
    public void onLocationChanged(Location location) {



    }


    // define location and set map elements TO HANDLE WHEN USE GPS
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {

            //using google api key to get your location
            Location userCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            // if user current not null get the wanted information
            if (userCurrentLocation != null) {
                currentUserLocation = new MarkerOptions();
                LatLng currentUserLatLang = new LatLng(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude());

                // set lat long value
                latitude = String.valueOf(currentUserLatLang.latitude);
                longitude =String.valueOf(currentUserLatLang.longitude);

                currentUserLocation.position(currentUserLatLang);

                //set img
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                data = preferences.getString("MessageOnMap", "");
                if (data != null) {
                    currentUserLocation.title("last memory: ").snippet(data);
                } else {
                    currentUserLocation.title("empty inbox: ").snippet("  ");
                }
                // add btn in keyboard
                postEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean handled = false;
                        if (actionId == EditorInfo.IME_ACTION_SEND) {

                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                            data = v.getText().toString();
                            Toast.makeText(HomeMapsActivity.this, "You Saved :) " + v.getText().toString(), Toast.LENGTH_LONG).show();
                            v.setText("");

                            connector = new Connector(context);
                            connector.addMemory(ID,data, latitude,longitude);

                            // add this post to shared pref
                            preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            editor = preferences.edit();
                            editor.putString("MessageOnMap",data);
                            editor.apply();

                            // Refresh
                            finish();
                            overridePendingTransition( 0, 0);
                            startActivity(getIntent());
                            overridePendingTransition( 0, 0);



                            handled = true;
                        }
                        return handled;
                    }
                });
                //add balloon marker
                mMap.addMarker(currentUserLocation);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentUserLatLang, 16));

                // for update
                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(5000);
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

            }
        }


    }


    // WE ALSO ADD THIS METHODS TO START CONNECT AND DISCONNECT WITH GOOGLE API
    @Override
    protected void onStart(){
        googleApiClient.connect();
        super.onStart();
    }
    @Override
    protected void onStop(){
        googleApiClient.disconnect();
        mMap.clear();
        super.onStop();
    }


}
