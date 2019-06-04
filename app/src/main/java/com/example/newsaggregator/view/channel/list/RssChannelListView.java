package com.example.newsaggregator.view.channel.list;

import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

public interface RssChannelListView {
    String RSS_CHANNEL_LINK_EXTRA_KEY = "com.example.newsaggregator.view.rss_channel_list.RSS_CHANNEL_LINK";
    void showRssChannelList(List<RssChannel> rssChannelList);
    String getAddRssChannelEditTextValue();
    String getRepeatingUpdateAlarmIntervalEditTextValue();
    int getRepeatingIntervalUnitSpinnerSelectedItemPosition();
    void clearAddRssChannelEditText();
    void clearRepeatingUpdateAlarmIntervalEditText();
    void startActivityToDisplayNewsEntryList(String rssChannelLinkExtraKey, String rssChannelLinkExtraValue);
    void startActivityToDeleteRssChannels();
    void startAlarmManagerToUpdateNewsEntryLists(long intervalMillis);
    void stopAlarmManagerToUpdateNewsEntryLists();
    void showProgressBar();
    void hideProgressBar();
    void showRssChannelsLoadingErrorMessage();
    void showRssChannelsAddingErrorMessage();
    void showRssChannelsAddingSuccessMessage();
    void showEmptyRssChannelListMessage();
    void showEnableUpdatingNotificationsMessage();
    void showDisableUpdatingNotificationsMessage();
    void showInvalidReepatingUpdateAlarmIntervalMessage();
    void showEmptyReepatingUpdateAlarmIntervalMessage();
    void hideDeleteRssChannelsButton();
    void hideEnableUpdatingNotificationsButton();
    void hideDisableUpdatingNotificationsButton();
    void hideRepeatingUpdateAlarmIntervalEditText();
    void hideRepeatingIntervalUnitSpinner();
    void showEnableUpdatingNotificationsButton();
    void showDisableUpdatingNotificationsButton();
    void showDeleteRssChannelsButton();
    void showRepeatingUpdateAlarmIntervalEditText();
    void showRepeatingIntervalUnitSpinner();
    void setEnabledNotificationsValue(boolean isEnable);
    boolean getEnabledNotificationsValue();
    void startServiceToUpdateNewsEntryLists();
}
