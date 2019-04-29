package com.example.newsaggregator.services;

import android.app.IntentService;
import android.content.Intent;

import com.example.newsaggregator.application.DBReader;
import com.example.newsaggregator.application.DBWriter;
import com.example.newsaggregator.application.RSSReaderApplication;
import com.example.newsaggregator.parser.News;
import com.example.newsaggregator.parser.XmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class MainService extends IntentService {
    public static final String ACTION_FETCH_NEWS = "com.example.gittest.services.action.FETCH_NEWS";
    public static final String EXTRA_PARAM_CHANNEL_URL = "com.example.gittest.services.extra.CHANNEL_URL";
    public static final int FETCHING_NEWS_RESULT_OK = 1;
    public static final int FETCHING_NEWS_RESULT_FAILING = -1;

    public MainService() {
        super("MainService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_NEWS.equals(action)) {
                final String channelUrl = intent.getStringExtra(EXTRA_PARAM_CHANNEL_URL);
                try {
                    handleActionFetchNews(channelUrl);
                } catch (final IOException | XmlPullParserException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    private void handleActionFetchNews(final String channelURL) throws IOException, XmlPullParserException {
        final Intent responseIntent = new Intent(ACTION_FETCH_NEWS);

        final XmlParser parser = new XmlParser(channelURL);
        final List<News> news = parser.parseXml();


        if(!news.isEmpty()) {
            final RSSReaderApplication app = RSSReaderApplication.getInstance();
            final DBWriter dbWriter = app.getDbWriter();
            final DBReader dbReader = app.getDbReader();
            long channelId = dbReader.getChannelId(channelURL);

            if(channelId == 0) {
                channelId = dbWriter.addChannel(channelURL);
            }

            for(final News elem : news) {
                elem.setChannelId(channelId);
                dbWriter.addNewsOrIgnore(elem);
            }

            responseIntent.putExtra("request_result", FETCHING_NEWS_RESULT_OK);
        } else {
            responseIntent.putExtra("request_result", FETCHING_NEWS_RESULT_FAILING);
        }

        sendBroadcast(responseIntent);
        stopSelf();
    }
}