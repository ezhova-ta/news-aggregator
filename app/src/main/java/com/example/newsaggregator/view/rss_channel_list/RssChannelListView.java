package com.example.newsaggregator.view.rss_channel_list;

import java.util.Set;

public interface RssChannelListView {
    void showRssChannelSet(Set<String> rssChannelLinkSet);
    String getAddRssChannelEditTextValue();
    void clearAddRssChannelEditText();
    void startActivityToDisplayNewsEntryList(Class<?> activityClass,
            String rssChannelLinkExtraKey, String rssChannelLinkExtraValue);
}
