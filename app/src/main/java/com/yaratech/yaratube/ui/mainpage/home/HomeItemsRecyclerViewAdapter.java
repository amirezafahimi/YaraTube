package com.yaratech.yaratube.ui.mainpage.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.ui.OnProductActionListener;

import java.util.List;


public class HomeItemsRecyclerViewAdapter extends RecyclerView.Adapter<HomeItemsRecyclerViewAdapter.ViewHolder> {

    private List<Product> products;
    private Context context;
    private OnProductActionListener mClickListener;

    // data is passed into the constructor
    HomeItemsRecyclerViewAdapter(Context context,
                                 List<com.yaratech.yaratube.data.model.Product> products,
                                 OnProductActionListener mClickListener) {
        this.context = context;
        this.products = products;
        this.mClickListener = mClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item, parent, false);
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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productAvatar;
        TextView productTitle;
        TextView productDescriptin;

        ViewHolder(View itemView) {
            super(itemView);
            productAvatar = itemView.findViewById(R.id.home_item_image);
            productTitle = itemView.findViewById(R.id.home_item_title);
            productDescriptin = itemView.findViewById(R.id.home_item_description);
        }

        public void onBind(Product product) {
            Glide.with(context).load(product.getFeatureAvatar().getXxxdpi())
                    .apply(RequestOptions.centerCropTransform())
                    .into(productAvatar);
            productTitle.setText(product.getName());
            productDescriptin.setText(product.getShortDescription());


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener
                    .goFromProductToProdutDetails(products.get(getAdapterPosition()), productAvatar);
        }
    }
}