package com.yaratech.yaratube.ui.Home;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Headeritem;
import com.yaratech.yaratube.util.Util;

import java.util.List;

public class HeaderItemsRecyclerViewAdapter extends RecyclerView.Adapter<HeaderItemsRecyclerViewAdapter.ViewHolder> {

    private List<Headeritem> headeritems;
    private Context context;

    // data is passed into the constructor
    HeaderItemsRecyclerViewAdapter(Context context, List<Headeritem> headeritems) {
        this.context = context;
        this.headeritems = headeritems;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String image_url = Util.BASE_URL + headeritems.get(position).getFeatureAvatar().getXxxdpi();
        Glide.with(context).load(image_url).into(holder.header_image);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return headeritems.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView header_image;

        ViewHolder(View itemView) {
            super(itemView);
            header_image = itemView.findViewById(R.id.header_image);
        }
    }

}