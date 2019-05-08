package com.example.newsaggregator.model.repository;

import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public interface RssChannelListRepository {
    List<RssChannel> getRssChannelList();
    void addRssChannel(RssChannel rssChannel);
}
