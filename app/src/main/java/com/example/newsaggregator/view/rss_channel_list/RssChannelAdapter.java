package com.example.newsaggregator.view.rss_channel_list;

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
    private final RssChannelListActivity activity;

    RssChannelAdapter(final RssChannelListActivity activity, final List<RssChannel> rssChannelList) {
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
        /*
        TODO Лучше засетить в адаптер абстрактный листенер
         */
        rssChannelHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                activity.getPresenter().onRssChannelListItemClick(rssChannel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssChannelList.size();
    }
}
