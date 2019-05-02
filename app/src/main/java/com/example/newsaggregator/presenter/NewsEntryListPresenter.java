package com.example.newsaggregator.presenter;

import android.content.Intent;

import com.example.newsaggregator.view.NewsEntryListView;

public class NewsEntryListPresenter {
    private final NewsEntryListView newsEntryListView;

    public NewsEntryListPresenter(final NewsEntryListView newsEntryListView) {
        this.newsEntryListView = newsEntryListView;
    }

    public void onCreate() {
//        newsEntryListView.registerReceiver(NewsEntryListService.ACTION_FETCH_NEWS);
    }

    public void onDestroy() {
        newsEntryListView.unregisterReceiver();
    }

    public void onUpdateNewsEntryListButtonClick() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void onReceivedBroadcastMessage(final Intent intent) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
