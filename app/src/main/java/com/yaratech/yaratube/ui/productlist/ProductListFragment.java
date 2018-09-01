package com.yaratech.yaratube.ui.productlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.ui.OnProductActionListener;

import java.util.ArrayList;
import java.util.List;


public class ProductListFragment extends Fragment implements ProductListContract.View,
        ProductListRecyclerViewAdapter.ProductClickListener {

    ProductListPresenter productListPresenter;
    List<Product> products = new ArrayList<>();
    ProductListRecyclerViewAdapter adapter;
    RecyclerView productsRecyclerView;
    ProgressBar progressBar;


    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int offset = 0;


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
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        productListPresenter = new ProductListPresenter(this, new Repository());
        productListPresenter.fetchProducts(getArguments().getInt("category_id"), offset);

        productsRecyclerView.setLayoutManager(layoutManager);

        adapter = new ProductListRecyclerViewAdapter(getContext(), ProductListFragment.this);
        adapter.setHasStableIds(true);
        productsRecyclerView.setAdapter(adapter);
        productsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            productListPresenter.fetchProducts(getArguments().getInt("category_id"), offset);
                        }
                    }
                }
            }
        });

    }

    @Override
    public void showListProducts(List<Product> products) {
        if (products.size() != 0) {
            this.products = products;
            adapter.updateData(products);
            offset += products.size();
        }
        loading = true;
    }

    @Override
    public void showErrorMessage(String err) {
        Toast.makeText(getContext(), err, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgrssBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgrssBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View view, Product product) {
        ((OnProductActionListener) getContext()).goFromProductToProdutDetails(product);
    }
}
