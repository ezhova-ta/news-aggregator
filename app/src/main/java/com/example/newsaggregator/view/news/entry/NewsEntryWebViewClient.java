package com.example.newsaggregator.view.news.entry;

import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.newsaggregator.app.NewsAggregatorApplication;

public class NewsEntryWebViewClient extends WebViewClient {
    private final NewsEntryView newsEntryView;

    public NewsEntryWebViewClient(final NewsEntryView newsEntryView) {
        this.newsEntryView = newsEntryView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, final WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        return true;
    }

    @Override
    public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        newsEntryView.showProgress();
    }

    @Override
    public void onPageFinished(final WebView view, final String url) {
        super.onPageFinished(view, url);
        newsEntryView.hideProgress();
    }
}
