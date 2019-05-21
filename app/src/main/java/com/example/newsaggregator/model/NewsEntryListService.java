package com.example.newsaggregator.model;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteException;

import com.example.newsaggregator.app.DependencyInjectionFactory;
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

    private final DependencyInjectionFactory diFactory;

    public NewsEntryListService() {
        super(NewsEntryListService.class.getSimpleName());
        diFactory = NewsAggregatorApplication.getInstance().getDiFactory();
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
            parser = diFactory.provideParser();
            final List<NewsEntry> newsEntryList = (List<NewsEntry>) parser.parse(rssChannelUrl);

            if(!newsEntryList.isEmpty()) {
                try {
                    diFactory.provideLocalNewsEntryListDataSource().addNewsEntryList(rssChannelUrl, newsEntryList);
                    responseIntent.putExtra(EXTRA_PARAM_REQUEST_RESULT, FETCHING_NEWS_ENTRY_LIST_RESULT_OK);
                } catch(final SQLiteException e) {
                    responseIntent.putExtra(EXTRA_PARAM_REQUEST_RESULT, FETCHING_NEWS_ENTRY_LIST_RESULT_FAILING);
                }
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
