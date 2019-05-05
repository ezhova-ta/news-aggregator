package com.example.newsaggregator.presenter.news_entry_list;

import android.content.Intent;

import com.example.newsaggregator.model.Repository;
import com.example.newsaggregator.model.RepositoryImpl;
import com.example.newsaggregator.model.entity.NewsEntry;
import com.example.newsaggregator.view.news_entry_list.NewsEntryListView;

import java.util.List;

public class NewsEntryListPresenter {
    private final NewsEntryListView newsEntryListView;
    private final Repository repository;

    public NewsEntryListPresenter(final NewsEntryListView newsEntryListView) {
        this.newsEntryListView = newsEntryListView;
        repository = new RepositoryImpl();
    }

    public void onCreate(final String rssChannelLink) {
        /*
        TODO registerReceiver()
         */
//        newsEntryListView.registerReceiver(NewsEntryListService.ACTION_FETCH_NEWS);

        final List<NewsEntry> newsEntryList = repository.getNewsEntryList(rssChannelLink);
        if(!newsEntryList.isEmpty()) {
            newsEntryListView.showNewsEntryList(newsEntryList);
        }
    }

    public void onDestroy() {
        newsEntryListView.unregisterReceiver();
        newsEntryListView.resetPresenter();
    }

    public void onUpdateNewsEntryListButtonClick() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void onReceivedBroadcastMessage(final Intent intent) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
