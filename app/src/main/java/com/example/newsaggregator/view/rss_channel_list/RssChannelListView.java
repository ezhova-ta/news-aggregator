package com.example.newsaggregator.view.rss_channel_list;

import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public interface RssChannelListView {
    void showRssChannelList(List<RssChannel> rssChannelList);
    void resetPresenter();
    String getAddRssChannelEditTextValue();
}
