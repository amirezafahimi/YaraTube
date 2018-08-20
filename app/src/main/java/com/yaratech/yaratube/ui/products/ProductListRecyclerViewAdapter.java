package com.yaratech.yaratube.ui.products;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.ViewHolder> {

    private List<Product> products = new ArrayList<>();
    private Context context;
    private ProductClickListener mClickListener;

    // data is passed into the constructor
    public ProductListRecyclerViewAdapter(Context context, ProductClickListener mClickListener) {
        this.context = context;
        this.mClickListener=mClickListener;
    }

    public void setData(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productAvatar;
        TextView productTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            productAvatar = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
        }

        public void onBind(Product product) {
            Glide.with(context).load(product.getFeatureAvatar().getXxxdpi()).into(productAvatar);
            productTitle.setText(product.getName());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, products.get(getAdapterPosition()));
        }
    }

    public interface ProductClickListener {
        void onItemClick(View view, Product product);
    }
}
