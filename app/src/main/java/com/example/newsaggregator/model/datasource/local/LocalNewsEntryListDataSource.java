package com.example.newsaggregator.model.datasource.local;

import android.database.sqlite.SQLiteException;

import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.List;

public interface LocalNewsEntryListDataSource {
    List<NewsEntry> getNewsEntryList(String rssChannelLink) throws SQLiteException;
    void addNewsEntryList(String rssChannelUrl, List<NewsEntry> newsEntryList) throws SQLiteException;
    int deleteOutdatedNewsEntries() throws SQLiteException;
}
