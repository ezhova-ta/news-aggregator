package com.example.newsaggregator.presenter.news.entry.list;

public interface NewsEntryListPresenter {
    void onCreate();
    void onUpdateNewsEntryListButtonClick();
    void onReceiveBroadcastMessage(final int requestResult);
}
