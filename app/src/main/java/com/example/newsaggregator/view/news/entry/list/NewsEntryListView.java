package com.example.newsaggregator.view.news.entry.list;

import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.List;

public interface NewsEntryListView {
    int FETCHING_NEWS_ENTRY_LIST_DEFAULT_RESULT = 0;
    String NEWS_ENTRY_LINK_EXTRA_KEY = "com.example.newsaggregator.view.news_entry_list.NEWS_ENTRY_LINK";
    String PREFERENCES_FILE_NAME = "news_entries";
    String PREFERENCE_NAME = "newsEntriesDetetionDate";
    long DEFAULT_PREFERENCE_VALUE = 0;

    void showNewsEntryList(final List<NewsEntry> newsEntryList);
    void startServiceToUpdateNewsEntryList();
    String getRssChannelLink();
    void startActivityToDisplayNewsEntry(String newsEntryLinkExtraKey,
            String newsEntryLinkExtraValue);
    long getNewsEntriesDetetionDateInMillis();
    void setNewsEntriesDetetionDate(long newsEntriesDetetionDateInMillis);
    void showProgressBar();
    void hideProgressBar();
    void showNewsEntriesUpdatingErrorMessage();
    void showNewsEntriesLoadingErrorMessage();
    void showEmptyNewsEntryListMessage();
}
