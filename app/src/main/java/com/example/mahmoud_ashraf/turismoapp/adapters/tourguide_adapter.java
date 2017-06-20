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

import com.example.mahmoud_ashraf.turismoapp.R;
import com.example.mahmoud_ashraf.turismoapp.activities.PlaceDetailsActivity;
import com.example.mahmoud_ashraf.turismoapp.activities.TourGuideActivity;
import com.example.mahmoud_ashraf.turismoapp.models.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mahmoud_ashraf on 6/12/2017.
 */

public class tourguide_adapter extends RecyclerView.Adapter<tourguide_adapter.ViewHolder> implements View.OnClickListener{

    // let the user here is tour guide
    ArrayList<Users> arrayList;
    Context context;

    Users encap = new Users();

    public tourguide_adapter(ArrayList<Users> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }
    @Override
    public tourguide_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View Layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.tourguide_row_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(Layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(tourguide_adapter.ViewHolder holder, int position) {

        encap = arrayList.get(position);

        holder.cardView.setTag(position);


       // holder.image.setImageResource(R.drawable.person_test);
        holder.textName.setText(encap.getUsername());
        holder.textLanguage.setText(encap.getLanguage());



if(encap.getProfile_pic().length()>7){
            Picasso.with(context)
                    //  .load("https://turismo2017.000webhostapp.com/images/images/"+encap.getProfile_pic())
                    .load("https://turismo2017.000webhostapp.com/images/images/" + encap.getProfile_pic().substring(7))
                    .placeholder(R.drawable.loading)   // optional
                    .error(R.drawable.p)      // optional
                    .resize(250, 200)                        // optional
                    .into(holder.image);
        }
        else {
    holder.image.setImageResource(R.drawable.p);


}


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
        TextView textLanguage;


        public ViewHolder(View layout) {
            super(layout);

            cardView = (CardView) layout.findViewById(R.id.list_row_container);
            image = (ImageView) layout.findViewById(R.id.tourguide_image);
            textName = (TextView) layout.findViewById(R.id.tourguide_name);
            textLanguage = (TextView)layout.findViewById(R.id.tourguide_languages);

            cardView.setOnClickListener(this);
            image.setOnClickListener(this);
            textName.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.list_row_container:
                    Intent intent = new Intent(context, PlaceDetailsActivity.class);
                    intent.putExtra("desc","NAME: "+arrayList.get(getAdapterPosition()).getUsername()+
                            "\n\nLANGUAGE: "+arrayList.get(getAdapterPosition()).getLanguage());
                    intent.putExtra("img","https://turismo2017.000webhostapp.com/images/images/"+arrayList.get(getAdapterPosition()).getProfile_pic().substring(7));

                    intent.putExtra("f","true");
                    context.startActivity(intent);
                    break;
                case R.id.tourguide_image:
                intent = new Intent(context, PlaceDetailsActivity.class);
                    intent.putExtra("desc","NAME: "+arrayList.get(getAdapterPosition()).getUsername()+
                            "\n\nLANGUAGE: "+arrayList.get(getAdapterPosition()).getLanguage());
                    intent.putExtra("img","https://turismo2017.000webhostapp.com/images/images/"+arrayList.get(getAdapterPosition()).getProfile_pic().substring(7));
                    intent.putExtra("f","true");
                    context.startActivity(intent);
                    break;
                case R.id.tourguide_name:
                 intent = new Intent(context, PlaceDetailsActivity.class);
                    intent.putExtra("desc","NAME: "+arrayList.get(getAdapterPosition()).getUsername()+
                            "\n\nLANGUAGE: "+arrayList.get(getAdapterPosition()).getLanguage());
                    intent.putExtra("img","https://turismo2017.000webhostapp.com/images/images/"+arrayList.get(getAdapterPosition()).getProfile_pic().substring(7));
                    intent.putExtra("f","true");
                    context.startActivity(intent);
                    break;





            }
        }

    }
}
