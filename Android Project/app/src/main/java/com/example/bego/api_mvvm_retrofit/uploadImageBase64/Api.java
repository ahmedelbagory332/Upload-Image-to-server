package com.example.bego.api_mvvm_retrofit.uploadImageBase64;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("img_upload_to_server.php")
    Call<Model> uploadImage(@Field("title") String title , @Field("image") String image);

}
