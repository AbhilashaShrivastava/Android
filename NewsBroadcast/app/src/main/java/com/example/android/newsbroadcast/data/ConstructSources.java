package com.example.android.newsbroadcast.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.android.newsbroadcast.R;

/**
 * Created by abhilasha on 16-05-2017.
 */

public class ConstructSources {

    private static final String CATEGORIES = "categories";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COUNTRY = "country";
    private static final String SORT_BY_OPTIONS = "availableSortBy";

    private static Map<String, List<NewsSource>> sources = new HashMap<>();;
    private static List<NewsSource> sourceList;

    private static final boolean topsort = true;
    //private static final boolean notTopsort = false;
    private static final boolean latestsort = true;
    private static final boolean notLatestsort = false;

    public static final String COUNTRY_US = "us";
    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_SPORTS = "sports";
    public static final String CATEGORY_ENTERTAINMENT = "entertainment";
    public static final String CATEGORY_BUSINESS = "business";

    private static Map<String, Map<String, List<NewsSource>>> mapMap = new HashMap<>();

    // ENTERTINMENT
    static {
        sourceList = new ArrayList<>();
        sourceList.add(new NewsSource("al-jazeera-english","Al Jazeera English",
                "general", topsort, latestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("associated-press","Associated Press",
                "general", topsort, notLatestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("cnn","CNN",
                "general", topsort, notLatestsort, R.color.al_jazeera_english));

//        sourceList.add(new NewsSource("google-news","Google News",
//                "general", topsort, notLatestsort));

        sourceList.add(new NewsSource("newsweek","Newsweek",
                "general", topsort, latestsort, R.color.al_jazeera_english));

//        sourceList.add(new NewsSource("new-york-magazine","New York Magazine",
//                "general", topsort, latestsort));

        sourceList.add(new NewsSource("reuters","Reuters",
                "general", topsort, latestsort, R.color.reuters));

        sourceList.add(new NewsSource("the-huffington-post","The Huffington Post",
                "general", topsort, notLatestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("the-new-york-times","The New York Times",
                "general", topsort, notLatestsort, R.color.al_jazeera_english));

//        sourceList.add(new NewsSource("reddit-r-all","Reddit \\/r\\/all",
//                "general", topsort, latestsort));

        sourceList.add(new NewsSource("the-washington-post","The Washington Post",
                "general", topsort, notLatestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("time","Time",
                "general", topsort, latestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("usa-today","USA Today",
                "general", topsort, latestsort, R.color.al_jazeera_english));

        //sources = new HashMap<>();
        sources.put(CATEGORY_GENERAL, sourceList);
        //mapMap.put(COUNTRY_US, sources);


        sourceList = new ArrayList<>();
        sourceList.add(new NewsSource("bloomberg","Bloomberg",
                "business", topsort, notLatestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("business-insider","Business Insider",
                "business", topsort, latestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("cnbc","CNBC",
                "business", topsort, notLatestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("fortune","Fortune",
                "business", topsort, latestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("the-wall-street-journal","The Wall Street Journal",
                "business", topsort, notLatestsort, R.color.al_jazeera_english));

        sources.put(CATEGORY_BUSINESS, sourceList);


        sourceList = new ArrayList<>();
        sourceList.add(new NewsSource("buzzfeed","Buzzfeed",
                "entertainment", topsort, latestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("entertainment-weekly","Entertainment Weekly",
                "entertainment", topsort, notLatestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("mashable","Mashable",
                "entertainment", topsort, latestsort, R.color.al_jazeera_english));

        sources.put(CATEGORY_ENTERTAINMENT, sourceList);


        sourceList = new ArrayList<>();
        sourceList.add(new NewsSource("espn","ESPN",
                "sport", topsort, notLatestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("espn-cric-info","ESPN Cric Info",
                "sport", topsort, latestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("fox-sports","Fox Sports",
                "sport", topsort, latestsort, R.color.al_jazeera_english));

        sourceList.add(new NewsSource("nfl-news","NFL News",
                "sport", topsort, latestsort, R.color.al_jazeera_english));

        sources.put(CATEGORY_SPORTS, sourceList);

        mapMap.put(COUNTRY_US, sources);
    }

    public static List<NewsSource> getNewsSource(String country, String category){

        if(mapMap == null || mapMap.size() == 0){
            return null;
        }

        Map<String, List<NewsSource>> sources;
        List<NewsSource> newsSourceList = null;
        for (Map.Entry<String, Map<String, List<NewsSource>>> entry : mapMap.entrySet()){
            if(country.equals(entry.getKey())) {
                sources = entry.getValue();
                for (Map.Entry<String, List<NewsSource>> subEntry: sources.entrySet()){
                    if(category.equals(subEntry.getKey())){
                        newsSourceList = subEntry.getValue();
                        break;
                    }
                }
                break;
            }
        }
        return newsSourceList;

    }

    public static NewsSource getNewsSourceByName(String sourceName){
        NewsSource source = null;
        for (String key: mapMap.keySet()) {
            Map<String, List<NewsSource>> map = mapMap.get(key);
            for (String cat: map.keySet()) {
                List<NewsSource> newsSourceList = map.get(cat);
                NewsSource newsSource = new NewsSource();
                newsSource.setName(sourceName);
                int idx = newsSourceList.indexOf(newsSource);
                if (idx != -1) {
                    source = newsSourceList.get(idx);
                    return source;
                }
            }
        }
        return source;
    }
}
