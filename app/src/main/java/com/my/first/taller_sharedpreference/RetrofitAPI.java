package com.my.first.taller_sharedpreference;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {
    @GET
    Call<NewsModal> getAllNews (@Url String url);


}
