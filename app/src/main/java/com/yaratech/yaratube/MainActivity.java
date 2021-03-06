package com.yaratech.yaratube;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.orhanobut.hawk.Hawk;
import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.local.AppDatabase;
import com.yaratech.yaratube.data.source.local.utility.DataGenerator;
import com.yaratech.yaratube.ui.login.LoginDialogContainer;
import com.yaratech.yaratube.ui.mainpage.MainPageFragment;
import com.yaratech.yaratube.ui.OnProductActionListener;
import com.yaratech.yaratube.ui.productdetails.ProductDetailsFragment;
import com.yaratech.yaratube.ui.mainpage.categories.CategoriesFragment;
import com.yaratech.yaratube.ui.productlist.ProductListFragment;
import com.yaratech.yaratube.util.AppConstants;

public class MainActivity extends AppCompatActivity
        implements MainContract.View,
        NavigationView.OnNavigationItemSelectedListener,
        CategoriesFragment.OnCategoryFragmentActionListener,
        OnProductActionListener {


    /*public static SharedPreferences sharedPreferences;*/
    MainPresenter mainPresenter;
    LoginDialogContainer loginDialogContainer;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AppConstants.setFragment(R.id.fragment_container,
                getSupportFragmentManager(),
                MainPageFragment.newInstance(),
                "mainPageFragment",
                false);

        /*sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);*/

        mainPresenter = new MainPresenter(this, new Repository());
        mainPresenter.setDatabaseWithContext(this);

        Hawk.init(this).build();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            if (mainPresenter.checkIfUserIsLogedIn()) {
                //Go to profile.
            } else {
                loginDialogContainer = LoginDialogContainer.newInstance();
                loginDialogContainer.setCancelable(false);
                loginDialogContainer.show(getSupportFragmentManager(), "login dialog");
            }
        } else if (id == R.id.aboutUs) {

        } else if (id == R.id.callUs) {
            DataGenerator.with(AppDatabase.getAppDatabase(this)).deleteUser(1);
            mainPresenter.setUserIsLogedIn(false);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    ProductListFragment productListFragment;
    @Override
    public void goFromCategoryToProductList(Category category) {
        productListFragment = ProductListFragment.newInstance(category.getId());
        AppConstants.setFragment(R.id.fragment_container,
                getSupportFragmentManager(),
                productListFragment,
                "productListFragment",
                true);

    }


    @Override
    public void goFromProductToProdutDetails(Product product, ImageView imageView) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName("transition" + product.getId());
        }

        ProductDetailsFragment productDetailsFragment = ProductDetailsFragment.newInstance(product);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .addSharedElement(imageView, ViewCompat.getTransitionName(imageView))
                    .setReorderingAllowed(true)
                    .hide(getSupportFragmentManager().findFragmentById(R.id.fragment_container))
                    .add(R.id.fragment_container, productDetailsFragment, "productDetailsFragment")
                    .addToBackStack("productDetailsFragment")
                    .commit();
        }
        /*AppConstants.setFragment(R.id.fragment_container,
                getSupportFragmentManager(),
                ProductDetailsFragment.newInstance(product),
                "productDetailsFragment",
                true);*/
    }

}