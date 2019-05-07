package com.example.newsaggregator.presenter.rss_channel_list;

import com.example.newsaggregator.model.repository.RssChannelListRepository;
import com.example.newsaggregator.model.repository.RssChannelListRepositoryImpl;
import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.view.news_entry_list.NewsEntryListActivity;
import com.example.newsaggregator.view.rss_channel_list.RssChannelListView;

import java.util.List;
import java.util.Set;

public class RssChannelListPresenter {
    private final RssChannelListView rssChannelListView;
    private final RssChannelListRepository repository;

    public RssChannelListPresenter(final RssChannelListView rssChannelListView) {
        this.rssChannelListView = rssChannelListView;
        /*
        TODO Закидывать зависимости извне
         */
        repository = new RssChannelListRepositoryImpl();
    }

    public void onCreate() {
        showAvailableRssChannelList();
    }

    public void onAddRssChannelButtonClick() {
        final String rssChannelLink = rssChannelListView.getAddRssChannelEditTextValue();
        if(!rssChannelLink.isEmpty()) {
            repository.addRssChannel(rssChannelLink);
            rssChannelListView.clearAddRssChannelEditText();
            showAvailableRssChannelList();
        }
    }

    public void onRssChannelListItemClick(final String rssChannelLink) {
        /*
        TODO Вынести magic const в константы
         */
        rssChannelListView.startActivityToDisplayNewsEntryList(NewsEntryListActivity.class,
                "rssChannelLink", rssChannelLink);
    }

    private void showAvailableRssChannelList() {
        final Set<String> rssChannelLinkSet = repository.getRssChannelLinkSet();
        if(rssChannelLinkSet != null) {
            rssChannelListView.showRssChannelSet(rssChannelLinkSet);
        }
    }
}
