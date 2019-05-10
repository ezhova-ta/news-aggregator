package com.example.newsaggregator.model.repository;

import android.database.sqlite.SQLiteException;

import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public interface RssChannelListRepository {
    List<RssChannel> getRssChannelList() throws SQLiteException;
    void addRssChannel(RssChannel rssChannel) throws SQLiteException;
}
