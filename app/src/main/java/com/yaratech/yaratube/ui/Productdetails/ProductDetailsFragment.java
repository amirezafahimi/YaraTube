package com.yaratech.yaratube.ui.Productdetails;

import android.content.Context;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.model.ProductDetail;
import com.yaratech.yaratube.data.source.Repository;

import java.util.List;

public class ProductDetailsFragment extends Fragment implements ProductDetailsContract.View {

    ProductDetailsPresenter productDetailsPresenter;
    ProductDetail productDetail;
    List<Comment> comments;
    TextView title;
    TextView description;
    ProgressBar progressBar;
    ProductDetailsRecyclerViewAdpter adpter;
    RecyclerView productDetailsRecyclerView;


    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ProductDetailsFragment newInstance(int productId) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args1 = new Bundle();
        args1.putInt("product_id", productId);
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
        progressBar = view.findViewById(R.id.loading_product_details);
        progressBar.setVisibility(View.GONE);
        productDetailsRecyclerView = view.findViewById(R.id.product_comments);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        productDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adpter = new ProductDetailsRecyclerViewAdpter(getContext());
        productDetailsRecyclerView.setAdapter(adpter);
        productDetailsPresenter = new ProductDetailsPresenter(this, new Repository());
        productDetailsPresenter.fetchProductDetails(getArguments().getInt("product_id"));

    }

    @Override
    public void showProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public void showCommentList(List<Comment> comments) {
        this.comments = comments;
        adpter.setData(comments);

    }

    @Override
    public void showErrorMessage(String err) {
        Toast.makeText(getContext(), err, Toast.LENGTH_LONG);
    }

    @Override
    public void showProgrssBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgrssBar() {
        progressBar.setVisibility(View.GONE);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
