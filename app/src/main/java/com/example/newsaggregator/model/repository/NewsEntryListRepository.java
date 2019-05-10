package com.example.newsaggregator.model.repository;

import android.database.sqlite.SQLiteException;

import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.List;

public interface NewsEntryListRepository {
    List<NewsEntry> getNewsEntryList(String rssChannelLink) throws SQLiteException;
}
