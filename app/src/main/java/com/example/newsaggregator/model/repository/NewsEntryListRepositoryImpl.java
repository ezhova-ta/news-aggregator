package com.example.newsaggregator.model.repository;

import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsaggregator.model.DbException;
import com.example.newsaggregator.model.datasource.local.LocalNewsEntryListDataSource;
import com.example.newsaggregator.model.datasource.local.LocalNewsEntryListDataSourceImpl;
import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.List;

public class NewsEntryListRepositoryImpl implements NewsEntryListRepository {
    private final SQLiteOpenHelper sqLiteOpenHelper;

    public NewsEntryListRepositoryImpl(final SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    @Override
    public List<NewsEntry> getNewsEntryList(final String rssChannelLink) throws DbException {
        try {
            final LocalNewsEntryListDataSource dataSource = new LocalNewsEntryListDataSourceImpl(sqLiteOpenHelper);
            return dataSource.getNewsEntryList(rssChannelLink);
        } catch(final SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }
}
