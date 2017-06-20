package com.example.mahmoud_ashraf.turismoapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mahmoud_ashraf.turismoapp.R;
import com.example.mahmoud_ashraf.turismoapp.async.Connector;

public class RegisterationAsActivity extends AppCompatActivity implements View.OnClickListener{

    TextView back;

    Button tourist,tourguide,carowner;

    String Name="",Email="",Password="",Type="";

    Connector con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_as);

        back = (TextView)findViewById(R.id.textView_back);
        back.setOnClickListener(this);

        tourist = (Button)findViewById(R.id.touristbtn);
        tourguide = (Button)findViewById(R.id.tourguidebtn);
        carowner = (Button)findViewById(R.id.carownerbtn);

        tourist.setOnClickListener(this);
        tourguide.setOnClickListener(this);
        carowner.setOnClickListener(this);



        Bundle b = getIntent().getExtras();
        Name = b.getString("name");
        Email= b.getString("email");
        Password = b.getString("password");
        Log.e("-///////",Name+"///"+Email+"///"+Password+"///");

        con = new Connector(this);


    }

    @Override
    public void onClick(View view) {
        if( view.getId() == back.getId() ) {
            finish();
        }
        else if (view.getId() == tourist.getId()){
            Type = "tourist";
            con.Register(Name,Email,Password,Type);
        }
        else if (view.getId() == tourguide.getId()){
            Type = "tour_guide";
            con.Register(Name,Email,Password,Type);
        }
        else if (view.getId() == carowner.getId()){
            Type = "car_owner";
            con.Register(Name,Email,Password,Type);
        }

    }
}
