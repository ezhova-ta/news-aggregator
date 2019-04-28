package com.example.newsaggregator.db;

import android.content.Context;

import com.example.newsaggregator.parser.News;

import java.util.List;
import java.util.concurrent.Callable;

public class DBNewsLoader implements Callable<List<News>> {
    private final Context context;
    private final String rssChannelUrl;

    public DBNewsLoader(Context context, String rssChannelUrl) {
        this.context = context;
        this.rssChannelUrl = rssChannelUrl;
    }

    @Override
    public List<News> call() throws Exception {
        final DBReader reader = new DBReader(context);
        final long channelId = reader.getChannelId(rssChannelUrl);
        final List<News> newsList = reader.getChannelNews(channelId);
        reader.close();
        return newsList;
    }
}
