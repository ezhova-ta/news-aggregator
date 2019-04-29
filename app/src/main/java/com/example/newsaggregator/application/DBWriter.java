package com.example.newsaggregator.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsaggregator.db.DBHelper;
import com.example.newsaggregator.db.DbConstants;
import com.example.newsaggregator.parser.News;

public class DBWriter {
    private static DBWriter instance;
    private SQLiteOpenHelper helper;
    private SQLiteDatabase db;
    private ContentValues contentValues;

    private DBWriter(final Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public static DBWriter getInstance(final Context context) {
        if(instance == null) {
            instance = new DBWriter(context);
        }
        return instance;
    }

    public long addChannel(final String link) {
        contentValues = new ContentValues();
        contentValues.put(DbConstants.CHANNEL_LINK_FIELD, link);
        return db.insert(DbConstants.RSS_CHANNELS_TABLE_NAME, null, contentValues);
    }

    public long addNews(final News news) {
        contentValues = new ContentValues();
        contentValues.put(DbConstants.NEWS_TITLE_FIELD, news.getTitle());
        contentValues.put(DbConstants.NEWS_LINK_FIELD, news.getLink());
        contentValues.put(DbConstants.NEWS_DESCRIPTION_FIELD, news.getDescription());
        contentValues.put(DbConstants.NEWS_PUB_DATE_FIELD, news.getPubDate());
        contentValues.put(DbConstants.NEWS_RSS_CHANNEL_ID_FIELS, news.getChannelId());
        return db.insert(DbConstants.NEWS_TABLE_NAME, null, contentValues);
    }

    public int removeChannel(final long id) {
        return db.delete(DbConstants.RSS_CHANNELS_TABLE_NAME,
                DbConstants.CHANNEL_ID_FIELD + " = " + id, null);
    }

    public int removeChannel(final String link) {
        return db.delete(DbConstants.RSS_CHANNELS_TABLE_NAME,
                DbConstants.CHANNEL_LINK_FIELD + " = " + link, null);
    }

    public int removeNews(final long id) {
        return db.delete(DbConstants.NEWS_TABLE_NAME,
                DbConstants.NEWS_ID_FIELD + " = " + id, null);
    }

    public int removeNews(final String title) {
        return db.delete(DbConstants.NEWS_TABLE_NAME,
                DbConstants.NEWS_TITLE_FIELD + " = " + title, null);
    }

    public int removeChannelNews(final long channelId) {
        return db.delete(DbConstants.NEWS_TABLE_NAME,
                DbConstants.NEWS_RSS_CHANNEL_ID_FIELS + " = " + channelId, null);
    }

    public void close() {
        helper.close();
        db.close();
    }
}
