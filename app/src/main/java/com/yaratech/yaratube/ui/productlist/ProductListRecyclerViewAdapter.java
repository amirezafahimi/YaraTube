package com.yaratech.yaratube.ui.productlist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
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
        this.mClickListener = mClickListener;
    }

    public void updateData(List<Product> Products) {
        List<Product> newProducts = new ArrayList<>();
        newProducts.addAll(this.products);
        newProducts.addAll(Products);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallBack(this.products, newProducts));
        products = newProducts;
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pruduct_item,
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
            Glide.with(context).load(product.getFeatureAvatar().getXxxdpi())
                    .apply(RequestOptions.centerCropTransform())
                    .into(productAvatar);
            productTitle.setText(product.getName());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, products.get(getAdapterPosition()), productAvatar);
        }
    }

    class MyDiffCallBack extends DiffUtil.Callback {


        private List<Product> oldProducts = new ArrayList<>();
        private List<Product> newProducts = new ArrayList<>();

        MyDiffCallBack(List<Product> oldProducts, List<Product> newProducts) {
            this.oldProducts = oldProducts;
            this.newProducts = newProducts;
        }


        @Override
        public int getOldListSize() {
            return oldProducts.size();
        }

        @Override
        public int getNewListSize() {
            return newProducts.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldProducts.get(oldItemPosition).getId() == newProducts.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldProducts.get(oldItemPosition).equals(newProducts.get(newItemPosition));
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }

    @Override
    public long getItemId(int position) {
        Product product = products.get(position);
        return product.getId();
    }

    public interface ProductClickListener {
        void onItemClick(View view, Product product, ImageView imageView);
    }
}
