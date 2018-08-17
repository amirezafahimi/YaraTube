package com.yaratech.yaratube.ui.product_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.Repository;

import java.util.ArrayList;
import java.util.List;


public class ProductListFragment extends Fragment implements ProductListContract.view{

    List<Product> products = new ArrayList<>();
    ProductListPresenter productListPresenter;
    ProductListRecyclerViewAdapter adapter;
    RecyclerView productsRecyclerView;
    ProgressBar progressBar;

    public ProductListFragment() {
        // Required empty public constructor
    }


    public static ProductListFragment newInstance(int id) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt("category_id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.loading_product);
        progressBar.setVisibility(View.GONE);
        productsRecyclerView = view.findViewById(R.id.productsRecyclerView);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
                LinearLayoutManager.VERTICAL, false));
        adapter = new ProductListRecyclerViewAdapter(getContext());
        productListPresenter = new ProductListPresenter(this, new Repository());
        productListPresenter.fetchProducts(getArguments().getInt("category_id"));
        adapter.setData(products);
    }

    @Override
    public void showListProducts(List<Product> categories) {
        this.products = categories;
        Toast.makeText(getContext(), "ترکید!", Toast.LENGTH_LONG);
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getContext(), "ترکید!", Toast.LENGTH_LONG);
    }

    @Override
    public void showProgrssBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgrssBar() {
        progressBar.setVisibility(View.GONE);
    }
}
