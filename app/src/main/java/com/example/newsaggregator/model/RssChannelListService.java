package com.example.newsaggregator.model;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteException;

import com.example.newsaggregator.app.DependencyInjectionFactory;
import com.example.newsaggregator.app.NewsAggregatorApplication;
import com.example.newsaggregator.model.datasource.local.LocalNewsEntryListDataSource;
import com.example.newsaggregator.model.datasource.local.LocalRssChannelDataSource;
import com.example.newsaggregator.model.entity.NewsEntry;
import com.example.newsaggregator.model.entity.RssChannel;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class RssChannelListService extends IntentService {
    public static final String ACTION_UPDATE_NEWS_ENTRY_LISTS =
            "com.example.newsaggregator.model.action.UPDATE_NEWS_ENTRY_LISTS";
    public static final String EXTRA_PARAM_REQUEST_RESULT =
            "com.example.newsaggregator.model.extra.REQUEST_RESULT";
    public static final int UPDATING_NEWS_ENTRY_LISTS_RESULT_FRESH_NEWS_ENTRIES = 1;
    public static final int UPDATING_NEWS_ENTRY_LISTS_RESULT_NO_FRESH_NEWS_ENTRIES = 2;
    public static final int UPDATING_NEWS_ENTRY_LISTS_RESULT_FAILING = -1;
    private final DependencyInjectionFactory diFactory;

    public RssChannelListService() {
        super("RssChannelListService");
        diFactory = NewsAggregatorApplication.getInstance().getDiFactory();
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_NEWS_ENTRY_LISTS.equals(action)) {
                handleActionUpdateNewsEntryLists();
            }
        }
    }

    private void handleActionUpdateNewsEntryLists() {
        final LocalNewsEntryListDataSource newsEntryListDataSource = diFactory.provideLocalNewsEntryListDataSource();
        final LocalRssChannelDataSource rssChannelDataSource = diFactory.provideLocalRssChannelDataSource();
        final Intent responseIntent = new Intent(ACTION_UPDATE_NEWS_ENTRY_LISTS);
        final Parser parser;
        final List<RssChannel> rssChannelList;
        List<NewsEntry> newsEntryList;
        boolean isUpdated = false;

        try {
            parser = diFactory.provideParser();
            rssChannelList = diFactory.provideLocalRssChannelDataSource().getRssChannelList();

            for(final RssChannel rssChannel : rssChannelList) {
                newsEntryList = (List<NewsEntry>) parser.parse(rssChannel.getLink());

                if(!newsEntryList.isEmpty()) {
                    if(newsEntryListDataSource.addNewsEntryList(rssChannel.getLink(), newsEntryList)) {
                        isUpdated = true;
                        rssChannelDataSource.setRssChannelReaded(rssChannel.getLink(), false);
                    }
                }
            }

            if(isUpdated) {
                responseIntent.putExtra(EXTRA_PARAM_REQUEST_RESULT, UPDATING_NEWS_ENTRY_LISTS_RESULT_FRESH_NEWS_ENTRIES);
            } else {
                responseIntent.putExtra(EXTRA_PARAM_REQUEST_RESULT, UPDATING_NEWS_ENTRY_LISTS_RESULT_NO_FRESH_NEWS_ENTRIES);
            }
        } catch(final XmlPullParserException | IOException | SQLiteException e) {
            responseIntent.putExtra(EXTRA_PARAM_REQUEST_RESULT, UPDATING_NEWS_ENTRY_LISTS_RESULT_FAILING);
        }

        sendBroadcast(responseIntent);
        stopSelf();
    }
}
