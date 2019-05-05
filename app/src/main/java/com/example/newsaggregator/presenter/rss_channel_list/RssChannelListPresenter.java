package com.example.newsaggregator.presenter.rss_channel_list;

import com.example.newsaggregator.model.Repository;
import com.example.newsaggregator.model.RepositoryImpl;
import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.view.rss_channel_list.RssChannelListView;

import java.util.List;

public class RssChannelListPresenter {
    private final RssChannelListView rssChannelListView;
    private final Repository repository;

    public RssChannelListPresenter(final RssChannelListView rssChannelListView) {
        this.rssChannelListView = rssChannelListView;
        repository = new RepositoryImpl();
    }

    public void onCreate() {
        showAvailableRssChannelList();
    }

    public void onDestroy() {
        rssChannelListView.resetPresenter();
    }

    public void onAddRssChannelButtonClick() {
        final String rssChannelLink = rssChannelListView.getAddRssChannelEditTextValue();
        if(!rssChannelLink.isEmpty()) {
            repository.addRssChannel(rssChannelLink);
            rssChannelListView.clearAddRssChannelEditText();
            showAvailableRssChannelList();
        }
    }

    private void showAvailableRssChannelList() {
        final List<RssChannel> rssChannelList = repository.getRssChannelList();
        rssChannelListView.showRssChannelList(rssChannelList);
    }
}
