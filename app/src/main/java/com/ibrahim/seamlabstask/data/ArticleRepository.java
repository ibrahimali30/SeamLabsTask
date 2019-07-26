package com.ibrahim.seamlabstask.data;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.ibrahim.seamlabstask.data.db.ArticleDao;
import com.ibrahim.seamlabstask.data.db.ArticleDataBase;
import com.ibrahim.seamlabstask.data.model.Article;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ArticleRepository {

    private static final String TAG = "ArticleRepository";
    private ArticleDao mArticleDao;
    private LiveData<List<Article>> allArticles;
    private LiveData<List<Article>> savedArticles;

    public ArticleRepository(Application application){
        ArticleDataBase database = ArticleDataBase.getInstance(application);
        mArticleDao = database.noteDao();
        allArticles = mArticleDao.getAllArticles();
        savedArticles = mArticleDao.getSavedArticles();
    }


    public void updateArticle(final String articleUrl){
        ExecutorService executors = Executors.newSingleThreadExecutor();
        Log.d(TAG, "updateArticle: called");
        executors.execute(new Runnable() {
            @Override
            public void run() {
                long l = mArticleDao.updateArticle(articleUrl);
                Log.d(TAG, "run: "+l);
            }
        });

    }


    public LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }

    public LiveData<List<Article>> getSvedArticles() {
        return savedArticles;
    }


    public void insertAllArticles(final List<Article> articles) {
        Log.d(TAG, "insertAllArticles: called");
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                mArticleDao.insertAllArticles(articles);
            }
        });
    }

    public int deleteAllArticles() {
        Log.d(TAG, "deleteAllArticles: called");
        ExecutorService service = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return  mArticleDao.deleteAllArticles();

            }
        };

        Future<Integer> future = service.submit(callable);

        try {
            return future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return -1;

    }
}
