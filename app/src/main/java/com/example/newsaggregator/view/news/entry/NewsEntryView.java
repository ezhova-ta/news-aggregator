package com.example.newsaggregator.view.news.entry;

import android.webkit.WebViewClient;

public interface NewsEntryView {
    void showNewsEntry(WebViewClient webViewClient, String newsEntryLink);
    void showProgress();
    void hideProgress();
}
