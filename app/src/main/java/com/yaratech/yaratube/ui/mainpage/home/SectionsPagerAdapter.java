package com.yaratech.yaratube.ui.mainpage.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.yaratech.yaratube.data.model.Product;

import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private List<Product> headerItemList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<Product> headeritems) {
        headerItemList = headeritems;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return HeaderItemsFragment.newInstance(headerItemList.get(position));
    }

    @Override
    public int getCount() {
        return headerItemList.size();
    }
}
