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
import com.example.newsaggregator.presenter.channel.list.RssChannelListPresenter;
import com.example.newsaggregator.presenter.channel.list.RssChannelListPresenterImpl;
import com.example.newsaggregator.presenter.channel.list.deleting.DeletingRssChannelListPresenter;
import com.example.newsaggregator.presenter.channel.list.deleting.DeletingRssChannelListPresenterImpl;
import com.example.newsaggregator.presenter.news.entry.NewsEntryPresenter;
import com.example.newsaggregator.presenter.news.entry.NewsEntryPresenterImpl;
import com.example.newsaggregator.presenter.news.entry.list.NewsEntryListPresenter;
import com.example.newsaggregator.presenter.news.entry.list.NewsEntryListPresenterImpl;
import com.example.newsaggregator.view.channel.list.OnRssChannelListItemClickListener;
import com.example.newsaggregator.view.channel.list.RssChannelListView;
import com.example.newsaggregator.view.channel.list.deleting.DeletingRssChannelListView;
import com.example.newsaggregator.view.channel.list.deleting.OnRssChannelListItemCheckListener;
import com.example.newsaggregator.view.news.entry.NewsEntryView;
import com.example.newsaggregator.view.news.entry.NewsEntryWebViewClient;
import com.example.newsaggregator.view.news.entry.list.NewsEntryListView;
import com.example.newsaggregator.view.news.entry.list.OnNewsEntryListItemClickListener;

import java.util.List;

public class DependencyInjectionFactory {
    private final Context context;

    public DependencyInjectionFactory(final Context context) {
        this.context = context;
    }

    public SQLiteOpenHelper provideSQLiteOpenHelper() {
        return DBHelper.getInstance(context);
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

    public RssChannelListPresenter provideRssChannelListPresenter(final RssChannelListView view) {
        return new RssChannelListPresenterImpl(view, provideRssChannelListRepository());
    }

    public OnRssChannelListItemClickListener provideOnRssChannelListItemClickListener(final RssChannelListView view) {
        return new RssChannelListPresenterImpl(view, provideRssChannelListRepository());
    }

    public NewsEntryListPresenter provideNewsEntryListPresenter(final NewsEntryListView view) {
        return new NewsEntryListPresenterImpl(view, provideNewsEntryListRepository());
    }

    public OnNewsEntryListItemClickListener provideOnNewsEntryListItemClickListener(final NewsEntryListView view) {
        return new NewsEntryListPresenterImpl(view, provideNewsEntryListRepository());
    }

    public NewsEntryPresenter provideNewsEntryPresenter(final NewsEntryView view) {
        return new NewsEntryPresenterImpl(view);
    }

    public DeletingRssChannelListPresenter provideDeletingRssChannelListPresenter(final DeletingRssChannelListView view) {
        return new DeletingRssChannelListPresenterImpl(view, provideRssChannelListRepository());
    }

    public OnRssChannelListItemCheckListener provideOnRssChannelListItemCheckListener(final DeletingRssChannelListView view) {
        return new DeletingRssChannelListPresenterImpl(view, provideRssChannelListRepository());
    }

    public WebViewClient provideWebViewClient() {
        return new NewsEntryWebViewClient();
    }
}
