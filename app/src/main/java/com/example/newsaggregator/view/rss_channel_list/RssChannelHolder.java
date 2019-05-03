package com.example.newsaggregator.view.rss_channel_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.newsaggregator.R;
import com.example.newsaggregator.model.entity.RssChannel;

class RssChannelHolder extends RecyclerView.ViewHolder {
    private final TextView rssChannelLinkTextView;

    RssChannelHolder(@NonNull final View rssChannelView) {
        super(rssChannelView);
        rssChannelLinkTextView = rssChannelView.findViewById(R.id.rssChannelLink);
    }

    void fillView(final RssChannel rssChannel) {
        rssChannelLinkTextView.setText(rssChannel.getLink());
    }
}
