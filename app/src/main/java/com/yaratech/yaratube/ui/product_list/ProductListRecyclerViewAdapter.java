package com.yaratech.yaratube.ui.product_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.ViewHolder> {

    private List<Product> products = new ArrayList<>();
    private Context context;

    // data is passed into the constructor
    public ProductListRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,
                parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(products.get(position).getAvatar().getXxxdpi()).into(holder.productAvatar);
        holder.productTitle.setText(products.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView productAvatar;
        TextView productTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            productAvatar = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
        }
    }
}
