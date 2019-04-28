package com.example.newsaggregator.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(final Context context) {
        super(context, "rss_news_reader", null, 1);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("create table rss_channels (id integer primary key autoincrement, link text)");
        db.execSQL("create table news (id integer primary key autoincrement, title text, " +
                "link text, description text, pub_date text, rss_channel_id integer not null, " +
                "foreign key (rss_channel_id) references rss_channels(id))");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {}
}
