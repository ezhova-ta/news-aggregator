package com.example.newsaggregator.view.rss_channel_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.newsaggregator.R;
import com.example.newsaggregator.presenter.rss_channel_list.RssChannelListPresenter;

public class RssChannelListActivity extends AppCompatActivity implements RssChannelListView {
    private RssChannelListPresenter presenter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_channel_list);
        recyclerView = findViewById(R.id.rssChannelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new RssChannelListPresenter(this);
        presenter.onCreate();
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

    public void onAddRssChannelButtonClick() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void resetPresenter() {
        presenter = null;
    }
}
