package com.example.newsaggregator.view.news.entry;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsEntryWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, final WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        return true;
    }
}
