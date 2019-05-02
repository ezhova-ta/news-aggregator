package com.example.newsaggregator.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.newsaggregator.R;
import com.example.newsaggregator.presenter.NewsEntry;

class NewsEntryHolder extends RecyclerView.ViewHolder {
    private TextView newsEntryTitleTextView;
    private TextView newsEntryLinkTextView;
    private TextView newsEntryDescriptionTextView;
    private TextView newsEntryPubDateTextView;

    NewsEntryHolder(@NonNull final View newsEntryView) {
        super(newsEntryView);

        newsEntryTitleTextView = newsEntryView.findViewById(R.id.newsEntryTitle);
        newsEntryLinkTextView = newsEntryView.findViewById(R.id.newsEntryLink);
        newsEntryDescriptionTextView = newsEntryView.findViewById(R.id.newsEntryDescription);
        newsEntryPubDateTextView = newsEntryView.findViewById(R.id.newsEntryPubDate);
    }

    void fillView(final NewsEntry newsEntry) {
        newsEntryTitleTextView.setText(newsEntry.getTitle());
        newsEntryLinkTextView.setText(newsEntry.getLink());
        newsEntryDescriptionTextView.setText(newsEntry.getDescription());
        newsEntryPubDateTextView.setText(newsEntry.getPubDate());
    }
}
