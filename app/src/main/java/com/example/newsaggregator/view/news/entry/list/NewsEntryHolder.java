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

        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        final String dayOfMonthString = dayOfMonth > 9 ? Integer.toString(dayOfMonth) : "0" + dayOfMonth;

        final int month = calendar.get(Calendar.MONTH) + 1;
        final String monthString = month > 9 ? Integer.toString(month) : "0" + month;

        final int year = calendar.get(Calendar.YEAR);

        final int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        final String hourOfDayString = hourOfDay > 9 ? Integer.toString(hourOfDay) : "0" + hourOfDay;

        final int minute = calendar.get(Calendar.MINUTE);
        final String minuteString = minute > 9 ? Integer.toString(minute) : "0" + minute;

        final int second = calendar.get(Calendar.SECOND);
        final String secondString = second > 9 ? Integer.toString(second) : "0" + second;

        return  dayOfMonthString + "." + monthString + "." + year + " " +
                hourOfDayString + ":" + minuteString + ":" + secondString;
    }
}
