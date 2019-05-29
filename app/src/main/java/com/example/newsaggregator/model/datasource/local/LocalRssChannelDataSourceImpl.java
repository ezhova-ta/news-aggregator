package com.example.newsaggregator.model.datasource.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsaggregator.model.DbConstants;
import com.example.newsaggregator.model.entity.RssChannel;

import java.util.ArrayList;
import java.util.List;

public class LocalRssChannelDataSourceImpl implements LocalRssChannelDataSource {
    private final SQLiteOpenHelper sqLiteOpenHelper;

    public LocalRssChannelDataSourceImpl(final SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    @Override
    public List<RssChannel> getRssChannelList() throws SQLiteException {
        final SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        final List<RssChannel> rssChannelList = new ArrayList<>(10);
        RssChannel rssChannel;

        final Cursor cursor = db.query(
                DbConstants.RSS_CHANNELS_TABLE_NAME,
                new String[]{DbConstants.RSS_CHANNEL_LINK_FIELD, DbConstants.RSS_CHANNEL_READED_FIELD},
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()) {
            final int linkColumnIndex = cursor.getColumnIndex(DbConstants.RSS_CHANNEL_LINK_FIELD);
            final int readedColumnIndex = cursor.getColumnIndex(DbConstants.RSS_CHANNEL_READED_FIELD);

            do {
                rssChannel = new RssChannel(
                        cursor.getString(linkColumnIndex),
                        /*
                        TODO magic const
                         */
                        cursor.getInt(readedColumnIndex) == 1 ? true : false
                );
                rssChannelList.add(rssChannel);
            } while(cursor.moveToNext());
        }

        cursor.close();
        sqLiteOpenHelper.close();
        db.close();

        return rssChannelList;
    }

    @Override
    public long addRssChannel(final RssChannel rssChannel) throws SQLiteException {
        final SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();

        contentValues.put(DbConstants.RSS_CHANNEL_LINK_FIELD, rssChannel.getLink());
        final long rowId = db.insertWithOnConflict(
                DbConstants.RSS_CHANNELS_TABLE_NAME,
                null,
                contentValues,
                SQLiteDatabase.CONFLICT_IGNORE
        );

        sqLiteOpenHelper.close();
        db.close();

        return rowId;
    }

    @Override
    public int deleteRssChannel(final String link) throws SQLiteException {
        final SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        final int rowNumber = db.delete(
                DbConstants.RSS_CHANNELS_TABLE_NAME,
                DbConstants.RSS_CHANNEL_LINK_FIELD + " = ?",
                new String[]{link}
        );

        sqLiteOpenHelper.close();
        db.close();

        return rowNumber;
    }

    @Override
    public int setRssChannelReaded(final String link, final boolean readed) throws SQLiteException {
        final SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();

        /*
        TODO magic const
         */
        contentValues.put(DbConstants.RSS_CHANNEL_READED_FIELD, readed ? 1 : 0);
        final int rowNumber = db.update(
                DbConstants.RSS_CHANNELS_TABLE_NAME,
                contentValues,
                DbConstants.RSS_CHANNEL_LINK_FIELD + " = ?",
                new String[]{link}
        );

        sqLiteOpenHelper.close();
        db.close();

        return rowNumber;
    }
}
