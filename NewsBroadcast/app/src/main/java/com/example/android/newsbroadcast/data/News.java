package com.example.android.newsbroadcast.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by abhilasha on 17-04-2017.
 */

public class News implements Serializable{

    private String title;
    private String descriptin;
    private String newsUrl;
    private String imageUrl;
    private String publishedAt;
    private Date actualPublishDate;
    private String source;
    private String author;
    private int icColor;

    public News(){

    }

    public News(String title, String descriptin, String newsUrl, String imageUrl, String publishedAt,
                String source, int icColor) {
        this.title = title;
        this.descriptin = descriptin;
        this.newsUrl = newsUrl;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
        this.icColor = icColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptin() {
        return descriptin;
    }

    public void setDescriptin(String descriptin) {
        this.descriptin = descriptin;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getActualPublishDate() {
        return actualPublishDate;
    }

    public void setActualPublishDate(Date actualPublishDate) {
        this.actualPublishDate = actualPublishDate;
    }

    public int getIcColor() {
        return icColor;
    }

    public void setIcColor(int icColor) {
        this.icColor = icColor;
    }
}
