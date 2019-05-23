package com.example.newsaggregator.view.channel.list.deleting;

import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public interface DeletingRssChannelListView {
    void showRssChannelList(List<RssChannel> rssChannelList);
    void addRssChannelLink(String rssChannelLink);
    void removeRssChannelLink(String rssChannelLink);
    List<String> getCheckedRssChannelLinkList();
    void clearCheckedRssChannelLinkList();
    void showProgressBar();
    void hideProgressBar();
    void showRssChannelsLoadingError();
    void showRssChannelsDeletingErrorMessage();
    void showRssChannelsDeletingSuccessMessage();
    void showEmptyRssChannelListMessage();
    void hideConfirmDeletingRssChannelsButton();
    void showRssChannelListUpdatedNotification();
}
