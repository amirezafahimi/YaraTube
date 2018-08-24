package com.yaratech.yaratube;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.MobileLoginStep1;
import com.yaratech.yaratube.data.model.MobileLoginStep2;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.ui.MainPage.MainPageFragment;
import com.yaratech.yaratube.ui.OnProductActionListener;
import com.yaratech.yaratube.ui.login.LoginDialog;
import com.yaratech.yaratube.ui.loginconfirmphone.ConfirmDialog;
import com.yaratech.yaratube.ui.loginwithphone.LoginWithPhoneDialog;
import com.yaratech.yaratube.ui.productdetails.ProductDetailsFragment;
import com.yaratech.yaratube.ui.MainPage.categories.CategoriesFragment;
import com.yaratech.yaratube.ui.products.ProductListFragment;
import com.yaratech.yaratube.util.AppConstants;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CategoriesFragment.OnCategoryFragmentActionListener,
        OnProductActionListener,
        LoginDialog.DismissDialog,
        LoginWithPhoneDialog.DismissDialog,
        ConfirmDialog.DismissDialog {


    /*public static SharedPreferences sharedPreferences;*/


    LoginDialog loginDialog;
    LoginWithPhoneDialog loginWithPhoneDialog;
    ConfirmDialog confirmDialog;

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

        } else if (id == R.id.aboutUs) {

        } else if (id == R.id.callUs) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void goFromCategoryToProductList(Category category) {
        AppConstants.setFragment(R.id.fragment_container,
                getSupportFragmentManager(),
                ProductListFragment.newInstance(category.getId()),
                "productListFragment",
                true);

    }


    @Override
    public void goFromProductToProdutDetails(Product product) {
        /*boolean isLoggedIn = sharedPreferences.getBoolean("USER_LOGIN", false);*/
        if (Hawk.contains("USER_LOGIN")) {
            AppConstants.setFragment(R.id.fragment_container,
                    getSupportFragmentManager(),
                    ProductDetailsFragment.newInstance(product),
                    "productDetailsFragment",
                    true);
        } else {
            loginDialog = LoginDialog.newInstance();
            loginDialog.show(getSupportFragmentManager(), "login dialog");
        }


    }

    @Override
    public void goToLoginWithPhoneDialog() {
        loginDialog.dismiss();
        loginWithPhoneDialog = LoginWithPhoneDialog.newInstance();
        loginWithPhoneDialog.show(getSupportFragmentManager(), "enter phone");
    }

    @Override
    public void goToConfirmDialog(MobileLoginStep1 step1, String phoneNum) {
        loginWithPhoneDialog.dismiss();
        confirmDialog = ConfirmDialog.newInstance(step1, phoneNum);
        confirmDialog.show(getSupportFragmentManager(), "Get the code");
        Toast.makeText(this, step1.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void dissmissConfirmDialog(MobileLoginStep2 step2) {
        confirmDialog.dismiss();
        Toast.makeText(this, step2.getMessage(), Toast.LENGTH_LONG).show();
        Hawk.put("USER_LOGIN", step2);
        /*sharedPreferences.edit().putBoolean("USER_LOGIN", true).apply();*/
    }
}
