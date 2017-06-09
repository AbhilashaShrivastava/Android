package com.example.android.newsbroadcast.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.newsbroadcast.R;
import com.example.android.newsbroadcast.Utilities.NetworkUtils;
import com.example.android.newsbroadcast.data.ConstructSources;
import com.example.android.newsbroadcast.data.News;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by abhilasha on 18-04-2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {
    private static final String TAG = NewsAdapter.class.getName();

    private List<News> mNewsList = new ArrayList<News>();

    //To implement onClick event
    private OnNewsItemClickHandler mOnNewsClickHandler;
    private Context mContext;

    // An interface to register onClick event
    public interface OnNewsItemClickHandler{
        public void onNewsItemClick(String newsUrl);
    }

    public NewsAdapter(OnNewsItemClickHandler onNewsItemClick, Context context){
        mOnNewsClickHandler = onNewsItemClick;
        mContext = context;
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);
        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        if(mNewsList == null) {
            return;
        }
        News news = mNewsList.get(position);
        holder.mNewsTitle.setText(news.getTitle());
        holder.mPublishdate.setText(news.getPublishedAt());

        String source = news.getSource();
        holder.mNewsSource.setText(source.trim());
        String firstName = String.valueOf(source.charAt(0));

        holder.mNewsLogo.setText(firstName);
        holder.mNewsLogo.setTextColor(mContext.getResources().getColor(news.getIcColor()));

        holder.itemView.setTag(news.getTitle());
    }

    @Override
    public int getItemCount() {
        if(mNewsList == null || mNewsList.size() == 0) return 0;
        return mNewsList.size();
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mNewsTitle;
        public TextView mPublishdate;
        public TextView mNewsSource;
        public TextView mNewsLogo;

        public NewsAdapterViewHolder(View itemView) {
            super(itemView);
            mNewsTitle = (TextView)itemView.findViewById(R.id.tv_tittle);
            mPublishdate = (TextView)itemView.findViewById(R.id.tv_date);
            mNewsSource = (TextView)itemView.findViewById(R.id.tv_source);
            mNewsLogo = (TextView)itemView.findViewById(R.id.tv_news_ic);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedItemPosition = getAdapterPosition();
            News clickedNewsItem = mNewsList.get(clickedItemPosition);
            mOnNewsClickHandler.onNewsItemClick(clickedNewsItem.getNewsUrl());
        }
    }

    public void setNewsData(List<News> newsList){
        mNewsList = newsList;
        notifyDataSetChanged();
    }
}
