package com.example.newsaggregator.view.news.entry.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.newsaggregator.R;
import com.example.newsaggregator.model.entity.NewsEntry;

class NewsEntryHolder extends RecyclerView.ViewHolder {
    private final TextView newsEntryTitleTextView;
    private final TextView newsEntryDescriptionTextView;
    private final TextView newsEntryPubDateTextView;

    NewsEntryHolder(@NonNull final View newsEntryView) {
        super(newsEntryView);

        newsEntryTitleTextView = newsEntryView.findViewById(R.id.newsEntryTitle);
        newsEntryDescriptionTextView = newsEntryView.findViewById(R.id.newsEntryDescription);
        newsEntryPubDateTextView = newsEntryView.findViewById(R.id.newsEntryPubDate);
    }

    void fillView(final NewsEntry newsEntry) {
        newsEntryTitleTextView.setText(newsEntry.getTitle());
        newsEntryDescriptionTextView.setText(newsEntry.getDescription());
        newsEntryPubDateTextView.setText(newsEntry.getPubDate());
    }
}
