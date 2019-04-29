package com.example.newsaggregator.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsaggregator.parser.News;

import java.util.ArrayList;
import java.util.List;

public class DBReader {
    private final SQLiteOpenHelper helper;
    private final SQLiteDatabase db;

    public DBReader(final Context context) {
        helper = new DBHelper(context);
        db = helper.getReadableDatabase();
    }

    public long getChannelId(final String link) {
        final Cursor cursor =
                db.query(DbConstants.RSS_CHANNELS_TABLE_NAME,
                        new String[]{DbConstants.CHANNEL_ID_FIELD},
                        DbConstants.CHANNEL_LINK_FIELD + " = " + link,
                        null, null, null, null);

        if(cursor.moveToFirst()) {
            return cursor.getLong(0);
        } else {
            return 0;
        }
    }

    public List<News> getChannelNews(final long channelId) {
        final List<News> newsList = new ArrayList<>(10);
        News news;
        final Cursor cursor = db.query(
                DbConstants.NEWS_TABLE_NAME,
                new String[]{
                        DbConstants.NEWS_TITLE_FIELD,
                        DbConstants.NEWS_LINK_FIELD,
                        DbConstants.NEWS_DESCRIPTION_FIELD,
                        DbConstants.NEWS_PUB_DATE_FIELD
                },
                DbConstants.NEWS_RSS_CHANNEL_ID_FIELS + " = " + channelId,
                null,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()) {
            do {
                news = new News(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        channelId
                );
                newsList.add(news);
            } while(cursor.moveToNext());
        }

        return newsList;
    }

    public void close() {
        helper.close();
        db.close();
    }
}
