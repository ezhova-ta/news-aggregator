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
        final String INSERTING_ERROR_MESSAGE = "An inserting error occurred.";
        try {
            final long rowId = dataSource.addRssChannel(rssChannel);
            if(rowId == -1) {
                throw new DbException(INSERTING_ERROR_MESSAGE);
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

    @Override
    public void setRssChannelReaded(final String link, final boolean readed) throws DbException {
        try {
            dataSource.setRssChannelReaded(link, readed);
        } catch(final SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }
}
