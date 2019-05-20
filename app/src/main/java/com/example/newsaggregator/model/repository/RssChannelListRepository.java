package com.example.newsaggregator.model.repository;

import com.example.newsaggregator.model.DbException;
import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public interface RssChannelListRepository {
    List<RssChannel> getRssChannelList() throws DbException;
    void addRssChannel(RssChannel rssChannel) throws DbException;
    void deleteRssChannel(String link) throws DbException;
}
