package com.example.newsaggregator.db;

import android.content.Context;

import com.example.newsaggregator.application.DBReader;
import com.example.newsaggregator.application.RSSReaderApplication;
import com.example.newsaggregator.parser.NewsEntry;

import java.util.List;
import java.util.concurrent.Callable;

public class DBNewsLoader implements Callable<List<NewsEntry>> {
    private final String rssChannelUrl;

    public DBNewsLoader(final Context context, final String rssChannelUrl) {
        this.rssChannelUrl = rssChannelUrl;
    }

    @Override
    public List<NewsEntry> call() throws Exception {
        final RSSReaderApplication app = RSSReaderApplication.getInstance();
        final DBReader reader = app.getDbReader();
        final long channelId = reader.getChannelId(rssChannelUrl);
        final List<NewsEntry> newsEntryList = reader.getChannelNews(channelId);
        return newsEntryList;
    }
}
