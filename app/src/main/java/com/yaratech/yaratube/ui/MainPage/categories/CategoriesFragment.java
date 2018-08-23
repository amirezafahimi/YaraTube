package com.yaratech.yaratube.ui.MainPage.categories;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.source.Repository;

import java.util.List;

import static android.widget.GridLayout.VERTICAL;


public class CategoriesFragment extends Fragment implements CategoriesContract.view, CategoryItemsRecyclerViewAdapter.ItemClickListener{

    List<Category> categories;
    CategoriesPresenter categoriesPresenter;
    CategoryItemsRecyclerViewAdapter adapter;
    RecyclerView categoriesRecyclerView;
    ProgressBar progressBar;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.loading_category);
        progressBar.setVisibility(View.GONE);
        categoriesRecyclerView = view.findViewById(R.id.categoryRecyclerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration itemDecor = new DividerItemDecoration(categoriesRecyclerView.getContext(), VERTICAL);
        categoriesRecyclerView.addItemDecoration(itemDecor);
        adapter = new CategoryItemsRecyclerViewAdapter(getContext());
        adapter.setClickListener(CategoriesFragment.this);
        categoriesRecyclerView.setAdapter(adapter);
        categoriesPresenter = new CategoriesPresenter(this, new Repository());
        categoriesPresenter.fetchCategories();
    }

    @Override
    public void showListCategories(List<Category> categories) {
        this.categories = categories;
        adapter.setData(categories);
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



    @Override
    public void onItemClick(View view, int position) {
        ((OnCategoryFragmentActionListener) getContext()).goFromCategoryToProductList(categories.get(position));
    }


    public interface OnCategoryFragmentActionListener {
        void goFromCategoryToProductList(Category category);
    }

}
