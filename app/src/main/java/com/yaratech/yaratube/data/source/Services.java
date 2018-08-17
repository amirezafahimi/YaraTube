package com.yaratech.yaratube.data.source;
import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.Home;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.util.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Services {
    @GET("store/"+ AppConstants.STORE_ID)
    Call<Home> getHome();

    @GET("category/"+ AppConstants.STORE_ID+"/463")
    Call<List<Category>> getCategory();

    @GET("listproducts/{category_id}")
    Call<List<Product>> getProductList(@Path("category_id") int categoryId);
}
