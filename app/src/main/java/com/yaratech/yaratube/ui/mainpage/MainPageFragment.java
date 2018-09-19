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
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.ui.mainpage.categories.CategoriesFragment;
import com.yaratech.yaratube.ui.mainpage.home.HomeFragment;
import com.yaratech.yaratube.ui.mainpage.more.MoreFragment;

import static com.yaratech.yaratube.ui.mainpage.categories.CategoriesFragment.CATEGORY_FRAGMENT_TAG;
import static com.yaratech.yaratube.ui.mainpage.home.HomeFragment.HOME_FRAGMENT_TAG;
import static com.yaratech.yaratube.ui.mainpage.more.MoreFragment.MORE_FRAGMENT_TAG;

public class MainPageFragment extends Fragment {

    FragmentManager fragmentManager;
    HomeFragment homeFragment;
    MoreFragment moreFragment;
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
        previousFragment = getChildFragmentManager()
                .findFragmentById(R.id.mainPageFragmentContainer);
        setFragment(homeFragment, HOME_FRAGMENT_TAG);
        final BottomNavigationView bottomNavigationView = view.findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                item.setChecked(true);
                                setFragment(homeFragment, HOME_FRAGMENT_TAG);
                                break;
                            case R.id.navigation_category:
                                item.setChecked(true);
                                setFragment(categoriesFragment, CATEGORY_FRAGMENT_TAG);
                                break;
                            case R.id.navigation_more:
                                item.setChecked(true);
                                setFragment(moreFragment, MORE_FRAGMENT_TAG);
                                break;
                        }
                        return false;
                    }
                });
    }


    Fragment previousFragment;

    private void setFragment(Fragment fragment, String tag) {
        fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment == null) {

            if (previousFragment != null
                    && previousFragment.isVisible()) {
                fragmentManager.beginTransaction().hide(previousFragment).commit();
            }

            if (tag == HOME_FRAGMENT_TAG) {
                homeFragment = HomeFragment.newInstance();
                fragmentTransaction.add(R.id.mainPageFragmentContainer, homeFragment);
                previousFragment = homeFragment;
            } else if (tag == CATEGORY_FRAGMENT_TAG) {
                categoriesFragment = CategoriesFragment.newInstance();
                fragmentTransaction.add(R.id.mainPageFragmentContainer, categoriesFragment);
                previousFragment = categoriesFragment;
            } else if (tag == MORE_FRAGMENT_TAG) {
                moreFragment = MoreFragment.newInstance();
                fragmentTransaction.add(R.id.mainPageFragmentContainer, moreFragment);
                previousFragment = moreFragment;
            }
            fragmentTransaction.addToBackStack(tag)
                    .commit();
        } else if (!fragment.isVisible()) {
            fragmentManager.beginTransaction().hide(previousFragment).commit();
            fragmentTransaction.show(fragment).commit();
            previousFragment = fragment;
        }
    }
}