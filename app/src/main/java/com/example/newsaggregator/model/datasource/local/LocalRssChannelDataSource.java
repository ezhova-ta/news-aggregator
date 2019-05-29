package com.example.newsaggregator.model.datasource.local;

import android.database.sqlite.SQLiteException;

import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public interface LocalRssChannelDataSource {
    List<RssChannel> getRssChannelList() throws SQLiteException;
    long addRssChannel(RssChannel rssChannel) throws SQLiteException;
    int deleteRssChannel(String link) throws SQLiteException;
    int setRssChannelReaded(String link, boolean readed) throws SQLiteException;
}
