package com.example.newsaggregator.model.repository;

import com.example.newsaggregator.model.DbException;
import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.List;

public interface NewsEntryListRepository {
    List<NewsEntry> getNewsEntryList(String rssChannelLink) throws DbException;
    void deleteOutdatedNewsEntries() throws DbException;
}
