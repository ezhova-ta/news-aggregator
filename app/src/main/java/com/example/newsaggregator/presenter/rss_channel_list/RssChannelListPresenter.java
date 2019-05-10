package com.example.newsaggregator.presenter.rss_channel_list;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.model.repository.RssChannelListRepository;
import com.example.newsaggregator.presenter.AsyncTaskResult;
import com.example.newsaggregator.presenter.VoidAsyncTaskResult;
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

    private static final class ShowRssChannelListTask extends AsyncTask<Void, Void, AsyncTaskResult<List<RssChannel>>> {
        private static final String MESSAGE_SUCCESSFUL_DATA_DOWNLOADING = "RSS-channels downloaded successfully!";
        private static final String MESSAGE_UNSUCCESSFUL_DATA_DOWNLOADING = "Downloading RSS-channels error!";
        private WeakReference<RssChannelListPresenter> presenter;

        private ShowRssChannelListTask(final RssChannelListPresenter presenter) {
            this.presenter = new WeakReference<>(presenter);
        }

        @Override
        protected AsyncTaskResult<List<RssChannel>> doInBackground(final Void... voids) {
            try {
                final List<RssChannel> rssChannelList = presenter.get().repository.getRssChannelList();
                return new AsyncTaskResult<>(rssChannelList);
            } catch(final SQLiteException e) {
                return new AsyncTaskResult<>(e);
            }
        }

        @Override
        protected void onPostExecute(final AsyncTaskResult<List<RssChannel>> result) {
            if(result.getException() != null) {
                presenter.get().rssChannelListView.showPopupMessage(MESSAGE_UNSUCCESSFUL_DATA_DOWNLOADING);
            } else if(!result.getResult().isEmpty()) {
                presenter.get().rssChannelListView.showRssChannelList(result.getResult());
                presenter.get().rssChannelListView.showPopupMessage(MESSAGE_SUCCESSFUL_DATA_DOWNLOADING);
            }
        }
    }

    private static final class AddRssChannelTask extends AsyncTask<RssChannel, Void, VoidAsyncTaskResult> {
        private static final String MESSAGE_SUCCESSFUL_DATA_ADDING = "RSS-channels added successfully!";
        private static final String MESSAGE_UNSUCCESSFUL_DATA_ADDING = "Adding RSS-channels error!";
        private WeakReference<RssChannelListPresenter> presenter;

        private AddRssChannelTask(final RssChannelListPresenter presenter) {
            this.presenter = new WeakReference<>(presenter);
        }

        @Override
        protected VoidAsyncTaskResult doInBackground(final RssChannel... rssChannels) {
            try {
                for(final RssChannel rssChannel : rssChannels) {
                    presenter.get().repository.addRssChannel(rssChannel);
                }
                return new VoidAsyncTaskResult();
            } catch(final SQLiteException e) {
                return new VoidAsyncTaskResult(e);
            }
        }

        @Override
        protected void onPostExecute(final VoidAsyncTaskResult result) {
            if(result.getException() != null) {
                presenter.get().rssChannelListView.showPopupMessage(MESSAGE_UNSUCCESSFUL_DATA_ADDING);
            } else {
                presenter.get().rssChannelListView.clearAddRssChannelEditText();
                final ShowRssChannelListTask task = new ShowRssChannelListTask(presenter.get());
                task.execute();
                presenter.get().rssChannelListView.showPopupMessage(MESSAGE_SUCCESSFUL_DATA_ADDING);
            }
        }
    }
}
