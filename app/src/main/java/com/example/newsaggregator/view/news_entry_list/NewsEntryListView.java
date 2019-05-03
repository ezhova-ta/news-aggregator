package com.example.newsaggregator.view.news_entry_list;

public interface NewsEntryListView {
    void registerReceiver(String action);
    void unregisterReceiver();
    void showNewsEntryList();
    void resetPresenter();
}
