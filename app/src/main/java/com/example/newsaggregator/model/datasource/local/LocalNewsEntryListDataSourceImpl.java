package com.example.newsaggregator.model.datasource.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsaggregator.model.DbConstants;
import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LocalNewsEntryListDataSourceImpl implements LocalNewsEntryListDataSource {
    private final SQLiteOpenHelper sqLiteOpenHelper;

    public LocalNewsEntryListDataSourceImpl(final SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    @Override
    public List<NewsEntry> getNewsEntryList(final String rssChannelLink) throws SQLiteException {
        final SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        final List<NewsEntry> newsEntryList = new ArrayList<>(10);
        NewsEntry newsEntry;

        final String sqlQuery =
                "select * from " + DbConstants.NEWS_ENTRIES_TABLE_NAME +
                " inner join " + DbConstants.RSS_CHANNELS_TABLE_NAME +
                " on " + DbConstants.NEWS_ENTRIES_TABLE_NAME + "." + DbConstants.NEWS_ENTRY_RSS_CHANNEL_ID_FIELD +
                " = " + DbConstants.RSS_CHANNELS_TABLE_NAME + "." + DbConstants.RSS_CHANNEL_ID_FIELD +
                " where " + DbConstants.RSS_CHANNELS_TABLE_NAME + "." + DbConstants.RSS_CHANNEL_LINK_FIELD + " = ?" +
                " order by " + DbConstants.NEWS_ENTRIES_TABLE_NAME + "." + DbConstants.NEWS_ENTRY_PUB_DATE_FIELD + " desc";

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
                        cursor.getLong(pubDateColumnIndex)
                );
                newsEntryList.add(newsEntry);
            } while(cursor.moveToNext());
        }

        cursor.close();
        sqLiteOpenHelper.close();
        db.close();

        return newsEntryList;
    }

    @Override
    public void addNewsEntryList(final String rssChannelUrl, final List<NewsEntry> newsEntryList) throws SQLiteException {
        final SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        final long rssChannelId;
        ContentValues contentValues;

        final Cursor cursor = db.query(
                DbConstants.RSS_CHANNELS_TABLE_NAME,
                new String[]{DbConstants.RSS_CHANNEL_ID_FIELD},
                DbConstants.RSS_CHANNEL_LINK_FIELD + " = ?",
                new String[]{rssChannelUrl},
                null,
                null,
                null
        );

        if(cursor.moveToFirst()) {
            final int idColumnIndex = cursor.getColumnIndex(DbConstants.RSS_CHANNEL_ID_FIELD);
            rssChannelId = cursor.getLong(idColumnIndex);
            cursor.close();
        } else {
            contentValues = new ContentValues();
            contentValues.put(DbConstants.RSS_CHANNEL_LINK_FIELD, rssChannelUrl);
            rssChannelId = db.insert(DbConstants.RSS_CHANNELS_TABLE_NAME, null, contentValues);
        }

        for(final NewsEntry elem : newsEntryList) {
            contentValues = new ContentValues();
            contentValues.put(DbConstants.NEWS_ENTRY_TITLE_FIELD, elem.getTitle());
            contentValues.put(DbConstants.NEWS_ENTRY_LINK_FIELD, elem.getLink());
            contentValues.put(DbConstants.NEWS_ENTRY_DESCRIPTION_FIELD, elem.getDescription());
            contentValues.put(DbConstants.NEWS_ENTRY_PUB_DATE_FIELD, elem.getPubDate());
            contentValues.put(DbConstants.NEWS_ENTRY_RSS_CHANNEL_ID_FIELD, rssChannelId);
            db.insertWithOnConflict(DbConstants.NEWS_ENTRIES_TABLE_NAME, null,
                    contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        }

        sqLiteOpenHelper.close();
        db.close();
    }

    @Override
    public int deleteOutdatedNewsEntries() throws SQLiteException {
        final long currentDateInMillis = Calendar.getInstance().getTimeInMillis();
        final long storagePeriodInMillis = DbConstants.NEWS_ENTRY_STORAGE_PERIOD_IN_MILLIS;
        final SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        final int rowCount = db.delete(
                DbConstants.NEWS_ENTRIES_TABLE_NAME,
                DbConstants.NEWS_ENTRY_PUB_DATE_FIELD + " < ?",
                new String[]{Long.toString(currentDateInMillis - storagePeriodInMillis)}
        );

        sqLiteOpenHelper.close();
        db.close();

        return rowCount;
    }
}
