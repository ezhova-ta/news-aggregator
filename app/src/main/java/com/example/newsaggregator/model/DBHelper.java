package com.example.newsaggregator.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(final Context context, final String dbName,
                    final SQLiteDatabase.CursorFactory factory, final int version) {
        super(context, dbName, factory, version);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(
                "create table if not exists " + DbConstants.RSS_CHANNELS_TABLE_NAME +
                " (" + DbConstants.RSS_CHANNEL_ID_FIELD + " integer primary key autoincrement, " +
                DbConstants.RSS_CHANNEL_LINK_FIELD + " text," +
                "unique (" + DbConstants.RSS_CHANNEL_LINK_FIELD + "))"
        );
        db.execSQL(
                "create table if not exists " + DbConstants.NEWS_ENTRIES_TABLE_NAME +
                " (" + DbConstants.NEWS_ENTRY_ID_FIELD + " integer primary key autoincrement, " +
                DbConstants.NEWS_ENTRY_TITLE_FIELD + " text, " +
                DbConstants.NEWS_ENTRY_LINK_FIELD + " text, " +
                DbConstants.NEWS_ENTRY_DESCRIPTION_FIELD + " text, " +
                DbConstants.NEWS_ENTRY_PUB_DATE_FIELD + " text, " +
                DbConstants.NEWS_ENTRY_RSS_CHANNEL_ID_FIELD + " integer not null, " +
                "unique (" + DbConstants.NEWS_ENTRY_LINK_FIELD + ")," +
                "foreign key (" + DbConstants.NEWS_ENTRY_RSS_CHANNEL_ID_FIELD + ") references " +
                DbConstants.RSS_CHANNELS_TABLE_NAME + "(id))"
        );
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {}
}
