package com.yaratech.yaratube.ui.categories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.Product;

import java.util.ArrayList;
import java.util.List;


public class CategoryItemsRecyclerViewAdapter
        extends RecyclerView.Adapter<CategoryItemsRecyclerViewAdapter.ViewHolder> {

    private List<Category> categories = new ArrayList<>();
    private Context context;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    CategoryItemsRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.onBind(categories.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return categories.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView category_avatar;
        TextView category_title;

        ViewHolder(View itemView) {
            super(itemView);
            category_avatar = itemView.findViewById(R.id.category_avatar);
            category_title = itemView.findViewById(R.id.category_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        public void onBind(Category category) {
            Glide.with(context).load(category.getAvatar().toString()).into(category_avatar);
            category_title.setText(category.getTitle());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}