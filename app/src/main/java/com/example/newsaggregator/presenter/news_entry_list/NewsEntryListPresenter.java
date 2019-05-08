package com.example.newsaggregator.presenter.news_entry_list;

import com.example.newsaggregator.model.entity.NewsEntry;
import com.example.newsaggregator.model.repository.NewsEntryListRepository;
import com.example.newsaggregator.view.news_entry_list.NewsEntryListView;

import java.util.List;

public class NewsEntryListPresenter {
    private final NewsEntryListView newsEntryListView;
    private final NewsEntryListRepository repository;

    public NewsEntryListPresenter(final NewsEntryListView newsEntryListView,
                                  final NewsEntryListRepository repository) {
        this.newsEntryListView = newsEntryListView;
        this.repository = repository;
        /*
        TODO repository.closeResources()
         */
    }

    public void onCreate(final String rssChannelLink) {
        final List<NewsEntry> newsEntryList = repository.getNewsEntryList(rssChannelLink);
        if(!newsEntryList.isEmpty()) {
            newsEntryListView.showNewsEntryList(newsEntryList);
        }
    }

    public void onUpdateNewsEntryListButtonClick() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void onReceivedBroadcastMessage() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
