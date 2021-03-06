package com.yaratech.yaratube.ui.mainpage.home;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.HomeItem;
import com.yaratech.yaratube.data.model.Home;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.ui.OnProductActionListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.*;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Home home;
    SectionsPagerAdapter sectionsPagerAdapter;
    List<Product> headeritems = new ArrayList<>();
    List<HomeItem> homeitems = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;
    private OnProductActionListener mClickListener;
    private static final int HEADER_LIST_ITEM_VIEW = 1;
    private static final int HOME_ITEM_LIST_ITEM_VIEW = 2;


    // data is passed into the constructor
    public HomeRecyclerViewAdapter(Context context, FragmentManager fragmentManager, OnProductActionListener mClickListener) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.mClickListener = mClickListener;
        sectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager);
    }

    // inflates the row layout from xml when needed
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        switch (viewType) {
            case HEADER_LIST_ITEM_VIEW:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_items_list,
                        parent, false);
                return new HeaderListItemViewHolder(v);

            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_items_list,
                        parent, false);
                return new HomeListItemViewHolder(v);

        }
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {

        try {
            if (holder instanceof HeaderListItemViewHolder) {
                HeaderListItemViewHolder vh = (HeaderListItemViewHolder) holder;
                vh.bindViewHeaderList();
            } else if (holder instanceof HomeListItemViewHolder) {
                HomeListItemViewHolder vh = (HomeListItemViewHolder) holder;
                vh.bindViewHomeList(homeitems.get(position - 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return 1 + homeitems.size();

    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0:
                return HEADER_LIST_ITEM_VIEW;

            default:
                return HOME_ITEM_LIST_ITEM_VIEW;
        }

    }


    public void setData(Home home) {
        headeritems = home.getHeaderitem();
        homeitems = home.getHomeitem();
        sectionsPagerAdapter.setData(headeritems);
        notifyDataSetChanged();
    }

    private class HeaderListItemViewHolder extends RecyclerView.ViewHolder {

        ViewPager viewPager;

        public HeaderListItemViewHolder(View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.header_item_viewpager);
            viewPager.setRotationY(180);

        }

        public void bindViewHeaderList() {
            viewPager.setAdapter(sectionsPagerAdapter);
        }

    }

    private class HomeListItemViewHolder extends RecyclerView.ViewHolder {

        RecyclerView homeRecyclerView;

        TextView title_name;

        public HomeListItemViewHolder(View itemView) {
            super(itemView);
            homeRecyclerView = itemView.findViewById(R.id.home_item_recycler);
            title_name = itemView.findViewById(R.id.items_name);
        }

        public void bindViewHomeList(HomeItem homeitem) {
            homeRecyclerView.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL, true));
            SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.END);
            snapHelperStart.attachToRecyclerView(homeRecyclerView);
            HomeItemsRecyclerViewAdapter homeItemsRecyclerViewAdapter = new HomeItemsRecyclerViewAdapter(context, homeitem.getProducts(), mClickListener);
            homeRecyclerView.setAdapter(homeItemsRecyclerViewAdapter);
            title_name.setText(homeitem.getTitle());
        }
    }

}
