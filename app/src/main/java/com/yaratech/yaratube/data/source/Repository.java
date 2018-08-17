package com.yaratech.yaratube.data.source;

import android.util.Log;

import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.Home;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.Services;
import com.yaratech.yaratube.data.source.remote.Client;
import com.yaratech.yaratube.ui.home.HomePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void getHome(final GetResultInterface<Home> homeInterfacee) {

        Client.getRetrofitInstance().create(Services.class).getHome().enqueue(new Callback<Home>() {
            @Override
            public void onResponse(Call<Home> call, Response<Home> response) {
                if (response.isSuccessful()) {
                    Home home = response.body();
                    homeInterfacee.onSuccess(home);

                } else {
                    Log.e("Tag", response.errorBody() + "");
                }


            }

            @Override
            public void onFailure(Call<Home> call, Throwable t) {
                homeInterfacee.onFail();
            }
        });
    }

    public void getCategories(final GetResultInterface<List<Category>> categoryInterface) {

        Client.getRetrofitInstance().create(Services.class).getCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    List<Category> categories = response.body();
                    categoryInterface.onSuccess(categories);

                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    public void getProducts(int id, final GetResultInterface<List<Product>> productInterface) {

        Client.getRetrofitInstance().create(Services.class).getProductList(id).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    productInterface.onSuccess(products);

                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
}
