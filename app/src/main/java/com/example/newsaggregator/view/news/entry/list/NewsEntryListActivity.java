package com.example.newsaggregator.view.news.entry.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsaggregator.R;
import com.example.newsaggregator.app.DependencyInjectionFactory;
import com.example.newsaggregator.app.NewsAggregatorApplication;
import com.example.newsaggregator.model.NewsEntryListService;
import com.example.newsaggregator.model.entity.NewsEntry;
import com.example.newsaggregator.presenter.news.entry.list.NewsEntryListPresenter;
import com.example.newsaggregator.view.channel.list.RssChannelListView;
import com.example.newsaggregator.view.news.entry.NewsEntryActivity;

import java.util.List;

public class NewsEntryListActivity extends AppCompatActivity implements NewsEntryListView {
    private static final String RSS_CHANNEL_LINK_BUNDLE_KEY = "rssChannelLink";
    private static final String PREFERENCES_NAME = "news_entries";
    private static final String PREFERENCES_KEY = "newsEntriesDetetionDate";
//    private static final int DOWNLOADING_NEWS_ENTRY_LIST_NOTIFICATION_ID = 514;

    private DependencyInjectionFactory diFactory;
    private NewsEntryListPresenter presenter;
    private OnNewsEntryListItemClickListener onNewsEntryListItemClickListener;
    private TextView rssChannelName;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private BroadcastReceiver receiver;
    private String rssChannelLink;
    private NotificationCompat.Builder notificationBuilder;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_entry_list);
        initViewElement();

        if(savedInstanceState != null) {
            rssChannelLink = savedInstanceState.getString(RSS_CHANNEL_LINK_BUNDLE_KEY);
        } else {
            rssChannelLink = getIntent().getStringExtra(RssChannelListView.RSS_CHANNEL_LINK_EXTRA_KEY);
        }

        diFactory = NewsAggregatorApplication.getInstance().getDiFactory();
        rssChannelName.setText(rssChannelLink);
        onNewsEntryListItemClickListener = diFactory.provideOnNewsEntryListItemClickListener(this);
        presenter = diFactory.provideNewsEntryListPresenter(this);
        presenter.onCreate();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                final int requestResult = intent.getIntExtra(NewsEntryListService.EXTRA_PARAM_REQUEST_RESULT,
                        FETCHING_NEWS_ENTRY_LIST_DEFAULT_RESULT);
                presenter.onReceiveBroadcastMessage(requestResult);
            }
        };

        final IntentFilter intentFilter = new IntentFilter(NewsEntryListService.ACTION_FETCH_NEWS_ENTRY_LIST);
        registerReceiver(receiver, intentFilter);
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
        unregisterReceiver(receiver);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RSS_CHANNEL_LINK_BUNDLE_KEY, rssChannelLink);
    }

    private void initViewElement() {
        rssChannelName = findViewById(R.id.rssChannelName);
        recyclerView = findViewById(R.id.newsEntryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.NewsEntriesLoadingProgress);
    }

    public void onUpdateNewsEntryListButtonClick(final View view) {
        presenter.onUpdateNewsEntryListButtonClick();
    }

    @Override
    public void showNewsEntryList(final List<NewsEntry> newsEntryList) {
        final NewsEntryAdapter adapter = new NewsEntryAdapter(this, newsEntryList);
        recyclerView.setAdapter(adapter);
        adapter.subscribeOnRssChannelListItemClick(onNewsEntryListItemClickListener);
    }

    @Override
    public void startServiceToUpdateNewsEntryList() {
        final Intent intent = new Intent(this, NewsEntryListService.class);
        intent.setAction(NewsEntryListService.ACTION_FETCH_NEWS_ENTRY_LIST);
        intent.putExtra(NewsEntryListService.EXTRA_PARAM_RSS_CHANNEL_URL, rssChannelLink);
        startService(intent);
    }

    @Override
    public String getRssChannelLink() {
        return rssChannelLink;
    }

    @Override
    public void startActivityToDisplayNewsEntry(final String newsEntryLinkExtraKey,
                                                final String newsEntryLinkExtraValue) {
        final Intent intent = new Intent(this, NewsEntryActivity.class);
        intent.putExtra(newsEntryLinkExtraKey, newsEntryLinkExtraValue);
        startActivity(intent);
    }

    @Override
    public long getNewsEntriesDetetionDateInMillis() {
        final SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        return preferences.getLong(PREFERENCES_KEY, DEFAULT_PREFERENCE_VALUE);
    }

    @Override
    public void setNewsEntriesDetetionDate(final long newsEntriesDetetionDateInMillis) {
        final SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        preferences
                .edit()
                .putLong(PREFERENCES_KEY, newsEntriesDetetionDateInMillis)
                .apply();
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
    public void showNewsEntriesUpdatingErrorMessage() {
        showPopupMessage(getResources().getText(R.string.news_entries_updating_error_message));
    }

    @Override
    public void showNewsEntriesLoadingErrorMessage() {
        showPopupMessage(getResources().getText(R.string.news_entries_loading_error_message));
    }

    @Override
    public void showEmptyNewsEntryListMessage() {
        showPopupMessage(getResources().getText(R.string.empty_news_entry_list_message));
    }

    private void showPopupMessage(final CharSequence text) {
        final Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
