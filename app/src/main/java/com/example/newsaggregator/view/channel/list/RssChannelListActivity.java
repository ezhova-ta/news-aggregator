package com.example.newsaggregator.view.channel.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsaggregator.R;
import com.example.newsaggregator.model.DBHelper;
import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.model.repository.RssChannelListRepositoryImpl;
import com.example.newsaggregator.presenter.channel.list.RssChannelListPresenter;
import com.example.newsaggregator.view.news.entry.list.NewsEntryListActivity;

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
        presenter = new RssChannelListPresenter(this,
                new RssChannelListRepositoryImpl(new DBHelper(this)));
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
    public void showRssChannelList(final List<RssChannel> rssChannelList) {
        final RssChannelAdapter adapter = new RssChannelAdapter(this, rssChannelList);
        recyclerView.setAdapter(adapter);
        adapter.subscribeOnRssChannelListItemClick(presenter);
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
    public void showPopupMessage(final String text) {
        final Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
