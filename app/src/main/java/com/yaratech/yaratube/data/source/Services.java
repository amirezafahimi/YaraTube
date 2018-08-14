package com.yaratech.yaratube.data.source;
import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.Home;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Services {
    @GET("store/16")
    Call<Home> getHome();

    @GET("category/16/463")
    Call<List<Category>> getCategory();
}
