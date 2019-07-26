package com.ibrahim.seamlabstask.view.articleList;

import android.app.Application;
import android.app.TaskInfo;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.ibrahim.seamlabstask.data.API.ApiManager;
import com.ibrahim.seamlabstask.data.ArticleRepository;
import com.ibrahim.seamlabstask.data.model.Article;
import com.ibrahim.seamlabstask.data.model.ArticleResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlListViewModel extends AndroidViewModel {
    private static final String TAG = "ArticlListViewModel";

    ArticleRepository mArticleRepository;
    public LiveData<List<Article>> articleLiveData;
    public LiveData<List<Article>> savedArticlesLiveData;
    public MutableLiveData<Boolean> setRefreshing;

    public ArticlListViewModel(@NonNull Application application) {
        super(application);
        mArticleRepository = new ArticleRepository(application);
        articleLiveData = mArticleRepository.getAllArticles();
        savedArticlesLiveData = mArticleRepository.getSvedArticles();
        setRefreshing = new MutableLiveData<>();

        getArticle();
    }

    public void getArticle() {
        Call<ArticleResponse> call = ApiManager.getAPIs().getArticles("egypt");

        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                Log.d(TAG, "onResponse: "+response.body().getArticles().size());

                if (response.isSuccessful() && response.body().getArticles() != null) {
                       Log.d(TAG, "onResponse: **** returning true");
                       int i = mArticleRepository.deleteAllArticles();
                    Log.d(TAG, "onResponse: i"+i);
                       mArticleRepository.insertAllArticles(response.body().getArticles());

                       setRefreshing.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                setRefreshing.setValue(false);
            }
        });
    }


}
