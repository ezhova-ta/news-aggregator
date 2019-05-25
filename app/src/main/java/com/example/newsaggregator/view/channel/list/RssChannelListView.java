package com.example.newsaggregator.view.channel.list;

import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public interface RssChannelListView {
    String RSS_CHANNEL_LINK_EXTRA_KEY = "com.example.newsaggregator.view.rss_channel_list.RSS_CHANNEL_LINK";
    int FETCHING_NEWS_ENTRY_LIST_DEFAULT_RESULT = 0;
    String UPDATE_NOTIFICATION_CONTENT_TITLE = "Fresh news";
    String UPDATE_NOTIFICATION_CONTENT_TEXT = "There were fresh news in RSS feeds.";
    int UPDATE_NOTIFICATION_ID = 513;
    long REPEATING_ALARM_INTERVAL = 120_000;

    void showRssChannelList(List<RssChannel> rssChannelList);
    String getAddRssChannelEditTextValue();
    void clearAddRssChannelEditText();
    void startActivityToDisplayNewsEntryList(String rssChannelLinkExtraKey, String rssChannelLinkExtraValue);
    void startActivityToDeleteRssChannels();
    void startAlarmManagerToUpdateNewsEntryLists();
    void showProgressBar();
    void hideProgressBar();
    void showRssChannelsLoadingErrorMessage();
    void showRssChannelsAddingErrorMessage();
    void showRssChannelsAddingSuccessMessage();
    void showEmptyRssChannelListMessage();
    void hideDeleteRssChannelsButton();
    void showDeleteRssChannelsButton();
    void showUpdateNotification();
}
