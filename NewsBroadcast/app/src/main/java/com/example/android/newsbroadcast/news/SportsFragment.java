package com.example.android.newsbroadcast.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.newsbroadcast.R;
import com.example.android.newsbroadcast.Utilities.NetworkUtils;
import com.example.android.newsbroadcast.data.ConstructSources;
import com.example.android.newsbroadcast.data.News;
import com.example.android.newsbroadcast.data.NewsSource;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SportsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>>
        , NewsAdapter.OnNewsItemClickHandler{
    private static final String TAG = SportsFragment.class.getName();

    private static List<NewsSource> mSportsNewsSources;
    private static List<News> mSportsNewsList = new ArrayList<>();
    private static final int NEWS_LOADER_ID = 2;

    //To implement RecyclerView
    private RecyclerView mNewsRecyclerView;
    private NewsAdapter mNewsAdapter;

    private ProgressBar mDownloadProgress;
    private TextView mErrorMessage;


    public SportsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate Called");
        if(mSportsNewsSources == null || mSportsNewsSources.size() == 0) {
            mSportsNewsSources = ((NewsActivity) getActivity()).getmNewsSourceList(ConstructSources.CATEGORY_SPORTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView Called");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_category, container, false);

        mDownloadProgress = (ProgressBar) rootView.findViewById(R.id.pb_download_progress);
        mErrorMessage = (TextView) rootView.findViewById(R.id.tv_error_message) ;

        // To implement RecyclerView
        mNewsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_news_list) ;

        mNewsAdapter = new NewsAdapter(this, getContext());

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mNewsRecyclerView.setLayoutManager(layoutManager);
        mNewsRecyclerView.setHasFixedSize(true);
        mNewsRecyclerView.setAdapter(mNewsAdapter);

        // To implement AsyncTaskLoader functionality
       if (getActivity().getSupportLoaderManager().getLoader(NEWS_LOADER_ID) == null) {
            getActivity().getSupportLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        } else {
            getActivity().getSupportLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
        }


        return rootView;
    }



    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<News>>(getContext()) {

           // private List<News> mNewsList = new ArrayList<News>();

            @Override
            protected void onStartLoading() {
                mDownloadProgress.setVisibility(View.VISIBLE);
                if(mSportsNewsList.size() != 0){
                    mDownloadProgress.setVisibility(View.INVISIBLE);
                    deliverResult(mSportsNewsList);
                }else {
                    forceLoad();
                }
            }

            @Override
            public List<News> loadInBackground() {
                try {
                    mSportsNewsList = NetworkUtils.getNewsDataFromHttp(mSportsNewsSources, new ArrayList<News>());
                   return mSportsNewsList;
                } catch (IOException e) {
                    Log.v(TAG, "IOException caught during getJSONFromHttp call");
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.v(TAG, "JSONException caught during getNewsFromJSON call");
                    e.printStackTrace();
                } catch (ParseException e) {
                    Log.v(TAG, "ParseException caught during getNewsFromJSON call");
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void deliverResult(List<News> data) {
                super.deliverResult(data);
                mSportsNewsList = data;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        mDownloadProgress.setVisibility(View.INVISIBLE);
        mNewsAdapter.setNewsData(newsList);
        if(newsList == null || newsList.size() <=0){
            showErrorMessage();
        }else{
            showNewsdata();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mNewsAdapter.setNewsData(null);
    }

    private void showErrorMessage(){
        mErrorMessage.setVisibility(View.VISIBLE);
        mNewsRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void showNewsdata(){
        mErrorMessage.setVisibility(View.INVISIBLE);
        mNewsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNewsItemClick(String newsUrl) {
        ((NewsActivity)getActivity()).openBrowser(newsUrl);

    }
}