package com.yaratech.yaratube;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yaratech.yaratube.ui.categories.CategoriesFragment;
import com.yaratech.yaratube.ui.home.HomeFragment;

public class MainPageFragment extends Fragment {

    public MainPageFragment() {
        // Required empty public constructor
    }

    public static MainPageFragment newInstance() {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFragment(HomeFragment.newInstance());
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:

                                setFragment(HomeFragment.newInstance());
                                break;
                            case R.id.navigation_category:
                                setFragment(CategoriesFragment.newInstance());
                                break;
                        }
                        return false;
                    }
                });
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeFragmentContainer, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}
