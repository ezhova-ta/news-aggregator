package com.example.newsaggregator.view.channel.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsaggregator.R;
import com.example.newsaggregator.app.DependencyInjectionFactory;
import com.example.newsaggregator.app.NewsAggregatorApplication;
import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.presenter.channel.list.RssChannelListPresenter;
import com.example.newsaggregator.view.channel.list.deleting.DeletingRssChannelListActivity;
import com.example.newsaggregator.view.news.entry.list.NewsEntryListActivity;

import java.util.List;

public class RssChannelListActivity extends AppCompatActivity implements RssChannelListView {
    private DependencyInjectionFactory diFactory;
    private EditText addRssChannelEditText;
    private RssChannelListPresenter presenter;
    private OnRssChannelListItemClickListener onRssChannelListItemClickListener;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_channel_list);
        initViewElement();

        diFactory = NewsAggregatorApplication.getInstance().getDiFactory();
        onRssChannelListItemClickListener = diFactory.provideOnRssChannelListItemClickListener(this);
        presenter = diFactory.provideRssChannelListPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
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

    private void initViewElement() {
        addRssChannelEditText = findViewById(R.id.addRssChannelEditText);
        recyclerView = findViewById(R.id.rssChannelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.rssChannelsLoadingProgress);
    }

    public void onAddRssChannelButtonClick(final View view) {
        presenter.onAddRssChannelButtonClick();
    }

    public void onDeleteRssChannelsButtonClick(final View view) {
        presenter.onDeleteRssChannelsButtonClick();
    }

    @Override
    public void showRssChannelList(final List<RssChannel> rssChannelList) {
        final RssChannelAdapter adapter = new RssChannelAdapter(this, rssChannelList);
        recyclerView.setAdapter(adapter);
        adapter.subscribeOnRssChannelListItemClick(onRssChannelListItemClickListener);
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
    public void startActivityToDisplayNewsEntryList(final String rssChannelLinkExtraKey,
                                                    final String rssChannelLinkExtraValue) {
        final Intent intent = new Intent(this, NewsEntryListActivity.class);
        intent.putExtra(rssChannelLinkExtraKey, rssChannelLinkExtraValue);
        startActivity(intent);
    }

    @Override
    public void startActivityToDeleteRssChannels() {
        final Intent intent = new Intent(this, DeletingRssChannelListActivity.class);
        startActivity(intent);
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
    public void showRssChannelsLoadingErrorMessage() {
        showPopupMessage(getResources().getText(R.string.rss_channels_loading_error_message));
    }

    @Override
    public void showRssChannelsAddingErrorMessage() {
        showPopupMessage(getResources().getText(R.string.rss_channels_adding_error_message));
    }

    @Override
    public void showRssChannelsAddingSuccessMessage() {
        showPopupMessage(getResources().getText(R.string.rss_channels_adding_success_message));
    }

    private void showPopupMessage(final CharSequence text) {
        final Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
