package com.example.newsaggregator.model.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsaggregator.application.NewsAggregatorApplication;
import com.example.newsaggregator.model.DBHelper;
import com.example.newsaggregator.model.DbConstants;
import com.example.newsaggregator.model.entity.RssChannel;

import java.util.ArrayList;
import java.util.List;

public class RssChannelListRepositoryImpl implements RssChannelListRepository {
    private final SQLiteOpenHelper sqLiteOpenHelper;
    private final SQLiteDatabase db;

    public RssChannelListRepositoryImpl() throws SQLiteException {
        sqLiteOpenHelper = new DBHelper(NewsAggregatorApplication.getInstance().getContext());
        db = sqLiteOpenHelper.getWritableDatabase();
    }

    @Override
    public List<RssChannel> getRssChannelList() throws SQLiteException {
        final List<RssChannel> rssChannelList = new ArrayList<>(10);
        RssChannel rssChannel;

        final Cursor cursor = db.query(
                DbConstants.RSS_CHANNELS_TABLE_NAME,
                new String[]{DbConstants.RSS_CHANNEL_LINK_FIELD},
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()) {
            final int linkColumnIndex = cursor.getColumnIndex(DbConstants.RSS_CHANNEL_LINK_FIELD);

            do {
                rssChannel = new RssChannel(cursor.getString(linkColumnIndex));
                rssChannelList.add(rssChannel);
            } while(cursor.moveToNext());
        }

        cursor.close();
        return rssChannelList;
    }

    @Override
    public void addRssChannel(final RssChannel rssChannel) throws SQLiteException {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(DbConstants.RSS_CHANNEL_LINK_FIELD, rssChannel.getLink());
        db.insertWithOnConflict(DbConstants.RSS_CHANNELS_TABLE_NAME, null,
                contentValues, SQLiteDatabase.CONFLICT_IGNORE);
    }

    @Override
    public void closeResources() {
        sqLiteOpenHelper.close();
        db.close();
    }
}
