package com.example.newsaggregator.view.news.entry.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.newsaggregator.R;
import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.Calendar;

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
        newsEntryPubDateTextView.setText(convertToString(newsEntry.getPubDate()));
    }

    private String convertToString(final long dateInMillis) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        return  calendar.get(Calendar.DAY_OF_MONTH) + "." +
                (calendar.get(Calendar.MONTH) + 1) + "." +
                calendar.get(Calendar.YEAR);
    }
}
