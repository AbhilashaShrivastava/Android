package com.example.android.newsbroadcast.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.example.android.newsbroadcast.data.ConstructSources;
import com.example.android.newsbroadcast.data.News;
import com.example.android.newsbroadcast.data.NewsSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * Created by abhilasha on 17-04-2017.
 */

public class NetworkUtils {
    //private static final String TAG = NetworkUtils.class.getName();

    private final static String BASE_URL = "https://newsapi.org/v1/articles";
    private final static String QUERY_SOURCE = "source";
    //private final static String QUERY_SOURCE_VALUE = "the-times-of-india";
    private final static String SORT_BY = "sortBy";
    private final static String SORT_BY_VALUE_TOP = "top";
    private final static String SORT_BY_VALUE_LATEST = "latest";
    private final static String ESPN_SORT_BY_VALUE = "top";
    private final static String API_KEY = "apiKey";
    private final static String API_KEY_VALUE = "";
    private final static String NEWS_TITLE = "title";
    private final static String NEWS_DESCRIPTION = "description";
    private final static String NEWS_URL = "url";
    private final static String NEWS_IMAGE_URL = "urlToImage";
    private final static String NEWS_PUBLISHED_AT = "publishedAt";
    private final static String NEWS_AUTHOR = "author";

    public final static String SOURCE_GEOGRAPHIC = "national-geographic";
    public final static String SOURCE_TIMES = "the-times-of-india";
    public final static String SOURCE_ESPN = "espn";
    public final static String SOURCE_BLOOMBERG = "bloomberg";
    public final static String SOURCE_MTV = "mtv-news";

    //private static List<News> mNewsList;

    public static List<News> getNewsDataFromHttp(List<NewsSource> sources, ArrayList<News> newsList)
            throws IOException, JSONException, ParseException{
        //mNewsList = new ArrayList<News>();
        Iterator<NewsSource> sourcesIterator = sources.iterator();
        if(sources == null || sources.size() == 0) {return null;}
        NewsSource newsSource;
        String soryBt;
        while(sourcesIterator.hasNext()) {
            newsSource = sourcesIterator.next();
            if(newsSource.isLatestSort()){
                soryBt = SORT_BY_VALUE_LATEST;
            }else{
                soryBt = SORT_BY_VALUE_TOP;
            }
            URL url = createUrl(newsSource.getId(), soryBt);
            //Log.d("NetworkUtils", "Source: ==== " + newsSource.getId());
            String jsonResponseString = getJSONFromHttp(url);
            getNewsFromJSON(jsonResponseString, newsSource.getName(), newsList);
        }

        Collections.sort(newsList, new NewsComparator());

        return newsList;
    }

    private static URL createUrl(String source, String sortBy){
        Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_SOURCE, source)
                .appendQueryParameter(SORT_BY, sortBy)
                .appendQueryParameter(API_KEY, API_KEY_VALUE)
                .build();
        URL queryUrl = null;
        try {
            queryUrl = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return queryUrl;
    }

    /**
     * This method gets internet connection and returns the JSON response
     * as a single string.
     * @param url
     * @return
     * @throws IOException
     */

    private static String getJSONFromHttp(URL url) throws IOException{
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("newsapi.org", session);
            }
        };
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setHostnameVerifier(hostnameVerifier);
        InputStream in = null;
        Scanner scanner = null;
        try {
            in = urlConnection.getInputStream();

            scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String jsonResponse = scanner.next();
                return jsonResponse;
            } else {
                return null;
            }
        } finally {
            if(in != null) in.close();
            if(scanner != null) scanner.close();
            urlConnection.disconnect();
        }
    }

    private static List<News> getNewsFromJSON(String jsonResponse, String newsSource, ArrayList<News> newsList) throws JSONException,
                                                    ParseException{
        if(jsonResponse == null) {return null;}

        JSONObject jsonObject = new JSONObject(jsonResponse);
        //String source = jsonObject.getString("source");

        JSONArray jsonArray = jsonObject.getJSONArray("articles");

        News news = null;
        NewsSource source = ConstructSources.getNewsSourceByName(newsSource);

        if(jsonArray != null){
            for(int count=0; count<jsonArray.length(); count++){
                JSONObject oneObject = jsonArray.getJSONObject(count);
                news = new News();
                news.setTitle(oneObject.getString(NEWS_TITLE));
                news.setDescriptin(oneObject.getString(NEWS_DESCRIPTION));
                news.setNewsUrl(oneObject.getString(NEWS_URL));
                //news.setImageUrl(oneObject.getString(NEWS_IMAGE_URL));
                String publishAsDate = oneObject.getString(NEWS_PUBLISHED_AT);
                news.setPublishedAt(convertDate(publishAsDate));
                news.setActualPublishDate(parseAsDate(publishAsDate));
                news.setAuthor(oneObject.getString(NEWS_AUTHOR));
                news.setSource(newsSource);
                news.setIcColor(source.getColor());

                newsList.add(news);
                //Log.d(TAG, "get date:: " + news.getPublishedAt());
            }
        }
        return newsList;
    }

    private static String convertDate(String time){
        String convertedDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            Date date = sdf.parse(time);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yy");
            convertedDate = sdf2.format(date);
        }catch(ParseException pe){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'+'");
                Date date = sdf.parse(time);
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yy");
                convertedDate = sdf2.format(date);
            } catch (ParseException pe2) {
                convertedDate = "";
            }

        }
        return convertedDate;
    }

    private static Date parseAsDate(String time){
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            date = sdf.parse(time);
        }catch(ParseException pe){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'+'");
                date = sdf.parse(time);
            } catch (ParseException pe2) {
                date = null;
            }

        }
        return date;
    }

    public static Bitmap downloadNewsImage(URL imageUrl) throws IOException{
//        InputStream is = null;
//        Bitmap bmImg = null;
//
//        HttpURLConnection conn = (HttpURLConnection) imageUrl
//                .openConnection();
//        conn.setDoInput(true);
//        conn.connect();
//        is = conn.getInputStream();
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.RGB_565;
//        bmImg = BitmapFactory.decodeStream(is, null, options);
//        return bmImg;

        return BitmapFactory.decodeStream((InputStream)imageUrl.getContent());
    }
}
