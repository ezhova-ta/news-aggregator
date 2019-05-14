package com.example.newsaggregator.model.repository;

import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsaggregator.model.DbException;
import com.example.newsaggregator.model.datasource.local.LocalRssChannelDataSource;
import com.example.newsaggregator.model.datasource.local.LocalRssChannelDataSourceImpl;
import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public class RssChannelListRepositoryImpl implements RssChannelListRepository {
    private final SQLiteOpenHelper sqLiteOpenHelper;

    public RssChannelListRepositoryImpl(final SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    @Override
    public List<RssChannel> getRssChannelList() throws DbException {
        try {
            final LocalRssChannelDataSource dataSource = new LocalRssChannelDataSourceImpl(sqLiteOpenHelper);
            return dataSource.getRssChannelList();
        } catch(final SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void addRssChannel(final RssChannel rssChannel) throws DbException {
        try {
            final LocalRssChannelDataSource dataSource = new LocalRssChannelDataSourceImpl(sqLiteOpenHelper);
            final long rowId = dataSource.addRssChannel(rssChannel);
            if(rowId == -1) {
                throw new DbException("An inserting error occurred.");
            }
        } catch(final SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }
}
