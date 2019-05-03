package com.example.newsaggregator.presenter.news_entry_list;

import android.content.Intent;

import com.example.newsaggregator.model.Repository;
import com.example.newsaggregator.model.RepositoryImpl;
import com.example.newsaggregator.view.news_entry_list.NewsEntryListView;

public class NewsEntryListPresenter {
    private final NewsEntryListView newsEntryListView;
    private final Repository repository;

    public NewsEntryListPresenter(final NewsEntryListView newsEntryListView) {
        this.newsEntryListView = newsEntryListView;
        repository = new RepositoryImpl();
    }

    public void onCreate() {
        /*
        TODO Показать список новостей из БД
         */
//        newsEntryListView.registerReceiver(NewsEntryListService.ACTION_FETCH_NEWS);
    }

    public void onDestroy() {
        newsEntryListView.unregisterReceiver();
        newsEntryListView.resetPresenter();
    }

    public void onUpdateNewsEntryListButtonClick() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void onReceivedBroadcastMessage(final Intent intent) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
