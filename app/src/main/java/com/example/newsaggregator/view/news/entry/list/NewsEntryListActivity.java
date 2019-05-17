package com.example.newsaggregator.view.news.entry.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import com.example.newsaggregator.model.NewsEntryListService;
import com.example.newsaggregator.model.entity.NewsEntry;
import com.example.newsaggregator.presenter.news.entry.list.NewsEntryListPresenter;
import com.example.newsaggregator.view.channel.list.RssChannelListView;
import com.example.newsaggregator.view.news.entry.NewsEntryActivity;

import java.util.List;

public class NewsEntryListActivity extends AppCompatActivity implements NewsEntryListView {
    private DependencyInjectionFactory diFactory;
    private NewsEntryListPresenter presenter;
    private OnNewsEntryListItemClickListener onNewsEntryListItemClickListener;
    private RecyclerView recyclerView;
    private BroadcastReceiver receiver;
    private String rssChannelLink;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_entry_list);

        diFactory = NewsAggregatorApplication.getInstance().getDiFactory();
        rssChannelLink = getIntent().getStringExtra(RssChannelListView.RSS_CHANNEL_LINK_EXTRA_KEY);
        recyclerView = findViewById(R.id.newsEntryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    public void onUpdateNewsEntryListButtonClick(final View view) {
        presenter.onUpdateNewsEntryListButtonClick();
    }

    @Override
    public String getRssChannelLink() {
        return rssChannelLink;
    }

    @Override
    public void showPopupMessage(final String text) {
        final Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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
        final SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE);
        return preferences.getLong(PREFERENCE_NAME, DEFAULT_PREFERENCE_VALUE);
    }

    @Override
    public void setNewsEntriesDetetionDate(final long newsEntriesDetetionDateInMillis) {
        final SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE);
        preferences
                .edit()
                .putLong(PREFERENCE_NAME, newsEntriesDetetionDateInMillis)
                .apply();
    }
}
