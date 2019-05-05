package com.example.newsaggregator.view.news_entry_list;

import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.List;

public interface NewsEntryListView {
    void registerReceiver(String action);
    void unregisterReceiver();
    void showNewsEntryList(final List<NewsEntry> newsEntryList);
    void resetPresenter();
}
