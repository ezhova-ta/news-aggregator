package com.example.newsaggregator.view.news_entry;

import android.webkit.WebViewClient;

public interface NewsEntryView {
    void showNewsEntry(WebViewClient webViewClient, String newsEntryLink);
}
