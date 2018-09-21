package com.yaratech.yaratube.data.source;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.CommentResponse;
import com.yaratech.yaratube.data.model.GoogleLoginResponse;
import com.yaratech.yaratube.data.model.Home;
import com.yaratech.yaratube.data.model.MobileLoginStepOneResponse;
import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.model.ProductDetail;
import com.yaratech.yaratube.data.model.ProfileGetResponse;
import com.yaratech.yaratube.data.model.ProfilePostResponse;
import com.yaratech.yaratube.data.source.local.AppDatabase;
import com.yaratech.yaratube.data.source.local.entity.User;
import com.yaratech.yaratube.data.source.local.utility.DataGenerator;
import com.yaratech.yaratube.data.source.local.utility.LocalDataSource;
import com.yaratech.yaratube.data.source.remote.Services;
import com.yaratech.yaratube.data.source.remote.Client;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public void getHome(final ApiResultCallback<Home> homeInterfacee) {

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

    public void getCategories(final ApiResultCallback<List<Category>> categoryInterface) {
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

    public void getProducts(int id, int offset, final ApiResultCallback<List<Product>> productInterface) {

        Client.getRetrofitInstance().create(Services.class).getProductList(id, offset).enqueue(new Callback<List<Product>>() {
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

    public void getProductDetails(int id, final ApiResultCallback<ProductDetail> productDetailsInterface) {

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

    public void getCommentList(int id, final ApiResultCallback<List<Comment>> commentListInterface) {
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
                                      final ApiResultCallback<MobileLoginStepOneResponse> mobileLoginInterface) {
        System.out.println(Client.getRetrofitInstance().create(Services.class).sendPhoneNumber(num,
                deviceId,
                deviceModel,
                deviceOs,
                gcm).toString());
        Client.getRetrofitInstance().create(Services.class).sendPhoneNumber(num,
                deviceId,
                deviceModel,
                deviceOs,
                gcm).enqueue(new Callback<MobileLoginStepOneResponse>() {
            @Override
            public void onResponse(Call<MobileLoginStepOneResponse> call, Response<MobileLoginStepOneResponse> response) {
                if (response.isSuccessful()) {
                    mobileLoginInterface.onSuccess(response.body());
                } else {
                    mobileLoginInterface.onFail(response.code() + " error");
                }
            }

            @Override
            public void onFailure(Call<MobileLoginStepOneResponse> call, Throwable t) {
                mobileLoginInterface.onFail("عملیات با خطا مواجه شد!");
            }
        });

    }

    public void varificationCode(String num,
                                 String deviceId,
                                 String activationCode,
                                 String nickname,
                                 final ApiResultCallback<MobileLoginStepTwoResponse> mobileLoginInterface) {
        Client.getRetrofitInstance().create(Services.class).sendActivationCode(num,
                deviceId,
                activationCode,
                nickname).enqueue(new Callback<MobileLoginStepTwoResponse>() {
            @Override
            public void onResponse(Call<MobileLoginStepTwoResponse> call, Response<MobileLoginStepTwoResponse> response) {
                if (response.isSuccessful()) {
                    mobileLoginInterface.onSuccess(response.body());
                } else {
                    mobileLoginInterface.onFail(response.code() + " error");
                }
            }

            @Override
            public void onFailure(Call<MobileLoginStepTwoResponse> call, Throwable t) {
                mobileLoginInterface.onFail("عملیات با خطا مواجه شد!");
            }
        });

    }


    public void sendComment(int productId, String token, float score, String comment,
                            ApiResultCallback<CommentResponse> commentResponseInterface) {

        Client.getRetrofitInstance().create(Services.class).sendComment(productId,
                "Token " + token,
                "",
                (int) score,
                comment).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful()) {
                    commentResponseInterface.onSuccess(response.body());
                } else {
                    commentResponseInterface.onFail(response.code() + " error");
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                commentResponseInterface.onFail("عملیات با خطا مواجه شد!");
            }
        });
    }

    public void googleLogin(String tokenId, String deviceId, String deviceOs, String deviceModel,
                            ApiResultCallback<GoogleLoginResponse> googleLoginResponseInterface) {
        Client.getRetrofitInstance().create(Services.class)
                .sendTokenId(
                        tokenId,
                        deviceId,
                        deviceOs,
                        deviceModel).enqueue(new Callback<GoogleLoginResponse>() {

            @Override
            public void onResponse(Call<GoogleLoginResponse> call, Response<GoogleLoginResponse> response) {
                if (response.isSuccessful()) {
                    googleLoginResponseInterface.onSuccess(response.body());
                } else {
                    googleLoginResponseInterface.onFail(response.code() + " error");
                }
            }

            @Override
            public void onFailure(Call<GoogleLoginResponse> call, Throwable t) {
                googleLoginResponseInterface.onFail("عملیات با خطا مواجه شد!");
            }
        });

    }


    public void sendProfile(String name,
                            String gender,
                            String birthdate,
                            String deviceId,
                            String deviceModel,
                            String deviceOs,
                            String userToken,
                            ApiResultCallback<ProfilePostResponse> profilePostResponseInterface) {
        Client.getRetrofitInstance().create(Services.class)
                .sendProfile(name,
                        gender,
                        birthdate,
                        deviceId,
                        deviceModel,
                        deviceOs,
                        "Token " + userToken).enqueue(new Callback<ProfilePostResponse>() {
            @Override
            public void onResponse(Call<ProfilePostResponse> call, Response<ProfilePostResponse> response) {
                if (response.isSuccessful()) {
                    profilePostResponseInterface.onSuccess(response.body());
                } else {
                    profilePostResponseInterface.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfilePostResponse> call, Throwable t) {

            }
        });
    }


    public void sendProfileImage(File imageFile,
                                 String userToken,
                                 ApiResultCallback<ProfilePostResponse> profilePostResponseInterface) {
        MultipartBody.Part body;
        RequestBody reqFile;
        if (imageFile != null) {
            reqFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
            body = MultipartBody.Part.createFormData("avatar", imageFile.getName(), reqFile);
        } else {
            reqFile = RequestBody.create(MediaType.parse("image/*"), "");
            body = MultipartBody.Part.createFormData("avatar", "", reqFile);
        }

        Client.getRetrofitInstance().create(Services.class)
                .sendProfileImage(body,
                        "Token " + userToken).enqueue(new Callback<ProfilePostResponse>() {
            @Override
            public void onResponse(Call<ProfilePostResponse> call, Response<ProfilePostResponse> response) {
                if (response.isSuccessful())
                    profilePostResponseInterface.onSuccess(response.body());
                else
                    profilePostResponseInterface.onFail(response.message());
            }

            @Override
            public void onFailure(Call<ProfilePostResponse> call, Throwable t) {

                profilePostResponseInterface.onFail(t.getMessage());
            }
        });
    }


    public void getProfile(String userToken,
                           ApiResultCallback<ProfileGetResponse> profileGetResponseInterface) {
        Client.getRetrofitInstance().create(Services.class)
                .getProfile("Token " + userToken).enqueue(new Callback<ProfileGetResponse>() {
            @Override
            public void onResponse(Call<ProfileGetResponse> call, Response<ProfileGetResponse> response) {
                if (response.isSuccessful()) {
                    profileGetResponseInterface.onSuccess(response.body());
                } else {
                    profileGetResponseInterface.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileGetResponse> call, Throwable t) {
                profileGetResponseInterface.onFail(t.getMessage());
            }
        });
    }
    //----------------------------------------------------------------------------------------------

    public AppDatabase getDatabaseWithContext(Context context) {
        return AppDatabase.getAppDatabase(context);
    }

    public void setDatabase(AppDatabase appDatabase) {
        DataGenerator.with(appDatabase);
        LocalDataSource.with(appDatabase);
    }

    public boolean checkIfUserIsLogedIn() {
        return LocalDataSource.getUserIsLogedIn();
    }

    public void sendUserDataToDatabase(User user,
                                       InsertIntoDatabaseCallback insertIntoDatabaseCallback) {
        DataGenerator.putUserData(user);
        insertIntoDatabaseCallback.onUserDataInserted();
    }

    public String getUserToken() {
        return LocalDataSource.getToken();
    }

    public void setUserIsLogedIn(boolean isLogedIn) {
        LocalDataSource.setUserIsLogedIn(isLogedIn);
    }

    public void deleteUserToken(User user) {
        user.setToken(null);
        DataGenerator.putUserData(user);
    }

    public User getUserFromDB() {
        return DataGenerator.userInstance();
    }
}


/*




    String googleAccountImageUri;
            Glide.with(getContext())
                    .asBitmap()
                    .load(account.getPhotoUrl())
                    .into(new SimpleTarget<Bitmap>(1024, 1024) {
@Override
public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
        try {
        saveImage(resource);
        } catch (IOException e) {
        e.printStackTrace();
        }
        }
        });

private void saveImage(Bitmap bitmap) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        FileOutputStream fileOutputStream = null;

        try {
        image.createNewFile();
        fileOutputStream = new FileOutputStream(image);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.close();
        } catch (IOException e) {
        e.printStackTrace();
        }

        // because file name is always same
        RequestOptions requestOptions = new RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true);
        googleAccountImageUri = image.getAbsolutePath();
        }
*/
