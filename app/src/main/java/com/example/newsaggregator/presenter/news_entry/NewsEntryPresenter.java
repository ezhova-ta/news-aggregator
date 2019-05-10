package com.example.newsaggregator.presenter.news_entry;

import com.example.newsaggregator.view.news_entry.NewsEntryView;

public class NewsEntryPresenter {
    private final NewsEntryView newsEntryView;

    public NewsEntryPresenter(final NewsEntryView newsEntryView) {
        this.newsEntryView = newsEntryView;
    }

    public void onCreate(final String newsEntryLink) {
        newsEntryView.showNewsEntry(newsEntryLink);
    }
}
