package com.example.android.newsbroadcast.data;

import java.util.Objects;

/**
 * Created by abhilasha on 16-05-2017.
 */

public class NewsSource {

    private String id;
    private String name;
    private String category;
    private String country;
    private boolean isTopSort;
    private boolean isLatestSort;
    private int color;

    public NewsSource(){}

    public NewsSource(String id, String name, String category,
                      //String country,
                      boolean isTopSort, boolean isLatestSort, int color) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.country = country;
        this.isTopSort = isTopSort;
        this.isLatestSort = isLatestSort;
        this.color = color;
    }

    public boolean isTopSort() {
        return isTopSort;
    }

    public void setTopSort(boolean topSort) {
        isTopSort = topSort;
    }

    public boolean isLatestSort() {
        return isLatestSort;
    }

    public void setLatestSort(boolean latestSort) {
        isLatestSort = latestSort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsSource that = (NewsSource) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.length();
    }
}
