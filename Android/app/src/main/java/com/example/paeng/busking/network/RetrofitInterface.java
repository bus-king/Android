package com.example.paeng.busking.network;

import com.example.paeng.busking.model.Res;
import com.example.paeng.busking.model.Show;
import com.example.paeng.busking.model.User;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

public interface RetrofitInterface {

    @POST("users")
    Observable<Res> registerUser(@Body User user);

    @POST("createContests")
    Observable<Res> registerShow(@Body Show show);

    @GET("createContests")
    Observable<Show[]> getAllShow();

    @GET("show/{id}")
    Observable<Show> getShow(@Path("id") int id);

    @GET("shows/{id}")
    Observable<Show[]> getShow(@Path("userId") String userId);


/*
    @Multipart
    @POST("images/upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part image);
*/



}
