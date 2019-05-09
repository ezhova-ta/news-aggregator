package com.example.newsaggregator.view.news_entry_list;

import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.List;

public interface NewsEntryListView {
    int FETCHING_NEWS_ENTRY_LIST_DEFAULT_RESULT = 0;
    void showNewsEntryList(final List<NewsEntry> newsEntryList);
    void startServiceToUpdateNewsEntryList();
    String getRssChannelLink();
}
