package com.ibrahim.seamlabstask.data.db;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ibrahim.seamlabstask.data.model.Article;

@Database(entities = {Article.class} , version = 2)
public abstract class ArticleDataBase extends RoomDatabase {
    private static final String TAG = "MessageDatabase";
    private static ArticleDataBase instance;

    public abstract ArticleDao noteDao();

    public static synchronized ArticleDataBase getInstance(Context context){
        Log.d(TAG, "getInstance: called");
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                   ArticleDataBase.class ,"notifications database" )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
