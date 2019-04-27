package com.example.newsaggregator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsaggregator.parser.News;

public class DBWriter {
    private SQLiteOpenHelper helper;
    private SQLiteDatabase db;
    private ContentValues contentValues;

    public DBWriter(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public long getChannelId(String link) {
        final Cursor cursor = db.query("rss_channels", new String[]{"id"}, "link = ?",
                new String[]{link}, null, null, null);
        if(cursor.moveToFirst()) {
            return cursor.getLong(0);
        } else {
            return 0;
        }
    }

    public long addChannel(String link) {
        contentValues = new ContentValues();
        contentValues.put("link", link);
        return db.insert("rss_channels", null, contentValues);
    }

    public long addNews(News news) {
        contentValues = new ContentValues();
        contentValues.put("title", news.getTitle());
        contentValues.put("link", news.getLink());
        contentValues.put("description", news.getDescription());
        contentValues.put("pub_date", news.getPubDate());
        contentValues.put("rss_channel_id", news.getChannelId());
        return db.insert("news", null, contentValues);
    }

    public int removeChannel(long id) {
        return db.delete("rss_channels", "id = " + id, null);
    }

    public int removeChannel(String link) {
        return db.delete("rss_channels", "link = " + link, null);
    }

    public int removeNews(long id) {
        return db.delete("news", "id = " + id, null);
    }

    public int removeNews(String title) {
        return db.delete("news", "title = " + title, null);
    }

    public int removeChannelNews(long channelId) {
        return db.delete("news", "rss_channel_id = " + channelId, null);
    }

    public void close() {
        helper.close();
        db.close();
    }
}
