package com.example.newsaggregator.presenter.channel.list;

public interface RssChannelListPresenter {
    void onResume();
    void onAddRssChannelButtonClick();
    void onDeleteRssChannelsButtonClick();
    void onEnableUpdatingNotificationsButtonClick();
    void onDisableUpdatingNotificationsButtonClick();
    void onReceiveBroadcastMessage(int requestResult);
}
