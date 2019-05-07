package com.example.newsaggregator.model.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.newsaggregator.application.NewsAggregatorApplication;

import java.util.HashSet;
import java.util.Set;

public class RssChannelListRepositoryImpl implements RssChannelListRepository {
    private final SharedPreferences preferences;

    public RssChannelListRepositoryImpl() {
        /*
        TODO Вынести magic const в константы
         */
        preferences =
                NewsAggregatorApplication
                .getInstance()
                .getContext()
                .getSharedPreferences("rss_channels", Context.MODE_PRIVATE);
    }

    @Override
    public Set<String> getRssChannelLinkSet() {
        return preferences.getStringSet("linkList", null);
    }

    @Override
    public void addRssChannel(final String link) {
        /*
        TODO Вынести magic const в константы
         */
        Set<String> rssChannelLinkSet = preferences.getStringSet("linkList", null);
        if (rssChannelLinkSet == null) {
            rssChannelLinkSet = new HashSet<>(1);
        }
        rssChannelLinkSet.add(link);
        /*
        TODO Вряд ли правильно удалять и добавлять linkList
         */
        preferences
                .edit()
                .remove("linkList")
                .putStringSet("linkList", rssChannelLinkSet)
                .apply();
    }
}
