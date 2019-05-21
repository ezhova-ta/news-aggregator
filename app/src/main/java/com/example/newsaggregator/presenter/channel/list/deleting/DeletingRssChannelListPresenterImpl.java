package com.example.newsaggregator.presenter.channel.list.deleting;

import android.os.AsyncTask;

import com.example.newsaggregator.model.DbException;
import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.model.repository.RssChannelListRepository;
import com.example.newsaggregator.presenter.AsyncTaskResult;
import com.example.newsaggregator.presenter.VoidAsyncTaskResult;
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
    public void onConfirmDeletingRssChannelsButtonClick() {
        final List<String> checkedRssChannelLinkList = deletingRssChannelListView.getCheckedRssChannelLinkList();
        if(!checkedRssChannelLinkList.isEmpty()) {
            final DeleteRssChannelListTask task = new DeleteRssChannelListTask(this);
            task.execute(checkedRssChannelLinkList);
        }
    }

    @Override
    public void onRssChannelListItemCheck(final RssChannel rssChannel, final boolean checked) {
        if(checked) {
            deletingRssChannelListView.addRssChannelLink(rssChannel.getLink());
        } else {
            deletingRssChannelListView.removeRssChannelLink(rssChannel.getLink());
        }
    }

    private static final class ShowRssChannelListTask extends AsyncTask<Void, Void, AsyncTaskResult<List<RssChannel>>> {
        private static final String MESSAGE_SUCCESSFUL_DATA_DOWNLOADING = "RSS-channels downloaded successfully";
        private static final String MESSAGE_UNSUCCESSFUL_DATA_DOWNLOADING = "Downloading RSS-channels error!";
        private WeakReference<DeletingRssChannelListPresenterImpl> presenter;

        private ShowRssChannelListTask(final DeletingRssChannelListPresenterImpl presenter) {
            this.presenter = new WeakReference<>(presenter);
        }

        @Override
        protected void onPreExecute() {
            presenter.get().deletingRssChannelListView.showProgressBar();
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
            presenter.get().deletingRssChannelListView.hideProgressBar();

            if(result.getException() != null) {
                presenter.get().deletingRssChannelListView.showPopupMessage(MESSAGE_UNSUCCESSFUL_DATA_DOWNLOADING);
            } else if(!result.getResult().isEmpty()) {
                presenter.get().deletingRssChannelListView.showRssChannelList(result.getResult());
            }
        }
    }

    private static final class DeleteRssChannelListTask extends AsyncTask<List<String>, Void, VoidAsyncTaskResult> {
        private static final String MESSAGE_SUCCESSFUL_DATA_DELETING = "RSS-channels deleted successfully";
        private static final String MESSAGE_UNSUCCESSFUL_DATA_DELETING = "Deleting RSS-channels error!";
        private WeakReference<DeletingRssChannelListPresenterImpl> presenter;

        private DeleteRssChannelListTask(final DeletingRssChannelListPresenterImpl presenter) {
            this.presenter = new WeakReference<>(presenter);
        }

        @Override
        protected VoidAsyncTaskResult doInBackground(final List<String>... linkLists) {
            try {
                for(final List<String> linkList : linkLists) {
                    for(final String link: linkList) {
                        presenter.get().repository.deleteRssChannel(link);
                    }
                }
                return new
                        VoidAsyncTaskResult();
            } catch(final DbException e) {
                return new VoidAsyncTaskResult(e);
            }
        }

        @Override
        protected void onPostExecute(final VoidAsyncTaskResult result) {
            if(result.getException() != null) {
                presenter.get().deletingRssChannelListView.showPopupMessage(MESSAGE_UNSUCCESSFUL_DATA_DELETING);
            } else {
                final ShowRssChannelListTask task = new ShowRssChannelListTask(presenter.get());
                task.execute();
                presenter.get().deletingRssChannelListView.showPopupMessage(MESSAGE_SUCCESSFUL_DATA_DELETING);
                presenter.get().deletingRssChannelListView.clearCheckedRssChannelLinkList();
            }
        }
    }
}
