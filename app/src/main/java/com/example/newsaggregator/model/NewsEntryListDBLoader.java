package com.example.newsaggregator.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsaggregator.application.NewsAggregatorApplication;
import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class NewsEntryListDBLoader implements Callable<List<NewsEntry>> {
    private final String rssChannelLink;
    private final SQLiteOpenHelper sqLiteOpenHelper;
    private final SQLiteDatabase db;

    public NewsEntryListDBLoader(final String rssChannelLink) {
        this.rssChannelLink = rssChannelLink;
        sqLiteOpenHelper = new DBHelper(NewsAggregatorApplication.getInstance().getContext());
        db = sqLiteOpenHelper.getReadableDatabase();
    }

    @Override
    public List<NewsEntry> call() throws Exception {
        final List<NewsEntry> newsEntryList = new ArrayList<>(10);
        NewsEntry newsEntry;

        final String sqlQuery =
                "select * from " + DbConstants.NEWS_ENTRIES_TABLE_NAME +
                " inner join " + DbConstants.RSS_CHANNELS_TABLE_NAME +
                " on " + DbConstants.NEWS_ENTRIES_TABLE_NAME + "." + DbConstants.NEWS_ENTRY_RSS_CHANNEL_ID_FIELD +
                " = " + DbConstants.RSS_CHANNELS_TABLE_NAME + "." + DbConstants.RSS_CHANNEL_ID_FIELD +
                " where " + DbConstants.RSS_CHANNELS_TABLE_NAME + "." + DbConstants.RSS_CHANNEL_LINK_FIELD + " = ?";

        final Cursor cursor = db.rawQuery(sqlQuery, new String[]{rssChannelLink});

        if(cursor.moveToFirst()) {
            do {
                newsEntry = new NewsEntry(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                newsEntryList.add(newsEntry);
            } while(cursor.moveToNext());
        }

        sqLiteOpenHelper.close();
        db.close();
        return newsEntryList;
    }
}
