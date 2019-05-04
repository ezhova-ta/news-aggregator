package com.example.newsaggregator.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsaggregator.application.NewsAggregatorApplication;

class RssChannelSaver implements Runnable {
    private final String rssChannelLink;

    RssChannelSaver(final String rssChannelLink) {
        this.rssChannelLink = rssChannelLink;
    }

    @Override
    public void run() {
        final SQLiteOpenHelper sqLiteOpenHelper =
                new DBHelper(NewsAggregatorApplication.getInstance().getContext());
        final SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        contentValues.put(DbConstants.RSS_CHANNEL_LINK_FIELD, rssChannelLink);
        db.insertWithOnConflict(DbConstants.RSS_CHANNELS_TABLE_NAME, null,
                contentValues, SQLiteDatabase.CONFLICT_IGNORE);
    }
}
