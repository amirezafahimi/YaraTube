package com.yaratech.yaratube.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.GlideApp;
import com.yaratech.yaratube.R;

public class HeaderFragment extends Fragment {

    ImageView imageView;

    public HeaderFragment() {
    }

    public static HeaderFragment newInstance(String url) {

        Bundle args = new Bundle();
        Log.e("taraghe", url);
        args.putString("URL", url);
        HeaderFragment fragment = new HeaderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_header, container, false);
//        Log.e("taraghe ", getArguments().getString("URL"));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        imageView = (ImageView) view.findViewById(R.id.section_label);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("TAG", "MAG" + getArguments().getString("URL"));
        GlideApp.with(getContext()).load(getArguments().getString("URL")).into(imageView);
    }
}
