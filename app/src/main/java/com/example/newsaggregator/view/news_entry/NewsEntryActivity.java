package com.example.newsaggregator.view.news_entry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.newsaggregator.R;
import com.example.newsaggregator.presenter.news_entry.NewsEntryPresenter;
import com.example.newsaggregator.view.news_entry_list.NewsEntryListView;

public class NewsEntryActivity extends AppCompatActivity implements NewsEntryView {
    private NewsEntryPresenter presenter;
    private String newsEntryLink;
    private WebView newsEntryWebView;

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_entry);
        newsEntryWebView = findViewById(R.id.newsEntryWebView);
        newsEntryLink = getIntent().getStringExtra(NewsEntryListView.NEWS_ENTRY_LINK_EXTRA_KEY);
        presenter = new NewsEntryPresenter(this);
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
    public void showNewsEntry(final String newsEntryLink) {
        newsEntryWebView.setWebViewClient(webViewClient);
        newsEntryWebView.loadUrl(newsEntryLink);
    }
}
