package com.example.newsaggregator.model;

import com.example.newsaggregator.model.entity.NewsEntry;
import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public interface Repository {
    List<RssChannel> getRssChannelList();
    List<NewsEntry> getNewsEntryList(final String channelLink);
    void addRssChannel(final String link);
}
