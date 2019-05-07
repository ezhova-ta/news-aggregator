package com.example.newsaggregator.model;

import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public interface RssChannelListRepository {
    List<RssChannel> getRssChannelList();
    void addRssChannel(String link);
}
