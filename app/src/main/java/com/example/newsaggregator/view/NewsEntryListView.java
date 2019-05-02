package com.example.newsaggregator.view;

public interface NewsEntryListView {
    void registerReceiver(String action);
    void unregisterReceiver();
    void showNewsEntryList();
}
