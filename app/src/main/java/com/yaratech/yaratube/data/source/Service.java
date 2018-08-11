package com.yaratech.yaratube.data.source;

import com.yaratech.yaratube.data.model.Category_list;
import com.yaratech.yaratube.data.model.Home;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("store/16")
    Call<Home> getStore();

    @GET("category/16/463")
    Call<List<Category_list>> getCategory();
}
