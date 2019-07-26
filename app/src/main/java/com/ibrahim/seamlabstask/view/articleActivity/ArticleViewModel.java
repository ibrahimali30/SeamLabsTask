package com.ibrahim.seamlabstask.view.articleActivity;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.ibrahim.seamlabstask.data.ArticleRepository;

public class ArticleViewModel extends AndroidViewModel {
    private static final String TAG = "ArticleViewModel";
    private ArticleRepository repository;
    Context mContext;
    public ArticleViewModel(@NonNull Application application) {
        super(application);
        repository = new ArticleRepository(application);
        mContext = application;

    }

    public void updateArticleAsSaved(String articleUrl) {
        repository.updateArticle(articleUrl);
    }

}
