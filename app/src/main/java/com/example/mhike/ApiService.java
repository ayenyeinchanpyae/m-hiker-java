package com.example.mhike;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @Multipart
    @POST("upload") // Replace with the actual endpoint on your server
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image);
}
