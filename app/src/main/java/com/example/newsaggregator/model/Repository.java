package com.example.newsaggregator.model;

import java.util.List;

public interface Repository {
    List<RssChannel> getRssChannelList(final String link);
    List<NewsEntry> getNewsEntryList(final String channelLink);
    void addRssChannel(final String link);
}
