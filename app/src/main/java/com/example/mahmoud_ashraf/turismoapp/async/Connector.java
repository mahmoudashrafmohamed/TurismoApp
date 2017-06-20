package com.example.mahmoud_ashraf.turismoapp.async;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mahmoud_ashraf.turismoapp.R;
import com.example.mahmoud_ashraf.turismoapp.activities.HomeMapsActivity;
import com.example.mahmoud_ashraf.turismoapp.activities.LoginActivity;
import com.example.mahmoud_ashraf.turismoapp.activities.MyProfileActivity;
import com.example.mahmoud_ashraf.turismoapp.activities.TourGuideActivity;
import com.example.mahmoud_ashraf.turismoapp.adapters.categories_adapter;
import com.example.mahmoud_ashraf.turismoapp.adapters.places_adapter;
import com.example.mahmoud_ashraf.turismoapp.adapters.tourguide_adapter;
import com.example.mahmoud_ashraf.turismoapp.models.Parser;
import com.example.mahmoud_ashraf.turismoapp.models.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mahmoud_ashraf on 6/11/2017.
 */

public class Connector {
    Context context;
    RecyclerView recyclerView;
    tourguide_adapter adapter;
    places_adapter placesAdapter;
    categories_adapter categoriesAdapter;

    //views for my profile
    TextView name,country,age;
    de.hdodenhof.circleimageview.CircleImageView pic;

    public Connector(Context context){
        this.context = context;
    }
    public Connector(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;

    }

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;

    public Connector(Context context, TextView name, CircleImageView pic, TextView country, TextView age) {
        this.context = context;
        this.name = name;
        this.pic = pic;
        this.country = country;
        this.age = age;
    }

    // Log In
    public void Login(final String email, final String pass) {

        final String URL = "https://turismo2017.000webhostapp.com/login/";

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        prefEditor = preferences.edit();

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Log.i("Login Service", "Response : " + response);

                // parsing
                Parser parser = new Parser();
                ArrayList<String> data = parser.ParseLoginResponse(response);
               // Log.i("Login Service", "Response After Parsing : " +data.toString());
                if (data.size()==3) {

                        prefEditor.putString("TOKEN", data.get(0));
                        prefEditor.apply();
                        prefEditor.putString("user_id",data.get(1));
                        prefEditor.apply();
                        prefEditor.putString("type",data.get(2));
                        prefEditor.apply();
                        prefEditor.putString("email", email);
                        prefEditor.apply();
                        prefEditor.putString("password", pass);
                        prefEditor.apply();



                       Intent intent = new Intent(context, HomeMapsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                    Toast.makeText(context,"Wrong e-mail or Password", Toast.LENGTH_LONG).show();

                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(context, "Check your internet connection then try again!", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Wrong Username or password!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                   Login(email,pass);
                } else {
                    Toast.makeText(context, "error happened try again!", Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("password",pass);

                return params;

            }
        };

        Volley.newRequestQueue(context).add(request);

    }

    // Register
    public void Register(final String name, final String email, final  String password, final String type){

        final String URL = "https://turismo2017.000webhostapp.com/reg/";

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        prefEditor = preferences.edit();


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Log.i("Login Service", "Response : " + response);

                // parsing
                Parser parser = new Parser();
               String dat = parser.ParseRegisterResponse(response);
                // Log.i("Login Service", "Response After Parsing : " +data.toString());
                if (dat.equals("success")) {
                    Toast.makeText(context,"Registered Successfully :)", Toast.LENGTH_LONG).show();

                    prefEditor.putString("email", email);
                    prefEditor.apply();
                    prefEditor.putString("password", password);
                    prefEditor.apply();



                    Intent intent = new Intent(context, HomeMapsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context,"Error in email or password try again", Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(context, "Check your internet connection then try again!", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Wrong Email or password!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                   Register(name,email,password,type);
                } else {
                    Toast.makeText(context, "this email is used before try another one please :)", Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",name);
                params.put("email",email);
                params.put("password",password);
                params.put("type",type);

                return params;

            }
        };

        Volley.newRequestQueue(context).add(request);

    }

    // TourGuides , Tourists and Car Owners
    public void GetTourGuides(final String URL) {

       // final String URL = "https://turismo2017.000webhostapp.com/tour_guides/";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Log.i("Login Service", "Response : " + response);

                // parsing
                Parser parser = new Parser();
                ArrayList<Users> arr = parser.ParseGetTourGuidesResponse(response);


                if (arr.size() != 0) {


                    adapter = new tourguide_adapter(arr, context);
                    //    Log.e("following Service", "arr size ::::::: "+ arr.size());
                    /// followingsViewOps.setRecyclerAdapter(adapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                   // recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(adapter);
                    // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    adapter.notifyDataSetChanged();


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(context, "Check your internet connection then try again!", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "error happened try again!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                   GetTourGuides(URL);
                } else {
                    Toast.makeText(context, "error happened try again!", Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;

            }
        };

        Volley.newRequestQueue(context).add(request);

    }

    // Places
    public void GetPlaces(final String cat_id) {

        final String URL = "https://turismo2017.000webhostapp.com/places_id/";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Log.i("Login Service", "Response : " + response);

                // parsing
                Parser parser = new Parser();
                ArrayList<Users> arr = parser.ParseGetPlacesResponse(response);


                if (arr.size() != 0) {


                    placesAdapter = new places_adapter(arr, context);
                    //    Log.e("following Service", "arr size ::::::: "+ arr.size());
                    /// followingsViewOps.setRecyclerAdapter(adapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                    // recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(placesAdapter);
                    // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    placesAdapter.notifyDataSetChanged();


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(context, "Check your internet connection then try again!", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "error happened try again!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    GetPlaces(cat_id);
                } else {
                    Toast.makeText(context, "error happened try again!", Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cat_id",cat_id);
                return params;

            }
        };

        Volley.newRequestQueue(context).add(request);

    }

    // Categories
    public void  GetCategories() {

        final String URL = "https://turismo2017.000webhostapp.com/cat/";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Log.i("Login Service", "Response : " + response);

                // parsing
                Parser parser = new Parser();
                ArrayList<Users> arr = parser.ParseCategoryResponse(response);


                if (arr.size() != 0) {


                    categoriesAdapter = new categories_adapter(arr, context);
                    //    Log.e("following Service", "arr size ::::::: "+ arr.size());
                    /// followingsViewOps.setRecyclerAdapter(adapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                     recyclerView.setLayoutManager(layoutManager);
                    //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(categoriesAdapter);
                    // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    categoriesAdapter.notifyDataSetChanged();


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(context, "Check your internet connection then try again!", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "error happened try again!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    GetCategories();
                } else {
                    Toast.makeText(context, "error happened try again!", Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;

            }
        };

        Volley.newRequestQueue(context).add(request);

    }

    // push memory on map
    public void addMemory(final String id, final String memory, final String lat, final String lon){
        //Log.e("id//"+id+"memory//"+memory,"lat//"+lat+"logit//"+lon);
        final String URL = "https://turismo2017.000webhostapp.com/memory/";




        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Log.i("Login Service", "Response : " + response);

                // parsing
                Parser parser = new Parser();
                String dat = parser.ParseAddMemoryResponse(response);
                // Log.i("Login Service", "Response After Parsing : " +data.toString());
                if (dat.equals("success")) {
                    Toast.makeText(context,"Your Memory Pushed Successfully :)", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(context,"Network Error Try Again :(", Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(context, "Check your internet connection then try again!", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Wrong Data!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                   addMemory(id,memory,lat,lon);
                } else {
                    Toast.makeText(context, "this email is used before try another one please :)", Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",id);
                params.put("memory",memory);
                params.put("lang",lon);
                params.put("lat",lat);

                return params;

            }
        };

        Volley.newRequestQueue(context).add(request);


    }

    //Fill My Profile
    public void getUserInfo(final String id, final String type){
        final String URL = "https://turismo2017.000webhostapp.com/get_user/";

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        prefEditor = preferences.edit();

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Log.i("Login Service", "Response : " + response);

                // parsing
                Parser parser = new Parser();
                Users users = new Users();



                users = parser.ParseFillProfileResponse(response);
                // Log.i("Login Service", "Response After Parsing : " +data.toString());
               /// if (data.size()==3) {

                  /*  prefEditor.putString("TOKEN", data.get(0));
                    prefEditor.apply();
                    prefEditor.putString("user_id",data.get(1));
                    prefEditor.apply();
                    prefEditor.putString("type",data.get(2));
                    prefEditor.apply();

*/
                if(users.getAge().length()>0){
                    age.setText("AGE: "+users.getAge().toUpperCase());
                }
                if(users.getCountry().length()>0){
                   country.setText("FROM: "+users.getCountry().toUpperCase());
                }
                if(users.getUsername().length()>0){
                    name.setText(users.getUsername().toUpperCase());
                }



                Log.e("objjjj ",users.getProfile_pic().toString());
                if(users.getProfile_pic().length()>0){
                  // pic.setImageResource(R.drawable.person_test);
                    Picasso.with(context)
                            //  .load("https://turismo2017.000webhostapp.com/images/images/"+encap.getProfile_pic())
                            .load("https://turismo2017.000webhostapp.com/images/images/"+users.getProfile_pic().substring(7))
                            .placeholder(R.drawable.loading)   // optional
                            .error(R.drawable.p)      // optional
                            .resize(250, 200)                        // optional
                            .into(pic);
                }

                else{
                    pic.setImageResource(R.drawable.p);

                }

                   /* Intent intent = new Intent(context, HomeMapsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);*/
                    /**
                     * Set for Views with Data ......
                     */
             ///   } else {
                  //  Toast.makeText(context,"Wrong e-mail or Password", Toast.LENGTH_LONG).show();

               /// }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(context, "Check your internet connection then try again!", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Wrong Username or password!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                   getUserInfo(id,type);
                } else {
                    Toast.makeText(context, "error happened try again!", Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",id);
                params.put("type",type);

                return params;

            }
        };

        Volley.newRequestQueue(context).add(request);

    }



}
