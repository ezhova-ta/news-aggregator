package com.example.newsaggregator.view.rss_channel_list;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsaggregator.R;
import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.view.news_entry_list.NewsEntryListActivity;

import java.util.List;

class RssChannelAdapter extends RecyclerView.Adapter<RssChannelHolder> {
    private final List<RssChannel> rssChannelList;
    private final Activity activity;

    RssChannelAdapter(final Activity activity, final List<RssChannel> rssChannelList) {
        this.rssChannelList = rssChannelList;
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
        final RssChannel rssChannel = rssChannelList.get(position);
        rssChannelHolder.fillView(rssChannel);
        rssChannelHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Intent intent = new Intent(view.getContext(), NewsEntryListActivity.class);
                intent.putExtra("rssChannelLink", rssChannel.getLink());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssChannelList.size();
    }
}
