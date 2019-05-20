package com.example.newsaggregator.view.channel.list.deleting;

import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public interface DeletingRssChannelListView {
    void showRssChannelList(List<RssChannel> rssChannelList);
    void showPopupMessage(String text);
    void addRssChannelLink(String rssChannelLink);
    void removeRssChannelLink(String rssChannelLink);
    List<String> getCheckedRssChannelLinkList();
}
