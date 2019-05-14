package com.example.newsaggregator.view.news.entry.list;

import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.List;

public interface NewsEntryListView {
    int FETCHING_NEWS_ENTRY_LIST_DEFAULT_RESULT = 0;
    String NEWS_ENTRY_LINK_EXTRA_KEY = "com.example.newsaggregator.view.news_entry_list.NEWS_ENTRY_LINK";
    void showNewsEntryList(final List<NewsEntry> newsEntryList);
    void startServiceToUpdateNewsEntryList();
    String getRssChannelLink();
    void showPopupMessage(String text);
    void startActivityToDisplayNewsEntry(Class<?> activityClass, String newsEntryLinkExtraKey,
            String newsEntryLinkExtraValue);
}
