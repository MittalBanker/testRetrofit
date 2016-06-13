package com.example.digicorp.testretrofit;

import android.util.Log;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.Call;

import static android.util.Log.*;

/**
 * Created by digicorp on 09/06/16.
 */
public interface StackOverflowAPI {
    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")

    Call<StackOverflowQuestions> loadQuestions(@Query("tagged") String tags);


}

