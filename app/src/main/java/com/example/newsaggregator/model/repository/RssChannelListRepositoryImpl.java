package com.example.newsaggregator.model.repository;

import android.database.sqlite.SQLiteException;

import com.example.newsaggregator.model.DbException;
import com.example.newsaggregator.model.datasource.local.LocalRssChannelDataSource;
import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public class RssChannelListRepositoryImpl implements RssChannelListRepository {
    private final LocalRssChannelDataSource dataSource;

    public RssChannelListRepositoryImpl(final LocalRssChannelDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<RssChannel> getRssChannelList() throws DbException {
        try {
            return dataSource.getRssChannelList();
        } catch(final SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void addRssChannel(final RssChannel rssChannel) throws DbException {
        try {
            final long rowId = dataSource.addRssChannel(rssChannel);
            if(rowId == -1) {
                throw new DbException("An inserting error occurred.");
            }
        } catch(final SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteRssChannel(final String link) throws DbException {
        try {
            dataSource.deleteRssChannel(link);
        } catch(final SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }
}
