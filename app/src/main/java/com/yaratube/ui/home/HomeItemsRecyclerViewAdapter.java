package com.yaratube.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;

import java.util.List;


public class HomeItemsRecyclerViewAdapter extends RecyclerView.Adapter<HomeItemsRecyclerViewAdapter.ViewHolder> {

    private List<com.yaratube.data.model.Product> products;
    private Context context;

    // data is passed into the constructor
    HomeItemsRecyclerViewAdapter(Context context, List<com.yaratube.data.model.Product> products) {
        this.context = context;
        this.products = products;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String image_url = com.yaratube.util.Util.BASE_URL+products.get(position).getAvatar().getXxxdpi();
        String title = products.get(position).getName();
        Glide.with(context).load(image_url).into(holder.product_avatar);
        holder.product_title.setText(title);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return products.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView product_avatar;
        TextView product_title;

        ViewHolder(View itemView) {
            super(itemView);
            product_avatar = itemView.findViewById(R.id.product_image);
            product_title = itemView.findViewById(R.id.product_title);
        }
    }

}
