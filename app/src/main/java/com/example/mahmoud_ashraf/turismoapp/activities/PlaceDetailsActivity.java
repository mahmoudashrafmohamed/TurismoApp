package com.example.mahmoud_ashraf.turismoapp.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mahmoud_ashraf.turismoapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static com.squareup.picasso.Picasso.*;

public class PlaceDetailsActivity extends AppCompatActivity {

    String desc="",img="",f="";

    TextView descTextView;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        descTextView = (TextView)findViewById(R.id.desc_txtv);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);


        Bundle b = getIntent().getExtras();
        desc = b.getString("desc");
        img = b.getString("img");
        f = b.getString("f");

        if(f!=null&&f.equals("true")){
            desc+="\n\nAGE: 25\n\nPHONE: +201023551000\n\nVerified\n\n";
        }
        descTextView.setText(desc);
        with(this).load(img)
                .into(new Target(){

                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            collapsingToolbarLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
                        }
                    }

                    @Override
                    public void onBitmapFailed(final Drawable errorDrawable) {
                        Log.d("TAG", "FAILED");
                    }

                    @Override
                    public void onPrepareLoad(final Drawable placeHolderDrawable) {
                        Log.d("TAG", "Prepare Load");
                    }
                });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Thanks :)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
