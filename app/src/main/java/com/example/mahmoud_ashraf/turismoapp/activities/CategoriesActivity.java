package com.example.mahmoud_ashraf.turismoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud_ashraf.turismoapp.R;
import com.example.mahmoud_ashraf.turismoapp.adapters.categories_adapter;
import com.example.mahmoud_ashraf.turismoapp.adapters.places_adapter;
import com.example.mahmoud_ashraf.turismoapp.async.Connector;

public class CategoriesActivity extends AppCompatActivity {

    SharedPreferences preferences;

    String  EMAIL="", PASSWORD="", TYPE="",TYPE_temp="", ID="", TOKKEN="";

    Context context;

    //recycler
    private RecyclerView recyclerView;

    //adapter
    private categories_adapter adapter;

    //async
    Connector con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_nav);

        context = this;


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Retrieve Date from Shared Preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        EMAIL = preferences.getString("email","");
        PASSWORD = preferences.getString("password","");
        TYPE = preferences.getString("type","");
        ID = preferences.getString("user_id","");
        TOKKEN = preferences.getString("TOKEN","");




           /*
        * navvvv
        *
        * */


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // drawer.setDrawerListener(toggle);
        //  toggle.syncState();
        // toolbar.setNavigationIcon(null);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        TextView nav_user =(TextView) headerView.findViewById(R.id.name_txtv);
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






        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                 int id = item.getItemId();
                if (id == R.id.nav_myProfile) {
                    startActivity(new Intent(context, MyProfileActivity.class));
                }
                else if(id == R.id.nav_home){
                    startActivity(new Intent(context, HomeMapsActivity.class));
                }

              /*  if (id == R.id.nav_my_profile) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    // Toast.makeText(getApplicationContext(), "hhhhhh", Toast.LENGTH_LONG).show();
                }
                else if (id == R.id.nav_notification) {
                    startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                } else if (id == R.id.nav_requests) {

                    startActivity(new Intent(getApplicationContext(), RequestsActivity.class));

                } else if (id == R.id.nav_Log_out) {

                    c.LogOut();
                }*/
                return true;
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        con = new Connector(this,recyclerView);
        con.GetCategories();

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
}
