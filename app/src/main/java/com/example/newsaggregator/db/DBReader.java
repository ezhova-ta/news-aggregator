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
        final Cursor cursor = db.query("rss_channels", new String[]{"id"}, "link = ?",
                new String[]{link}, null, null, null);
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
                "news",
                new String[]{"title", "link", "description", "pub_date"},
                "rss_channel_id = ?",
                new String[]{Long.toString(channelId)},
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
