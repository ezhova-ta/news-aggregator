package com.example.newsaggregator.presenter.news.entry;

import com.example.newsaggregator.view.news.entry.NewsEntryView;
import com.example.newsaggregator.view.news.entry.NewsEntryWebViewClient;

public class NewsEntryPresenter {
    private final NewsEntryView newsEntryView;

    public NewsEntryPresenter(final NewsEntryView newsEntryView) {
        this.newsEntryView = newsEntryView;
    }

    public void onCreate(final String newsEntryLink) {
        newsEntryView.showNewsEntry(new NewsEntryWebViewClient(), newsEntryLink);
    }
}
