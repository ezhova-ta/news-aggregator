package com.example.newsaggregator.view.channel.list.deleting;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsaggregator.R;
import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;

class RssChannelAdapter extends RecyclerView.Adapter<RssChannelHolder> {
    private final List<RssChannel> rssChannelList;
    private final DeletingRssChannelListActivity activity;
    private OnRssChannelListItemCheckListener onRssChannelListItemCheckListener;

    RssChannelAdapter(final DeletingRssChannelListActivity activity, final List<RssChannel> rssChannelList) {
        this.rssChannelList = rssChannelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RssChannelHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int position) {
        final LayoutInflater inflater = activity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.deleting_rss_channel_list_item_view, viewGroup, false);
        return new RssChannelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RssChannelHolder rssChannelHolder, final int position) {
        final RssChannel rssChannel = rssChannelList.get(position);
        rssChannelHolder.fillView(rssChannel);

        rssChannelHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(rssChannelHolder.isChecked()) {
                    rssChannelHolder.setChecked(false);
                    notifyOnRssChannelListItemCheckListener(rssChannel, false);
                } else {
                    rssChannelHolder.setChecked(true);
                    notifyOnRssChannelListItemCheckListener(rssChannel, true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssChannelList.size();
    }

    public void subscribeOnRssChannelListItemCheck(final OnRssChannelListItemCheckListener listener) {
        onRssChannelListItemCheckListener = listener;
    }

    public void unSubscribeOnRssChannelListItemCheck(final OnRssChannelListItemCheckListener listener) {
        onRssChannelListItemCheckListener = null;
    }

    private void notifyOnRssChannelListItemCheckListener(final RssChannel rssChannel, final boolean checked) {
        onRssChannelListItemCheckListener.onRssChannelListItemCheck(rssChannel, checked);
    }
}
