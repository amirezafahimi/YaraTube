package com.yaratech.yaratube.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Product;

import java.util.List;


public class HomeItemsRecyclerViewAdapter extends RecyclerView.Adapter<HomeItemsRecyclerViewAdapter.ViewHolder> {

    private List<Product> products;
    private Context context;

    // data is passed into the constructor
    HomeItemsRecyclerViewAdapter(Context context, List<com.yaratech.yaratube.data.model.Product> products) {
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
        holder.onBind(products.get(position));
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

        public void onBind(Product product) {
            Glide.with(context).load(product.getFeatureAvatar().getXxxdpi()).into(product_avatar);
            product_title.setText(product.getName());

        }
    }

}