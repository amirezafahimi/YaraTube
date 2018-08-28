package com.yaratech.yaratube.data.source;

import android.util.Log;

import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.Home;
import com.yaratech.yaratube.data.model.MobileLoginStep1;
import com.yaratech.yaratube.data.model.MobileLoginStep2;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.model.ProductDetail;
import com.yaratech.yaratube.data.source.remote.Services;
import com.yaratech.yaratube.data.source.remote.Client;

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
                    homeInterfacee.onFail("عملیات با خطا مواجه شد!");
                }


            }

            @Override
            public void onFailure(Call<Home> call, Throwable t) {
                homeInterfacee.onFail("عملیات با خطا مواجه شد!");
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
                    categoryInterface.onFail("عملیات با خطا مواجه شد!");
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                categoryInterface.onFail("عملیات با خطا مواجه شد!");
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
                    productInterface.onFail("عملیات با خطا مواجه شد!");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                productInterface.onFail("عملیات با خطا مواجه شد!");
            }
        });
    }

    public void getProductDetails(int id, final GetResultInterface<ProductDetail> productDetailsInterface) {

        Client.getRetrofitInstance().create(Services.class).getProductDetail(id).enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.isSuccessful()) {
                    productDetailsInterface.onSuccess(response.body());

                } else {
                    productDetailsInterface.onFail("عملیات با خطا مواجه شد!");
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {
                productDetailsInterface.onFail("عملیات با خطا مواجه شد!");
            }
        });
    }

    public void getCommentList(int id, final GetResultInterface<List<Comment>> commentListInterface) {
        Client.getRetrofitInstance().create(Services.class).getCommentList(id).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    commentListInterface.onSuccess(response.body());
                } else {
                    commentListInterface.onFail("عملیات با خطا مواجه شد!");
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                commentListInterface.onFail("عملیات با خطا مواجه شد!");

            }
        });

    }

    public void requestActivationCode(String num,
                                      String deviceId,
                                      String deviceModel,
                                      String deviceOs,
                                      String gcm,
                                      final GetResultInterface<MobileLoginStep1> mobileLoginInterface) {
        System.out.println(Client.getRetrofitInstance().create(Services.class).sendPhoneNumber(num,
                deviceId,
                deviceModel,
                deviceOs,
                gcm).toString());
        Client.getRetrofitInstance().create(Services.class).sendPhoneNumber(num,
                deviceId,
                deviceModel,
                deviceOs,
                gcm).enqueue(new Callback<MobileLoginStep1>() {
            @Override
            public void onResponse(Call<MobileLoginStep1> call, Response<MobileLoginStep1> response) {
                if (response.isSuccessful()) {
                    mobileLoginInterface.onSuccess(response.body());
                } else {
                    mobileLoginInterface.onFail(response.code()+" error");
                }
            }

            @Override
            public void onFailure(Call<MobileLoginStep1> call, Throwable t) {
                mobileLoginInterface.onFail("عملیات با خطا مواجه شد!");
            }
        });

    }

    public void varificationCode(String num,
                                 String deviceId,
                                 String activationCode,
                                 String nickname,
                                 final GetResultInterface<MobileLoginStep2> mobileLoginInterface) {
        Client.getRetrofitInstance().create(Services.class).sendActivationCode(num,
                deviceId,
                activationCode,
                nickname).enqueue(new Callback<MobileLoginStep2>() {
            @Override
            public void onResponse(Call<MobileLoginStep2> call, Response<MobileLoginStep2> response) {
                if (response.isSuccessful()) {
                    mobileLoginInterface.onSuccess(response.body());
                } else {
                    mobileLoginInterface.onFail(response.code()+" error");
                }
            }

            @Override
            public void onFailure(Call<MobileLoginStep2> call, Throwable t) {
                mobileLoginInterface.onFail("عملیات با خطا مواجه شد!");
            }
        });

    }
}
