package com.example.bego.api_mvvm_retrofit.MultiPart;



import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    @Multipart
    @POST("img_upload_to_server.php")
    Call<List<ServerResponse>> storePost(@Part ("title") String title, @Part MultipartBody.Part image);

}
