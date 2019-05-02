package com.example.newsaggregator.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsaggregator.R;
import com.example.newsaggregator.presenter.NewsEntry;

import java.util.List;

class NewsEntryAdapter extends RecyclerView.Adapter<NewsEntryHolder> {
    private final List<NewsEntry> newsEntryList;
    private final Activity activity;

    NewsEntryAdapter(final Activity activity, final List<NewsEntry> newsEntryList) {
        this.newsEntryList = newsEntryList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NewsEntryHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int position) {
        final LayoutInflater inflater = activity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.news_entry_list_item_view, viewGroup, false);
        return new NewsEntryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsEntryHolder newsEntryHolder, final int position) {
        final NewsEntry newsEntry = newsEntryList.get(position);
        newsEntryHolder.fillView(newsEntry);
    }

    @Override
    public int getItemCount() {
        return newsEntryList.size();
    }
}
