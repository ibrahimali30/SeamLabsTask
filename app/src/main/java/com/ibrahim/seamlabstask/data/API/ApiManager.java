package com.ibrahim.seamlabstask.data.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {



    public static Retrofit retrofitInstance;
    public static Retrofit getInstance(){
        if(retrofitInstance==null){//create

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                                          @Override
                                          public Response intercept(Interceptor.Chain chain) throws IOException {
                                              Request original = chain.request();
                                              Request request = original.newBuilder()
                                                      .addHeader("X-Api-Key", "39ef90bd75c340abaedb6f712e648b30")
                                                      .method(original.method(), original.body())
                                                      .build();

                                              return chain.proceed(request);
                                          }
                                      });

            OkHttpClient client2 = httpClient.addInterceptor(interceptor).build();


            retrofitInstance = new Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .client(client2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

    public static ArticleAPI getAPIs(){
        ArticleAPI services = getInstance().create(ArticleAPI.class);
        return services;
    }
}




