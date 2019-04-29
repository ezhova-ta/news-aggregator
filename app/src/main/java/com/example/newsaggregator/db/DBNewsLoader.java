package com.example.newsaggregator.db;

import android.content.Context;

import com.example.newsaggregator.application.DBReader;
import com.example.newsaggregator.application.RSSReaderApplication;
import com.example.newsaggregator.parser.News;

import java.util.List;
import java.util.concurrent.Callable;

public class DBNewsLoader implements Callable<List<News>> {
    private final String rssChannelUrl;

    public DBNewsLoader(final Context context, final String rssChannelUrl) {
        this.rssChannelUrl = rssChannelUrl;
    }

    @Override
    public List<News> call() throws Exception {
        final RSSReaderApplication app = RSSReaderApplication.getInstance();
        final DBReader reader = app.getDbReader();
        final long channelId = reader.getChannelId(rssChannelUrl);
        final List<News> newsList = reader.getChannelNews(channelId);
        reader.close();
        return newsList;
    }
}
