package com.example.newsaggregator.view.rss_channel_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.newsaggregator.R;
import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.presenter.rss_channel_list.RssChannelListPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    }

    public void onAddRssChannelButtonClick(final View view) {
        presenter.onAddRssChannelButtonClick();
    }

    @Override
    public void showRssChannelSet(final Set<String> rssChannelLinkSet) {
        final List<String> rssChannelLinkList = new ArrayList<>();
        rssChannelLinkList.addAll(rssChannelLinkSet);
        final RssChannelAdapter adapter = new RssChannelAdapter(this, rssChannelLinkList);
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
    public void startActivityToDisplayNewsEntryList(final Class<?> activityClass,
            final String rssChannelLinkExtraKey, final String rssChannelLinkExtraValue) {
        final Intent intent = new Intent(this, activityClass);
        intent.putExtra(rssChannelLinkExtraKey, rssChannelLinkExtraValue);
        startActivity(intent);
    }
}
