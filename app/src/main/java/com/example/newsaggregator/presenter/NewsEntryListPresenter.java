package com.example.newsaggregator.presenter;

import android.content.Intent;

import com.example.newsaggregator.model.Repository;
import com.example.newsaggregator.view.NewsEntryListView;

public class NewsEntryListPresenter {
    private final NewsEntryListView newsEntryListView;
    private final Repository repository;

    public NewsEntryListPresenter(final NewsEntryListView newsEntryListView) {
        this.newsEntryListView = newsEntryListView;
    }

    public void onCreate() {
        /*
        TODO Показать новости из БД
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
