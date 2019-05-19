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

import java.util.List;

public class DeletingRssChannelListActivity extends AppCompatActivity implements DeletingRssChannelListView {
    private DependencyInjectionFactory diFactory;
    private RecyclerView recyclerView;
    private OnRssChannelListItemCheckListener onRssChannelListItemCheckListener;
    private DeletingRssChannelListPresenter presenter;

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

    public void onConfirmDeletingRssChannelsButtonClick(final View view) {

    }

    @Override
    public void showRssChannelList(final List<RssChannel> rssChannelList) {
        final RssChannelAdapter adapter = new RssChannelAdapter(this, rssChannelList);
        recyclerView.setAdapter(adapter);
        adapter.subscribeOnRssChannelListItemCheck(onRssChannelListItemCheckListener);
    }

    @Override
    public void showPopupMessage(final String text) {
        final Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
