package com.example.newsaggregator.model.repository;

import android.database.sqlite.SQLiteException;

import com.example.newsaggregator.model.DbException;
import com.example.newsaggregator.model.datasource.local.LocalNewsEntryListDataSource;
import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.List;

public class NewsEntryListRepositoryImpl implements NewsEntryListRepository {
    private final LocalNewsEntryListDataSource dataSource;

    public NewsEntryListRepositoryImpl(final LocalNewsEntryListDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<NewsEntry> getNewsEntryList(final String rssChannelLink) throws DbException {
        try {
            return dataSource.getNewsEntryList(rssChannelLink);
        } catch(final SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteOutdatedNewsEntries() throws DbException {
        try {
            dataSource.deleteOutdatedNewsEntries();
        } catch(final SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }
}
