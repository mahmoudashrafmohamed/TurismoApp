package com.example.mahmoud_ashraf.turismoapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mahmoud_ashraf.turismoapp.R;
import com.example.mahmoud_ashraf.turismoapp.activities.PlaceDetailsActivity;
import com.example.mahmoud_ashraf.turismoapp.models.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mahmoud_ashraf on 6/12/2017.
 */

public class places_adapter extends RecyclerView.Adapter<places_adapter.ViewHolder> implements View.OnClickListener{

    // let the user here is tour guide
    ArrayList<Users> arrayList;
    Context context;

    Users encap = new Users();

    public places_adapter(ArrayList<Users> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }
    @Override
    public places_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View Layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_row_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(Layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(places_adapter.ViewHolder holder, int position) {

        encap = arrayList.get(position);

        holder.cardView.setTag(position);


      //  holder.image.setImageResource(R.drawable.test_place);

        /*

        More Alternatives you can used it from picaso library
         */

        Picasso.with(context)
              //  .load("https://turismo2017.000webhostapp.com/images/images/"+encap.getProfile_pic())
                .load("https://turismo2017.000webhostapp.com/images/images/"+encap.getProfile_pic().substring(7))
                .placeholder(R.drawable.loading)   // optional
                .error(R.drawable.nophoto)      // optional
                .resize(250, 200)                        // optional
                .into(holder.image);


        Log.e("website////","https://turismo2017.000webhostapp.com/images/"+encap.getProfile_pic().substring(7));

        holder.textName.setText(encap.getUsername());

    }

    @Override
    public int getItemCount() {
        return  arrayList.size();
    }

    @Override
    public void onClick(View view) {

    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        ImageView image;
        TextView textName;



        public ViewHolder(View layout) {
            super(layout);

            cardView = (CardView) layout.findViewById(R.id.list_row_container);
            image = (ImageView) layout.findViewById(R.id.place_image);
            textName = (TextView) layout.findViewById(R.id.place_name);


            cardView.setOnClickListener(this);
            image.setOnClickListener(this);
            textName.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

          switch (view.getId()) {

              case R.id.list_row_container:
                    Intent intent = new Intent(context, PlaceDetailsActivity.class);
                  intent.putExtra("desc",arrayList.get(getAdapterPosition()).getDescription());
                  intent.putExtra("img","https://turismo2017.000webhostapp.com/images/images/"+arrayList.get(getAdapterPosition()).getProfile_pic().substring(7));

                    context.startActivity(intent);
                    break;
              case R.id.place_image:
                intent = new Intent(context, PlaceDetailsActivity.class);
                  intent.putExtra("desc",arrayList.get(getAdapterPosition()).getDescription());
                  intent.putExtra("img","https://turismo2017.000webhostapp.com/images/images/"+arrayList.get(getAdapterPosition()).getProfile_pic().substring(7));

                  context.startActivity(intent);
                  break;
              case R.id.place_name:
                  intent = new Intent(context, PlaceDetailsActivity.class);
                  intent.putExtra("desc",arrayList.get(getAdapterPosition()).getDescription());
                  intent.putExtra("img","https://turismo2017.000webhostapp.com/images/images/"+arrayList.get(getAdapterPosition()).getProfile_pic().substring(7));

                  context.startActivity(intent);
                  break;





            }
        }

    }
}
