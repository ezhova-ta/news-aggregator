package com.example.newsaggregator.view.rss_channel_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsaggregator.R;

import java.util.List;

class RssChannelAdapter extends RecyclerView.Adapter<RssChannelHolder> {
    private final List<String> rssChannelLinkList;
    private final RssChannelListActivity activity;

    RssChannelAdapter(final RssChannelListActivity activity, final List<String> rssChannelLinkList) {
        this.rssChannelLinkList = rssChannelLinkList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RssChannelHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int position) {
        final LayoutInflater inflater = activity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.rss_channel_list_item_view, viewGroup, false);
        return new RssChannelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RssChannelHolder rssChannelHolder, final int position) {
        final String rssChannelLink = rssChannelLinkList.get(position);
        rssChannelHolder.fillView(rssChannelLink);
        rssChannelHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                activity.getPresenter().onRssChannelListItemClick(rssChannelLink);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssChannelLinkList.size();
    }
}
