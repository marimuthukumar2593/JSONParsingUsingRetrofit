package com.androiddeft.jsonretrofit.api;


import com.androiddeft.jsonretrofit.UserList;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Abhi on 06 Sep 2017 006.
 */

public interface ApiService {

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of EmployeeList
    */
    @GET("users?")
    Call<UserList> doGetUserList(@Query("page") String page);

}
