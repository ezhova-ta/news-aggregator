package com.example.newsaggregator.view.channel.list;

import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public interface RssChannelListView {
    String RSS_CHANNEL_LINK_EXTRA_KEY = "com.example.newsaggregator.view.rss_channel_list.RSS_CHANNEL_LINK";
    int FETCHING_NEWS_ENTRY_LIST_DEFAULT_RESULT = 0;
    int UPDATE_NOTIFICATION_ID = 513;
    long REPEATING_ALARM_INTERVAL = 120_000;

    void showRssChannelList(List<RssChannel> rssChannelList);
    String getAddRssChannelEditTextValue();
    void clearAddRssChannelEditText();

    void startActivityToDisplayNewsEntryList(String rssChannelLinkExtraKey, String rssChannelLinkExtraValue);
    void startActivityToDeleteRssChannels();
    void startAlarmManagerToUpdateNewsEntryLists();
    void stopAlarmManagerToUpdateNewsEntryLists();

    void showProgressBar();
    void hideProgressBar();

    void showRssChannelsLoadingErrorMessage();
    void showRssChannelsAddingErrorMessage();
    void showRssChannelsAddingSuccessMessage();
    void showEmptyRssChannelListMessage();
    void showEnableUpdatingNotificationsMessage();
    void showDisableUpdatingNotificationsMessage();

    void hideDeleteRssChannelsButton();
    void hideEnableUpdatingNotificationsButton();
    void hideDisableUpdatingNotificationsButton();
    void showEnableUpdatingNotificationsButton();
    void showDisableUpdatingNotificationsButton();
    void showDeleteRssChannelsButton();

    void showUpdateNotification();

    void setEnabledNotificationsValue(boolean isEnable);
    boolean getEnabledNotificationsValue();
}
