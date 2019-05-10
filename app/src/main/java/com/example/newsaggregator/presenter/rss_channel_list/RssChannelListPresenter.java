package com.example.newsaggregator.presenter.rss_channel_list;

import android.os.AsyncTask;

import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.model.repository.RssChannelListRepository;
import com.example.newsaggregator.view.news_entry_list.NewsEntryListActivity;
import com.example.newsaggregator.view.rss_channel_list.OnItemClickListener;
import com.example.newsaggregator.view.rss_channel_list.RssChannelListView;

import java.lang.ref.WeakReference;
import java.util.List;

public class RssChannelListPresenter implements OnItemClickListener {
    private final RssChannelListView rssChannelListView;
    private final RssChannelListRepository repository;

    public RssChannelListPresenter(final RssChannelListView rssChannelListView,
                                   final RssChannelListRepository repository) {
        this.rssChannelListView = rssChannelListView;
        this.repository = repository;
    }

    public void onCreate() {
        final ShowRssChannelListTask task = new ShowRssChannelListTask(this);
        task.execute();
    }

    public void onAddRssChannelButtonClick() {
        final String rssChannelLink = rssChannelListView.getAddRssChannelEditTextValue();
        if(!rssChannelLink.isEmpty()) {
            final AddRssChannelTask task = new AddRssChannelTask(this);
            task.execute(new RssChannel(rssChannelLink));
        }
    }

    @Override
    public void onItemClick(final RssChannel rssChannel) {
        rssChannelListView.startActivityToDisplayNewsEntryList(NewsEntryListActivity.class,
                RssChannelListView.RSS_CHANNEL_LINK_EXTRA_KEY, rssChannel.getLink());
    }

    private static final class ShowRssChannelListTask extends AsyncTask<Void, Void, List<RssChannel>> {
        private WeakReference<RssChannelListPresenter> presenterWeakReference;

        private ShowRssChannelListTask(final RssChannelListPresenter presenter) {
            presenterWeakReference = new WeakReference<>(presenter);
        }

        @Override
        protected List<RssChannel> doInBackground(final Void... voids) {
            /*
            TODO Обработать SQLiteException
             */
            return presenterWeakReference.get().repository.getRssChannelList();
        }

        @Override
        protected void onPostExecute(final List<RssChannel> rssChannelList) {
            if(!rssChannelList.isEmpty()) {
                presenterWeakReference.get().rssChannelListView.showRssChannelList(rssChannelList);
            }
        }
    }

    private static final class AddRssChannelTask extends AsyncTask<RssChannel, Void, Void> {
        private WeakReference<RssChannelListPresenter> presenterWeakReference;

        private AddRssChannelTask(final RssChannelListPresenter presenter) {
            presenterWeakReference = new WeakReference<>(presenter);
        }

        @Override
        protected Void doInBackground(final RssChannel... rssChannels) {
            for(final RssChannel rssChannel : rssChannels) {
                /*
                TODO Обработать SQLiteException
                 */
                presenterWeakReference.get().repository.addRssChannel(rssChannel);
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
            presenterWeakReference.get().rssChannelListView.clearAddRssChannelEditText();
            final ShowRssChannelListTask task = new ShowRssChannelListTask(presenterWeakReference.get());
            task.execute();
        }
    }
}
