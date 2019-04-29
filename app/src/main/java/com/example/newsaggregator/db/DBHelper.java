package com.example.newsaggregator.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(final Context context) {
        super(context, DbConstants.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(
                "create table " + DbConstants.RSS_CHANNELS_TABLE_NAME +
                " (" + DbConstants.CHANNEL_ID_FIELD + " integer primary key autoincrement, " +
                DbConstants.CHANNEL_LINK_FIELD + " text)");
        db.execSQL(
                "create table " + DbConstants.NEWS_TABLE_NAME +
                " (" + DbConstants.NEWS_ID_FIELD + " integer primary key autoincrement, " +
                DbConstants.NEWS_TITLE_FIELD + " text, " +
                DbConstants.NEWS_LINK_FIELD + " text, " +
                DbConstants.NEWS_DESCRIPTION_FIELD + " text, " +
                DbConstants.NEWS_PUB_DATE_FIELD + " text, " +
                DbConstants.NEWS_RSS_CHANNEL_ID_FIELS + " integer not null, " +
                "unique (" + DbConstants.NEWS_LINK_FIELD + ")," +
                "foreign key (" + DbConstants.NEWS_RSS_CHANNEL_ID_FIELS + ") references " +
                DbConstants.RSS_CHANNELS_TABLE_NAME + "(id))");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {}
}
