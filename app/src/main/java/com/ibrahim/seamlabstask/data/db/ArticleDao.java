package com.ibrahim.seamlabstask.data.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ibrahim.seamlabstask.data.model.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    @Query("UPDATE Article SET isSaved = 1 where url = :articleUrl ")
    long updateArticle(String articleUrl);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllArticles(List<Article> articles);

    @Delete
    void deleteArticle(Article article);

    @Query("delete from Article where isSaved = 0")
    int deleteAllArticles();


    @Query("select * from Article where isSaved = 0")
    LiveData<List<Article>> getAllArticles();

    @Query("select * from Article where isSaved = 1")
    LiveData<List<Article>> getSavedArticles();

}
