package com.example.newsaggregator.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsaggregator.application.NewsAggregatorApplication;
import com.example.newsaggregator.model.entity.RssChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class RssChannelListDBLoader implements Callable<List<RssChannel>> {
    @Override
    public List<RssChannel> call() throws Exception {
        final SQLiteOpenHelper sqLiteOpenHelper =
                new DBHelper(NewsAggregatorApplication.getInstance().getContext());
        final SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        final List<RssChannel> rssChannelList = new ArrayList<>(10);
        RssChannel rssChannel;

        final Cursor cursor = db.query(
                DbConstants.RSS_CHANNELS_TABLE_NAME,
                new String[]{DbConstants.RSS_CHANNEL_LINK_FIELD},
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()) {
            do {
                rssChannel = new RssChannel(cursor.getString(0));
                rssChannelList.add(rssChannel);
            } while(cursor.moveToNext());
        }

        sqLiteOpenHelper.close();
        db.close();

        return rssChannelList;
    }
}
