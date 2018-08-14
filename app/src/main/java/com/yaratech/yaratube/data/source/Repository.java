package com.yaratech.yaratube.data.source;

import android.util.Log;

import com.yaratech.yaratube.data.model.Home;
import com.yaratech.yaratube.data.source.Services;
import com.yaratech.yaratube.data.source.remote.Client;
import com.yaratech.yaratube.ui.home.HomePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    Home home;

    public void getHome(final GetResultInterface<Home> homeInterfacee) {

        Client.getRetrofitInstance().create(Services.class).getHome().enqueue(new Callback<Home>() {
            @Override
            public void onResponse(Call<Home> call, Response<Home> response) {
                if (response.isSuccessful()) {
                    home = response.body();
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
}
