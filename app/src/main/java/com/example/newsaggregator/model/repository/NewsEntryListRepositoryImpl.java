package com.example.newsaggregator.model.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsaggregator.application.NewsAggregatorApplication;
import com.example.newsaggregator.model.DBHelper;
import com.example.newsaggregator.model.DbConstants;
import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.ArrayList;
import java.util.List;

public class NewsEntryListRepositoryImpl implements NewsEntryListRepository {
    @Override
    public List<NewsEntry> getNewsEntryList(final String rssChannelLink) {
        /*
        TODO Пока просто получение новостей канала из БД
         */

        final SQLiteOpenHelper sqLiteOpenHelper =
                new DBHelper(NewsAggregatorApplication.getInstance().getContext());
        final SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();

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
            final int titleColumnIndex = cursor.getColumnIndex(DbConstants.NEWS_ENTRY_TITLE_FIELD);
            final int linkColumnIndex = cursor.getColumnIndex(DbConstants.NEWS_ENTRY_LINK_FIELD);
            final int descriptionColumnIndex = cursor.getColumnIndex(DbConstants.NEWS_ENTRY_DESCRIPTION_FIELD);
            final int pubDateColumnIndex = cursor.getColumnIndex(DbConstants.NEWS_ENTRY_PUB_DATE_FIELD);

            do {
                newsEntry = new NewsEntry(
                        cursor.getString(titleColumnIndex),
                        cursor.getString(linkColumnIndex),
                        cursor.getString(descriptionColumnIndex),
                        cursor.getString(pubDateColumnIndex)
                );
                newsEntryList.add(newsEntry);
            } while(cursor.moveToNext());
        }

        cursor.close();
        sqLiteOpenHelper.close();
        db.close();

        return newsEntryList;
    }
}
