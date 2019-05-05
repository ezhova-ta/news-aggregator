package com.example.newsaggregator.view.news_entry_list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.newsaggregator.R;
import com.example.newsaggregator.model.entity.NewsEntry;
import com.example.newsaggregator.presenter.news_entry_list.NewsEntryListPresenter;

import java.util.List;

public class NewsEntryListActivity extends AppCompatActivity implements NewsEntryListView {
    private NewsEntryListPresenter presenter;
    private BroadcastReceiver receiver;
    private NewsEntryAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_entry_list);

        final String rssChannelLink = getIntent().getStringExtra("rssChannelLink");

        recyclerView = findViewById(R.id.newsEntryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new NewsEntryListPresenter(this);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                presenter.onReceivedBroadcastMessage(intent);
            }
        };

        presenter.onCreate(rssChannelLink);
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
        presenter.onDestroy();
    }

    public void onUpdateNewsEntryListButtonClick(final View view) {
        presenter.onUpdateNewsEntryListButtonClick();
    }

    @Override
    public void registerReceiver(final String action) {
        registerReceiver(receiver, new IntentFilter(action));
    }

    @Override
    public void unregisterReceiver() {
        unregisterReceiver(receiver);
    }

    @Override
    public void showNewsEntryList(final List<NewsEntry> newsEntryList) {
        final NewsEntryAdapter adapter = new NewsEntryAdapter(this, newsEntryList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void resetPresenter() {
        presenter = null;
    }
}
