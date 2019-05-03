package com.example.newsaggregator.presenter.rss_channel_list;

import com.example.newsaggregator.model.Repository;
import com.example.newsaggregator.view.rss_channel_list.RssChannelListView;

public class RssChannelListPresenter {
    private final RssChannelListView rssChannelListView;
    private final Repository repository;

    public RssChannelListPresenter(final RssChannelListView rssChannelListView) {
        this.rssChannelListView = rssChannelListView;
    }

    public void onCreate() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void onDestroy() {
        rssChannelListView.resetPresenter();
    }
}
