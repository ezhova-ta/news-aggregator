package com.example.newsaggregator.view.rss_channel_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.newsaggregator.R;
import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.presenter.rss_channel_list.RssChannelListPresenter;

import java.util.List;

public class RssChannelListActivity extends AppCompatActivity implements RssChannelListView {
    private EditText addRssChannelEditText;
    private RssChannelListPresenter presenter;
    private RecyclerView recyclerView;

    public RssChannelListPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_channel_list);
        addRssChannelEditText = findViewById(R.id.addRssChannelEditText);
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

    public void onAddRssChannelButtonClick(final View view) {
        presenter.onAddRssChannelButtonClick();
    }

    @Override
    public void showRssChannelList(final List<RssChannel> rssChannelList) {
        final RssChannelAdapter adapter = new RssChannelAdapter(this, rssChannelList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public String getAddRssChannelEditTextValue() {
        addRssChannelEditText = findViewById(R.id.addRssChannelEditText);
        return addRssChannelEditText.getText().toString();
    }

    @Override
    public void clearAddRssChannelEditText() {
        addRssChannelEditText.setText("");
    }
    @Override
    public void resetPresenter() {
        presenter = null;
    }
}
