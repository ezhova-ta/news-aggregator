package com.example.newsaggregator.presenter.rss_channel_list;

import android.os.AsyncTask;

import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.model.repository.RssChannelListRepository;
import com.example.newsaggregator.view.news_entry_list.NewsEntryListActivity;
import com.example.newsaggregator.view.rss_channel_list.RssChannelListView;

import java.util.List;

public class RssChannelListPresenter {
    private final RssChannelListView rssChannelListView;
    private final RssChannelListRepository repository;

    public RssChannelListPresenter(final RssChannelListView rssChannelListView,
                                   final RssChannelListRepository repository) {
        this.rssChannelListView = rssChannelListView;
        this.repository = repository;
        /*
        TODO repository.closeResources()
         */
    }

    public void onCreate() {
        showAvailableRssChannelList();
    }

    public void onAddRssChannelButtonClick() {
        final String rssChannelLink = rssChannelListView.getAddRssChannelEditTextValue();
        if(!rssChannelLink.isEmpty()) {
            final AddRssChannelTask task = new AddRssChannelTask();
            task.execute(new RssChannel(rssChannelLink));
        }
    }

    public void onRssChannelListItemClick(final RssChannel rssChannel) {
        /*
        TODO Вынести magic const в константы
         */
        rssChannelListView.startActivityToDisplayNewsEntryList(NewsEntryListActivity.class,
                "rssChannelLink", rssChannel.getLink());
    }

    private void showAvailableRssChannelList() {
        final ShowRssChannelListTask task = new ShowRssChannelListTask();
        task.execute();
    }

    private class ShowRssChannelListTask extends AsyncTask<Void ,Void, List<RssChannel>> {
        @Override
        protected List<RssChannel> doInBackground(final Void... voids) {
            /*
            TODO Обработать SQLiteException
             */
            return repository.getRssChannelList();
        }

        @Override
        protected void onPostExecute(final List<RssChannel> rssChannelList) {
            rssChannelListView.showRssChannelList(rssChannelList);
        }
    }

    private class AddRssChannelTask extends AsyncTask<RssChannel, Void, Void> {
        @Override
        protected Void doInBackground(final RssChannel... rssChannels) {
            for(final RssChannel rssChannel : rssChannels) {
                /*
                TODO Обработать SQLiteException
                 */
                repository.addRssChannel(rssChannel);
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
            rssChannelListView.clearAddRssChannelEditText();
            showAvailableRssChannelList();
        }
    }
}
