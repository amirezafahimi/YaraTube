package com.yaratech.yaratube.data.source.remote;
import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.Home;
import com.yaratech.yaratube.data.model.MobileLoginStep1;
import com.yaratech.yaratube.data.model.MobileLoginStep2;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.model.ProductDetail;
import com.yaratech.yaratube.util.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Services {
    @GET("store/"+ AppConstants.STORE_ID)
    Call<Home> getHome();

    @GET("category/"+ AppConstants.STORE_ID+"/463")
    Call<List<Category>> getCategory();

    @GET("listproducts/{category_id}")
    Call<List<Product>> getProductList(@Path("category_id") int categoryId);

    @GET("product/{product_id}")
    Call<ProductDetail> getProductDetail(@Path("product_id") int productId);

    @GET("comment/{product_id}")
    Call<List<Comment>> getCommentList(@Path("product_id") int productId);

    @FormUrlEncoded
    @POST("mobile_login_step1/" + AppConstants.STORE_ID)
    Call<MobileLoginStep1> sendPhoneNumber(
            @Field("mobile") String mobile,
            @Field("device_id") String deviceId,
            @Field("device_model") String deviceModel,
            @Field("device_os") String deviceOs,
            @Field("gcm") String gcm);

    @FormUrlEncoded
    @POST("mobile_login_step2/" + AppConstants.STORE_ID)
    Call<MobileLoginStep1> sendPhoneNumber(
            @Field("mobile") String mobile,
            @Field("device_id") String deviceId,
            @Field("verification_code") String deviceModel,
            @Field("nickname") String nickname);

    @FormUrlEncoded
    @POST("mobile_login_step2/" + AppConstants.STORE_ID)
    Call<MobileLoginStep2> sendActivationCode(
            @Field("mobile") String mobile,
            @Field("device_id") String deviceId,
            @Field("verification_code") String verificationCode,
            @Field("nickname") String nickname);

}
