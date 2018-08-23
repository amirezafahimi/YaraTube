package com.yaratech.yaratube.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.ui.OnProductActionListener;

public class HeaderItemsFragment extends Fragment {

    ImageView imageView;
    private Product headerItem;
    private OnProductActionListener mListener;

    public HeaderItemsFragment() {
    }

    public static HeaderItemsFragment newInstance(Product headerItem) {

        Bundle args = new Bundle();
        args.putParcelable("HEADER_ITEM", headerItem);
        HeaderItemsFragment fragment = new HeaderItemsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductActionListener) {
            mListener = (OnProductActionListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            headerItem = getArguments().getParcelable("HEADER_ITEM");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_header_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.section_label);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goFromProductToProdutDetails(headerItem);
            }
        });
        Glide.with(getContext()).load(headerItem.getFeatureAvatar().getXxxdpi()).into(imageView);
        /*imageView.setScaleX(-1);*/
    }

}
