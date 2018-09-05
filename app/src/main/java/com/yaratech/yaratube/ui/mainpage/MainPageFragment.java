package com.yaratech.yaratube.ui.mainpage;

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

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.ui.mainpage.categories.CategoriesFragment;
import com.yaratech.yaratube.ui.mainpage.home.HomeFragment;

import static com.yaratech.yaratube.ui.mainpage.home.HomeFragment.HOME_FRAGMENT_TAG;

public class MainPageFragment extends Fragment {

    FragmentManager fragmentManager;
    HomeFragment homeFragment;
    CategoriesFragment categoriesFragment;

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
        setHomeFragment();
        final BottomNavigationView bottomNavigationView = view.findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                item.setChecked(true);
                                setHomeFragment();
                                break;
                            case R.id.navigation_category:
                                item.setChecked(true);
                                setCategoryFragment();
                                break;
                        }
                        return false;
                    }
                });
    }

    private void setHomeFragment() {
        if (homeFragment == null) {
            if (categoriesFragment != null && categoriesFragment.isVisible()) {
                fragmentManager.beginTransaction().hide(categoriesFragment).commit();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            }
            homeFragment = HomeFragment.newInstance();
            fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.mainPageFragmentContainer, homeFragment).commit();
            fragmentTransaction.addToBackStack(HOME_FRAGMENT_TAG);
        } else if (!homeFragment.isVisible()) {
            fragmentManager.beginTransaction().hide(categoriesFragment).commit();
            fragmentManager.beginTransaction().show(homeFragment).commit();
        }
    }


    private void setCategoryFragment() {
        if (categoriesFragment == null) {
            if (homeFragment != null && homeFragment.isVisible()) {
                fragmentManager.beginTransaction().hide(homeFragment).commit();
            }

            categoriesFragment = categoriesFragment.newInstance();
            fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.mainPageFragmentContainer, categoriesFragment).commit();
            fragmentTransaction.addToBackStack("category_fragment");
        } else if (!categoriesFragment.isVisible()) {
            fragmentManager.beginTransaction().hide(homeFragment).commit();
            fragmentManager.beginTransaction().show(categoriesFragment).commit();
        }
    }
}