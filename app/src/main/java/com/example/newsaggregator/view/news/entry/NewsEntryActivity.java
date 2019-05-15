package com.example.newsaggregator.view.news.entry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.newsaggregator.R;
import com.example.newsaggregator.presenter.news.entry.NewsEntryPresenter;
import com.example.newsaggregator.presenter.news.entry.NewsEntryPresenterImpl;
import com.example.newsaggregator.view.news.entry.list.NewsEntryListView;

public class NewsEntryActivity extends AppCompatActivity implements NewsEntryView {
    private NewsEntryPresenter presenter;
    private WebView newsEntryWebView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_entry);
        newsEntryWebView = findViewById(R.id.newsEntryWebView);
        final String newsEntryLink = getIntent().getStringExtra(NewsEntryListView.NEWS_ENTRY_LINK_EXTRA_KEY);
        presenter = new NewsEntryPresenterImpl(this);
        presenter.onCreate(newsEntryLink);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showNewsEntry(final WebViewClient webViewClient, final String newsEntryLink) {
        newsEntryWebView.setWebViewClient(webViewClient);
        newsEntryWebView.loadUrl(newsEntryLink);
    }
}
