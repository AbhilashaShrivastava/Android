package com.example.android.newsbroadcast.Utilities;

import com.example.android.newsbroadcast.data.News;

import java.util.Comparator;

/**
 * Created by abhilasha on 24-04-2017.
 */

public class NewsComparator implements Comparator<News> {
    @Override
    public int compare(News o2, News o1) {
        if(o2.getActualPublishDate() == null && o1.getActualPublishDate() == null) {
            return 0;
        }
        if(o2.getActualPublishDate() == null) {
            return 1;
        }
        if(o1.getActualPublishDate() == null) {
            return -1;
        }
        return o1.getActualPublishDate().compareTo(o2.getActualPublishDate());
    }
}
