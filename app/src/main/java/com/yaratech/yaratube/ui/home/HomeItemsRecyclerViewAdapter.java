package com.yaratech.yaratube.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.ui.categories.CategoryItemsRecyclerViewAdapter;

import java.util.List;


public class HomeItemsRecyclerViewAdapter extends RecyclerView.Adapter<HomeItemsRecyclerViewAdapter.ViewHolder> {

    private List<Product> products;
    private Context context;
    private HomeItemClickListener mClickListener;

    // data is passed into the constructor
    HomeItemsRecyclerViewAdapter(Context context, List<com.yaratech.yaratube.data.model.Product> products, HomeItemClickListener mClickListener) {
        this.context = context;
        this.products = products;
        this.mClickListener = mClickListener;
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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView productAvatar;
        TextView productTitle;
        TextView productDescriptin;

        ViewHolder(View itemView) {
            super(itemView);
            productAvatar = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productDescriptin = itemView.findViewById(R.id.description_product);
        }

        public void onBind(Product product) {
            Glide.with(context).load(product.getFeatureAvatar().getXxxdpi()).into(productAvatar);
            productTitle.setText(product.getName());
            productDescriptin.setText(product.getShortDescription());


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, products.get(getAdapterPosition()));
        }
    }


    // parent activity will implement this method to respond to click events
    public interface HomeItemClickListener {
        void onItemClick(View view, Product product);
    }
}