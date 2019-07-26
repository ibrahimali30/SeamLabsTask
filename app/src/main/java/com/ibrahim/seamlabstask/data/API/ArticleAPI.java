package com.ibrahim.seamlabstask.data.API;


import com.ibrahim.seamlabstask.data.model.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleAPI {


    @GET("everything")
    Call<ArticleResponse> getArticles (@Query("q") String searchQuery);

}
