package com.yaratech.yaratube.data.source.remote;

import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.CommentResponse;
import com.yaratech.yaratube.data.model.Home;
import com.yaratech.yaratube.data.model.MobileLoginStepOneResponse;
import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.model.ProductDetail;
import com.yaratech.yaratube.util.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Services {
    @GET("store/" + Util.STORE_ID)
    Call<Home> getHome();

    @GET("category/" + Util.STORE_ID + "/463")
    Call<List<Category>> getCategory();

    @GET("listproducts/{category_id}?limit=" + Util.LIMIT + "&")
    Call<List<Product>> getProductList(@Path("category_id") int categoryId, @Query("offset") int offset);

    @GET("product/{product_id}?device_os=ios")
    Call<ProductDetail> getProductDetail(@Path("product_id") int productId);

    @GET("comment/{product_id}")
    Call<List<Comment>> getCommentList(@Path("product_id") int productId);

    @FormUrlEncoded
    @POST("mobile_login_step1/" + Util.STORE_ID)
    Call<MobileLoginStepOneResponse> sendPhoneNumber(
            @Field("mobile") String mobile,
            @Field("device_id") String deviceId,
            @Field("device_model") String deviceModel,
            @Field("device_os") String deviceOs,
            @Field("gcm") String gcm);

    @FormUrlEncoded
    @POST("mobile_login_step2/" + Util.STORE_ID)
    Call<MobileLoginStepOneResponse> sendPhoneNumber(
            @Field("mobile") String mobile,
            @Field("device_id") String deviceId,
            @Field("verification_code") String deviceModel,
            @Field("nickname") String nickname);

    @FormUrlEncoded
    @POST("mobile_login_step2/" + Util.STORE_ID)
    Call<MobileLoginStepTwoResponse> sendActivationCode(
            @Field("mobile") String mobile,
            @Field("device_id") String deviceId,
            @Field("verification_code") String verificationCode,
            @Field("nickname") String nickname);

    @FormUrlEncoded
    @POST("/comment/{product_id}")
    Call<CommentResponse> sendComment(
            @Path("product_id") int productId,
            @Header("Authorization") String authorization,
            @Field("title") String title,
            @Field("score") int score,
            @Field("comment_text") String commentText);

    @FormUrlEncoded
    @POST("login_google/" + Util.STORE_ID)
    Call<MobileLoginStepTwoResponse> sendTokenId(
            @Field("token_id") String token_id,
            @Field("device_id") String deviceId,
            @Field("device_os") String device_os,
            @Field("device_model") String device_model);
}
