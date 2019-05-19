package com.example.newsaggregator.view.channel.list.deleting;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;

import com.example.newsaggregator.R;
import com.example.newsaggregator.model.entity.RssChannel;

class RssChannelHolder extends RecyclerView.ViewHolder {
    private final CheckedTextView rssChannelLinkTextView;

    RssChannelHolder(@NonNull final View rssChannelView) {
        super(rssChannelView);
        rssChannelLinkTextView = rssChannelView.findViewById(R.id.rssChannelLinkForDeleting);
    }

    void fillView(final RssChannel rssChannel) {
        rssChannelLinkTextView.setText(rssChannel.getLink());
    }

    boolean isChecked() {
        return rssChannelLinkTextView.isChecked();
    }

    void setChecked(final boolean checked) {
        rssChannelLinkTextView.setChecked(checked);
    }
}
