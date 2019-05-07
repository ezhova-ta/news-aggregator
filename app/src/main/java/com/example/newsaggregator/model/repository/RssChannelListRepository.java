package com.example.newsaggregator.model.repository;

import java.util.Set;

public interface RssChannelListRepository {
    Set<String> getRssChannelLinkSet();
    void addRssChannel(String link);
}
