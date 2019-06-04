package com.example.newsaggregator.view.channel.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.newsaggregator.model.RssChannelListService;

public class UpdatingNewsEntryListsAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        final Intent rssChannelListServiceIntent =
                new Intent(context, RssChannelListService.class);
        rssChannelListServiceIntent.setAction(RssChannelListService.ACTION_UPDATE_NEWS_ENTRY_LISTS);
        context.startService(rssChannelListServiceIntent);
    }
}