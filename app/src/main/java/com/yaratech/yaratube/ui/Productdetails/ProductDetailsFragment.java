package com.yaratech.yaratube.ui.Productdetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.model.ProductDetail;

import java.util.List;

public class ProductDetailsFragment extends Fragment implements ProductDetailsContract.View{

    ProductDetailsPresenter productDetailsPresenter;
    ProductDetail productDetail;
    List<Comment> comments;
    TextView title;
    TextView description;
    ProgressBar progressBar;
    ProductDetailsRecyclerViewAdpter adpter;
    RecyclerView productRecyclerView;


    public ProductDetailsFragment() {
        // Required empty public constructor
    }
    public static ProductDetailsFragment newInstance(int productId) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args1 = new Bundle();
        args1.putInt("product_title", productId);
        fragment.setArguments(args1);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showListProducts(ProductDetail productDetail) {

    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void showProgrssBar() {

    }

    @Override
    public void hideProgrssBar() {

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
