package com.yaratube.data.source;

import com.yaratube.data.model.Category_list;
import com.yaratube.data.model.MainPage;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("store/16")
    Call<MainPage> getHome();

    @GET("category/16/463")
    Call<List<Category_list>> getCategory();
}
