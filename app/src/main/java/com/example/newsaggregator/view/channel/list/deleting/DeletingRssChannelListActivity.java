package com.example.newsaggregator.view.channel.list.deleting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.newsaggregator.R;
import com.example.newsaggregator.app.DependencyInjectionFactory;
import com.example.newsaggregator.app.NewsAggregatorApplication;
import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.presenter.channel.list.deleting.DeletingRssChannelListPresenter;

import java.util.ArrayList;
import java.util.List;

public class DeletingRssChannelListActivity extends AppCompatActivity implements DeletingRssChannelListView {
    private DependencyInjectionFactory diFactory;
    private RecyclerView recyclerView;
    private RssChannelAdapter adapter;
    private OnRssChannelListItemCheckListener onRssChannelListItemCheckListener;
    private DeletingRssChannelListPresenter presenter;
    private ArrayList<String> checkedRssChannelLinkList = new ArrayList<>();

    private static final String CHECKED_RSS_CHANNEL_LINKS_BUNDLE_KEY = "checkedRssChannelLinks";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deleting_rss_channel_list);

        recyclerView = findViewById(R.id.rssChannelListForDeleting);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        diFactory = NewsAggregatorApplication.getInstance().getDiFactory();
        onRssChannelListItemCheckListener = diFactory.provideOnRssChannelListItemCheckListener(this);
        presenter = diFactory.provideDeletingRssChannelListPresenter(this);
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

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(CHECKED_RSS_CHANNEL_LINKS_BUNDLE_KEY, checkedRssChannelLinkList);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        checkedRssChannelLinkList = savedInstanceState.getStringArrayList(CHECKED_RSS_CHANNEL_LINKS_BUNDLE_KEY);
    }

    public void onConfirmDeletingRssChannelsButtonClick(final View view) {
        presenter.onConfirmDeletingRssChannelsButtonClick();
    }

    @Override
    public void showRssChannelList(final List<RssChannel> rssChannelList) {
        adapter = new RssChannelAdapter(this, rssChannelList, checkedRssChannelLinkList);
        recyclerView.setAdapter(adapter);
        adapter.subscribeOnRssChannelListItemCheck(onRssChannelListItemCheckListener);
    }

    @Override
    public void showPopupMessage(final String text) {
        final Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void addRssChannelLink(final String rssChannelLink) {
        checkedRssChannelLinkList.add(rssChannelLink);
    }

    @Override
    public void removeRssChannelLink(final String rssChannelLink) {
        checkedRssChannelLinkList.remove(rssChannelLink);
    }

    @Override
    public List<String> getCheckedRssChannelLinkList() {
        return checkedRssChannelLinkList;
    }

    @Override
    public void clearCheckedRssChannelLinkList() {
        checkedRssChannelLinkList.clear();
    }
}
