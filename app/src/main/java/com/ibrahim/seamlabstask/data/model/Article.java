package com.ibrahim.seamlabstask.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Article {
    private String author;
    private String title  ;
    private String description  ;
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String url;
    private String urlToImage ;
    private String publishedAt ;
    private String content ;
    @Ignore
    private ArticleSource source ;

    private int isSaved;

    public Article() {
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }

    public ArticleSource getSource() {
        return source;
    }


    public int getIsSaved() {
        return isSaved;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSource(ArticleSource source) {
        this.source = source;
    }


    public void setIsSaved(int isSaved) {
        this.isSaved = isSaved;
    }
}
