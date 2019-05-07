package com.example.newsaggregator.model;

import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.List;

public interface NewsEntryListRepository {
    List<NewsEntry> getNewsEntryList(final String channelLink);
}
