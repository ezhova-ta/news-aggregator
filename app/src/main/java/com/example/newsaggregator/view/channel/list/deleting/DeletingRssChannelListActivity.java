package com.example.newsaggregator.view.channel.list.deleting;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsaggregator.R;
import com.example.newsaggregator.app.DependencyInjectionFactory;
import com.example.newsaggregator.app.NewsAggregatorApplication;
import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.presenter.channel.list.deleting.DeletingRssChannelListPresenter;
import com.example.newsaggregator.view.channel.list.RssChannelListActivity;

import java.util.ArrayList;
import java.util.List;

public class DeletingRssChannelListActivity extends AppCompatActivity implements DeletingRssChannelListView {
    private DependencyInjectionFactory diFactory;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RssChannelAdapter adapter;
    private OnRssChannelListItemCheckListener onRssChannelListItemCheckListener;
    private DeletingRssChannelListPresenter presenter;
    private ArrayList<String> checkedRssChannelLinkList = new ArrayList<>();

    private static final String CHECKED_RSS_CHANNEL_LINKS_BUNDLE_KEY = "checkedRssChannelLinks";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deleting_rss_channel_list);

        initViewElement();

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

    private void initViewElement() {
        recyclerView = findViewById(R.id.rssChannelListForDeleting);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.rssChannelsDeletingProgress);
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

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRssChannelsLoadingError() {
        showPopupMessage(getResources().getText(R.string.rss_channels_loading_error_message));
    }

    @Override
    public void showRssChannelsDeletingErrorMessage() {
        showPopupMessage(getResources().getText(R.string.rss_channels_deleting_error_message));
    }

    @Override
    public void showRssChannelsDeletingSuccessMessage() {
        showPopupMessage(getResources().getText(R.string.rss_channels_deleting_success_message));
    }

    @Override
    public void showEmptyRssChannelListMessage() {
        showPopupMessage(getResources().getText(R.string.empty_rss_channel_list_message));
    }

    @Override
    public void hideConfirmDeletingRssChannelsButton() {
        findViewById(R.id.confirmDeletingRssChannelsButton).setVisibility(View.GONE);
    }

    private void showPopupMessage(final CharSequence text) {
        final Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
