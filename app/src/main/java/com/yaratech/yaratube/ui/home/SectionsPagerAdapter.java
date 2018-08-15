package com.yaratech.yaratube.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yaratech.yaratube.data.model.HeaderItem;

import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private List<HeaderItem> headerItemList = new ArrayList<>();
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return HeaderFragment.newInstance(headerItemList.get(position).getFeatureAvatar().getXxxdpi());}

    @Override
    public int getCount() {
        return headerItemList.size();
    }

    public void setData(List<HeaderItem> headeritems) {
        headerItemList = headeritems;
        notifyDataSetChanged();
    }
}
