package com.example.newsaggregator.presenter.news.entry;

import com.example.newsaggregator.app.NewsAggregatorApplication;
import com.example.newsaggregator.view.news.entry.NewsEntryView;

public class NewsEntryPresenterImpl implements NewsEntryPresenter {
    private final NewsEntryView newsEntryView;

    public NewsEntryPresenterImpl(final NewsEntryView newsEntryView) {
        this.newsEntryView = newsEntryView;
    }

    @Override
    public void onCreate(final String newsEntryLink) {
        newsEntryView.showNewsEntry(
                NewsAggregatorApplication.getInstance().getDiFactory().provideWebViewClient(),
                newsEntryLink
        );
    }
}
