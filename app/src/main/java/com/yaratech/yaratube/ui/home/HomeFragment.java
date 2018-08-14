package com.yaratech.yaratube.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.model.Home;


public class HomeFragment extends Fragment implements HomeContract.View{

    RecyclerView homeRecyclerView;
    Home home;
    ProgressBar progressBar;
    HomePresenter homePresenter;
    //UserPresenterInterface userPresenterInterface;
    HomeRecyclerViewAdapter adapter;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.loading);
        progressBar.setVisibility(View.GONE);
        homeRecyclerView = view.findViewById(R.id.homeRecyclerView);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homePresenter = new HomePresenter(this, new Repository());
        homePresenter.fetchHome();
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomeRecyclerViewAdapter(getContext());
        homeRecyclerView.setAdapter(adapter);

    }

    @Override
    public void showListHome(Home home) {
        this.home=home;
        adapter.setData(home);
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getContext(),  "ترکید!" ,Toast.LENGTH_LONG);
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
