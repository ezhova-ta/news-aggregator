package com.example.newsaggregator.presenter.channel.list.deleting;

import android.os.AsyncTask;

import com.example.newsaggregator.model.DbException;
import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.model.repository.RssChannelListRepository;
import com.example.newsaggregator.presenter.AsyncTaskResult;
import com.example.newsaggregator.view.channel.list.deleting.DeletingRssChannelListView;
import com.example.newsaggregator.view.channel.list.deleting.OnRssChannelListItemCheckListener;

import java.lang.ref.WeakReference;
import java.util.List;

public class DeletingRssChannelListPresenterImpl implements DeletingRssChannelListPresenter, OnRssChannelListItemCheckListener {
    private DeletingRssChannelListView deletingRssChannelListView;
    private RssChannelListRepository repository;

    public DeletingRssChannelListPresenterImpl(final DeletingRssChannelListView deletingRssChannelListView,
                                       final RssChannelListRepository repository) {
        this.deletingRssChannelListView = deletingRssChannelListView;
        this.repository = repository;
    }

    @Override
    public void onCreate() {
        final ShowRssChannelListTask task = new ShowRssChannelListTask(this);
        task.execute();
    }

    @Override
    public void onRssChannelListItemCheck(final RssChannel rssChannel, final boolean checked) {
        System.out.println("---> Click on rss-channel: " + rssChannel.getLink() + ". Checked: " + checked);
    }

    private static final class ShowRssChannelListTask extends AsyncTask<Void, Void, AsyncTaskResult<List<RssChannel>>> {
        private static final String MESSAGE_SUCCESSFUL_DATA_DOWNLOADING = "RSS-channels downloaded successfully";
        private static final String MESSAGE_UNSUCCESSFUL_DATA_DOWNLOADING = "Downloading RSS-channels error!";
        private WeakReference<DeletingRssChannelListPresenterImpl> presenter;

        private ShowRssChannelListTask(final DeletingRssChannelListPresenterImpl presenter) {
            this.presenter = new WeakReference<>(presenter);
        }

        @Override
        protected AsyncTaskResult<List<RssChannel>> doInBackground(final Void... voids) {
            try {
                final List<RssChannel> rssChannelList = presenter.get().repository.getRssChannelList();
                return new AsyncTaskResult<>(rssChannelList);
            } catch(final DbException e) {
                return new AsyncTaskResult<>(e);
            }
        }

        @Override
        protected void onPostExecute(final AsyncTaskResult<List<RssChannel>> result) {
            if(result.getException() != null) {
                presenter.get().deletingRssChannelListView.showPopupMessage(MESSAGE_UNSUCCESSFUL_DATA_DOWNLOADING);
            } else if(!result.getResult().isEmpty()) {
                presenter.get().deletingRssChannelListView.showRssChannelList(result.getResult());
                presenter.get().deletingRssChannelListView.showPopupMessage(MESSAGE_SUCCESSFUL_DATA_DOWNLOADING);
            }
        }
    }
}
