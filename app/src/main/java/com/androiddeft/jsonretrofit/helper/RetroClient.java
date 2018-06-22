package com.androiddeft.jsonretrofit.helper;

import com.androiddeft.jsonretrofit.api.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abhi on 06 Sep 2017 006.
 */

//public class RetroClient {
//
//    /********
//     * URLS
//     *******/
////    private static final String ROOT_URL = "http://api.androiddeft.com/";
//    private static final String ROOT_URL = "http://api.androiddeft.com/";
//    //private static final String ROOT_URL = "https://request.in/api/users?page=1";
//
//    /**
//     * Get Retrofit Instance
//     */
//    private static Retrofit getRetrofitInstance() {
//        return new Retrofit.Builder()
//                .baseUrl(ROOT_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
//
//    /**
//     * Get API Service
//     *
//     * @return API Service
//     */
//    public static ApiService getApiService() {
//        return getRetrofitInstance().create(ApiService.class);
//    }
//
//}

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
public class RetroClient {
    public static Retrofit retrofit = null;
    private static final String ROOT_URL = "https://reqres.in/api/";
    public  static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) .build();
        return retrofit;
    }
}