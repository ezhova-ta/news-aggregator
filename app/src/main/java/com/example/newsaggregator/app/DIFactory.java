package com.example.newsaggregator.app;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.webkit.WebViewClient;

import com.example.newsaggregator.model.DBHelper;
import com.example.newsaggregator.model.DbConstants;
import com.example.newsaggregator.model.Parser;
import com.example.newsaggregator.model.XmlParser;
import com.example.newsaggregator.model.datasource.local.LocalNewsEntryListDataSource;
import com.example.newsaggregator.model.datasource.local.LocalNewsEntryListDataSourceImpl;
import com.example.newsaggregator.model.datasource.local.LocalRssChannelDataSource;
import com.example.newsaggregator.model.datasource.local.LocalRssChannelDataSourceImpl;
import com.example.newsaggregator.model.entity.NewsEntry;
import com.example.newsaggregator.model.repository.NewsEntryListRepository;
import com.example.newsaggregator.model.repository.NewsEntryListRepositoryImpl;
import com.example.newsaggregator.model.repository.RssChannelListRepository;
import com.example.newsaggregator.model.repository.RssChannelListRepositoryImpl;
import com.example.newsaggregator.view.news.entry.NewsEntryWebViewClient;

import java.util.List;

public class DIFactory {
    private final Context context;

    public DIFactory(final Context context) {
        this.context = context;
    }

    public SQLiteOpenHelper provideSQLiteOpenHelper() {
        /*
        TODO Избавиться от magic const
         */
        return new DBHelper(context, DbConstants.DB_NAME, null, 1);
    }

    public LocalRssChannelDataSource provideLocalRssChannelDataSource() {
        return new LocalRssChannelDataSourceImpl(provideSQLiteOpenHelper());
    }

    public LocalNewsEntryListDataSource provideLocalNewsEntryListDataSource() {
        return new LocalNewsEntryListDataSourceImpl(provideSQLiteOpenHelper());
    }

    public RssChannelListRepository provideRssChannelListRepository() {
        return new RssChannelListRepositoryImpl(provideLocalRssChannelDataSource());
    }

    public NewsEntryListRepository provideNewsEntryListRepository() {
        return new NewsEntryListRepositoryImpl(provideLocalNewsEntryListDataSource());
    }

    public Parser<List<NewsEntry>> provideParser() {
        return new XmlParser();
    }

    public WebViewClient provideWebViewClient() {
        return new NewsEntryWebViewClient();
    }

    /*
    TODO Экземпляры презентеров создаются в активити.
     */
}
