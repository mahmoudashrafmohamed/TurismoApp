package com.example.mahmoud_ashraf.turismoapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud_ashraf.turismoapp.R;
import com.example.mahmoud_ashraf.turismoapp.activities.PlacesActivity;
import com.example.mahmoud_ashraf.turismoapp.models.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mahmoud_ashraf on 6/12/2017.
 */

public class categories_adapter extends RecyclerView.Adapter<categories_adapter.ViewHolder> implements View.OnClickListener{

    // let the user here is tour guide
    ArrayList<Users> arrayList;
    Context context;

    Users encap = new Users();

    public categories_adapter(ArrayList<Users> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }
    @Override
    public categories_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View Layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_row_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(Layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(categories_adapter.ViewHolder holder, int position) {

        encap = arrayList.get(position);

        holder.cardView.setTag(position);


      ////  holder.image.setImageResource(R.drawable.test_place);
        holder.textName.setText(encap.getUsername());

        Picasso.with(context)
                //  .load("https://turismo2017.000webhostapp.com/images/images/"+encap.getProfile_pic())
                .load("https://turismo2017.000webhostapp.com/images/images/"+encap.getProfile_pic().substring(7))
                .placeholder(R.drawable.loading)   // optional
                .error(R.drawable.no_cat)      // optional
                .resize(250, 200)                        // optional
                .into(holder.image);

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
            Users temp;
           switch (view.getId()) {

               case R.id.place_name:
                   Toast.makeText(context, "Loading ... ", Toast.LENGTH_SHORT).show();
                  temp = arrayList.get(getAdapterPosition());
                   String id = temp.getTourGuide_id();
                  // Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(context, PlacesActivity.class);
                   intent.putExtra("id",id);
                   context.startActivity(intent);
                    break;

               /* case R.id.image_item_followers:
                    intent1 = new Intent(followerActivity, UserProfileActivity.class);
                    followerActivity.startActivity(intent1);
                    break;*/



            }
        }

    }
}
