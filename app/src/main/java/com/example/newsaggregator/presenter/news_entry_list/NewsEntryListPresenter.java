package com.example.newsaggregator.presenter.news_entry_list;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import com.example.newsaggregator.model.NewsEntryListService;
import com.example.newsaggregator.model.entity.NewsEntry;
import com.example.newsaggregator.model.repository.NewsEntryListRepository;
import com.example.newsaggregator.presenter.AsyncTaskResult;
import com.example.newsaggregator.view.news_entry.NewsEntryActivity;
import com.example.newsaggregator.view.news_entry_list.NewsEntryListView;
import com.example.newsaggregator.view.rss_channel_list.OnNewsEntryLinkClickListener;

import java.lang.ref.WeakReference;
import java.util.List;

public class NewsEntryListPresenter implements OnNewsEntryLinkClickListener {
    private static final String MESSAGE_UNSUCCESSFUL_DATA_UPDATING = "Updating news entry list error!";
    private final NewsEntryListView newsEntryListView;
    private final NewsEntryListRepository repository;

    public NewsEntryListPresenter(final NewsEntryListView newsEntryListView,
                                  final NewsEntryListRepository repository) {
        this.newsEntryListView = newsEntryListView;
        this.repository = repository;
    }

    public void onCreate() {
        showNewsEntryList(newsEntryListView.getRssChannelLink());
    }

    public void onUpdateNewsEntryListButtonClick() {
        newsEntryListView.startServiceToUpdateNewsEntryList();
    }

    public void onReceiveBroadcastMessage(final int requestResult) {
        if(requestResult == NewsEntryListService.FETCHING_NEWS_ENTRY_LIST_RESULT_OK) {
            showNewsEntryList(newsEntryListView.getRssChannelLink());
        } else if(requestResult == NewsEntryListService.FETCHING_NEWS_ENTRY_LIST_RESULT_FAILING ||
                requestResult == NewsEntryListView.FETCHING_NEWS_ENTRY_LIST_DEFAULT_RESULT) {
            newsEntryListView.showPopupMessage(MESSAGE_UNSUCCESSFUL_DATA_UPDATING);
        }
    }

    private void showNewsEntryList(final String rssChannelLink) {
        final ShowNewsEntryListTask task = new ShowNewsEntryListTask(this);
        task.execute(rssChannelLink);
    }

    @Override
    public void onNewsEntryLinkClick(final String newsEntryLink) {
        newsEntryListView.startActivityToDisplayNewsEntry(NewsEntryActivity.class,
                NewsEntryListView.NEWS_ENTRY_LINK_EXTRA_KEY, newsEntryLink);
    }

    private static final class ShowNewsEntryListTask extends AsyncTask<String, Void, AsyncTaskResult<List<NewsEntry>>> {
        private static final String MESSAGE_SUCCESSFUL_DATA_DOWNLOADING = "News entry list downloaded successfully";
        private static final String MESSAGE_UNSUCCESSFUL_DATA_DOWNLOADING = "Downloading news entry list error!";
        private final WeakReference<NewsEntryListPresenter> presenter;

        private ShowNewsEntryListTask(final NewsEntryListPresenter presenter) {
            this.presenter = new WeakReference<>(presenter);
        }

        @Override
        protected AsyncTaskResult<List<NewsEntry>> doInBackground(final String... rssChannelLinks) {
            try {
                final List<NewsEntry> newsEntryList = presenter.get().repository.getNewsEntryList(rssChannelLinks[0]);
                return new AsyncTaskResult<>(newsEntryList);
            } catch(final SQLiteException e) {
                return new AsyncTaskResult<>(e);
            }
        }

        @Override
        protected void onPostExecute(final AsyncTaskResult<List<NewsEntry>> result) {
            if(result.getException() != null) {
                presenter.get().newsEntryListView.showPopupMessage(MESSAGE_UNSUCCESSFUL_DATA_DOWNLOADING);
            } else if(!result.getResult().isEmpty()) {
                presenter.get().newsEntryListView.showNewsEntryList(result.getResult());
                presenter.get().newsEntryListView.showPopupMessage(MESSAGE_SUCCESSFUL_DATA_DOWNLOADING);
            }
        }
    }
}
