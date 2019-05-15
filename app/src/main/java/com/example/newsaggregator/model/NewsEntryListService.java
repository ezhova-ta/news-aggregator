package com.example.newsaggregator.model;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsaggregator.app.NewsAggregatorApplication;
import com.example.newsaggregator.model.entity.NewsEntry;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class NewsEntryListService extends IntentService {
    public static final String ACTION_FETCH_NEWS_ENTRY_LIST =
            "com.example.newsaggregator.model.action.FETCH_NEWS_ENTRY_LIST";
    public static final String EXTRA_PARAM_RSS_CHANNEL_URL =
            "com.example.newsaggregator.model.extra.RSS_CHANNEL_URL";
    public static final String EXTRA_PARAM_REQUEST_RESULT =
            "com.example.newsaggregator.model.extra.REQUEST_RESULT";
    public static final int FETCHING_NEWS_ENTRY_LIST_RESULT_OK = 1;
    public static final int FETCHING_NEWS_ENTRY_LIST_RESULT_FAILING = -1;

    public NewsEntryListService() {
        super(NewsEntryListService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_NEWS_ENTRY_LIST.equals(action)) {
                final String channelUrl = intent.getStringExtra(EXTRA_PARAM_RSS_CHANNEL_URL);
                handleActionFetchNewsEntryList(channelUrl);
            }
        }
    }

    private void handleActionFetchNewsEntryList(final String rssChannelUrl) {
        final Intent responseIntent = new Intent(ACTION_FETCH_NEWS_ENTRY_LIST);
        final Parser parser;

        try {
            parser = NewsAggregatorApplication.getInstance().getDiFactory().provideParser();
            final List<NewsEntry> newsEntryList = (List<NewsEntry>) parser.parse(rssChannelUrl);

            if(!newsEntryList.isEmpty()) {
                final SQLiteOpenHelper sqLiteOpenHelper =
                        NewsAggregatorApplication.getInstance().getDiFactory().provideSQLiteOpenHelper();
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

                responseIntent.putExtra(EXTRA_PARAM_REQUEST_RESULT, FETCHING_NEWS_ENTRY_LIST_RESULT_OK);
            } else {
                responseIntent.putExtra(EXTRA_PARAM_REQUEST_RESULT, FETCHING_NEWS_ENTRY_LIST_RESULT_FAILING);
            }
        } catch (final XmlPullParserException | IOException | SQLiteException e) {
            e.printStackTrace(System.err);
            responseIntent.putExtra(EXTRA_PARAM_REQUEST_RESULT, FETCHING_NEWS_ENTRY_LIST_RESULT_FAILING);
        }

        sendBroadcast(responseIntent);
        stopSelf();
    }
}
