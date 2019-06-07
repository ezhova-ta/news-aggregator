package com.example.newsaggregator.presenter.news.entry.list;

import android.os.AsyncTask;

import com.example.newsaggregator.model.DbConstants;
import com.example.newsaggregator.model.DbException;
import com.example.newsaggregator.model.NewsEntryListService;
import com.example.newsaggregator.model.entity.NewsEntry;
import com.example.newsaggregator.model.repository.NewsEntryListRepository;
import com.example.newsaggregator.presenter.AsyncTaskResult;
import com.example.newsaggregator.presenter.VoidAsyncTaskResult;
import com.example.newsaggregator.view.news.entry.list.NewsEntryListView;
import com.example.newsaggregator.view.news.entry.list.OnNewsEntryListItemClickListener;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;

public class NewsEntryListPresenterImpl implements NewsEntryListPresenter, OnNewsEntryListItemClickListener {
    private final NewsEntryListView newsEntryListView;
    private final NewsEntryListRepository repository;

    public NewsEntryListPresenterImpl(final NewsEntryListView newsEntryListView,
                                      final NewsEntryListRepository repository) {
        this.newsEntryListView = newsEntryListView;
        this.repository = repository;
    }

    @Override
    public void onCreate() {
        final long currentDateInMillis = Calendar.getInstance().getTimeInMillis();
        final long newsEntriesDetetionDateInMillis = newsEntryListView.getNewsEntriesDetetionDateInMillis();

        if(newsEntriesDetetionDateInMillis < (currentDateInMillis - DbConstants.NEWS_ENTRIES_CHECK_PERIODICITY) ||
                newsEntriesDetetionDateInMillis == NewsEntryListView.DEFAULT_PREFERENCE_VALUE) {
            deleteOutdatedNewsEntries();
            newsEntryListView.setNewsEntriesDetetionDate(currentDateInMillis);
        }

        showNewsEntryList(newsEntryListView.getRssChannelLink());
    }

    @Override
    public void onUpdateNewsEntryListButtonClick() {
        newsEntryListView.showProgressBar();
        newsEntryListView.startServiceToUpdateNewsEntryList();
    }

    @Override
    public void onReceiveBroadcastMessage(final int requestResult) {
        newsEntryListView.hideProgressBar();

        if(requestResult == NewsEntryListService.FETCHING_NEWS_ENTRY_LIST_RESULT_OK) {
            showNewsEntryList(newsEntryListView.getRssChannelLink());
        } else if(requestResult == NewsEntryListService.FETCHING_NEWS_ENTRY_LIST_RESULT_FAILING ||
                requestResult == NewsEntryListView.FETCHING_NEWS_ENTRY_LIST_DEFAULT_RESULT) {
            newsEntryListView.showNewsEntriesUpdatingErrorMessage();
        }
    }

    private void showNewsEntryList(final String rssChannelLink) {
        final ShowNewsEntryListTask task = new ShowNewsEntryListTask(this);
        task.execute(rssChannelLink);
    }

    private void deleteOutdatedNewsEntries() {
        final DeleteOutdatedNewsEntriesTask task = new DeleteOutdatedNewsEntriesTask(this);
        task.execute();
    }

    @Override
    public void onNewsEntryListItemClick(final String newsEntryLink) {
        newsEntryListView.startActivityToDisplayNewsEntry(NewsEntryListView.NEWS_ENTRY_LINK_EXTRA_KEY,
                newsEntryLink);
    }

    private static final class ShowNewsEntryListTask extends AsyncTask<String, Void, AsyncTaskResult<List<NewsEntry>>> {
        private final WeakReference<NewsEntryListPresenterImpl> presenter;

        private ShowNewsEntryListTask(final NewsEntryListPresenterImpl presenter) {
            this.presenter = new WeakReference<>(presenter);
        }

        @Override
        protected AsyncTaskResult<List<NewsEntry>> doInBackground(final String... rssChannelLinks) {
            try {
                final List<NewsEntry> newsEntryList = presenter.get().repository.getNewsEntryList(rssChannelLinks[0]);
                return new AsyncTaskResult<>(newsEntryList);
            } catch(final DbException e) {
                return new AsyncTaskResult<>(e);
            }
        }

        @Override
        protected void onPostExecute(final AsyncTaskResult<List<NewsEntry>> result) {
            if(result.getException() != null) {
                presenter.get().newsEntryListView.showNewsEntriesLoadingErrorMessage();
            } else {
                if(result.getResult().isEmpty()) {
                    presenter.get().newsEntryListView.showProgressBar();
                    presenter.get().newsEntryListView.startServiceToUpdateNewsEntryList();
                } else {
                    presenter.get().newsEntryListView.showNewsEntryList(result.getResult());
                }
            }
        }
    }

    private static final class DeleteOutdatedNewsEntriesTask extends AsyncTask<Void, Void, VoidAsyncTaskResult> {
        private WeakReference<NewsEntryListPresenterImpl> presenter;

        private DeleteOutdatedNewsEntriesTask(final NewsEntryListPresenterImpl presenter) {
            this.presenter = new WeakReference<>(presenter);
        }

        /*
        TODO Обработка ошибок
        */
        @Override
        protected VoidAsyncTaskResult doInBackground(final Void... voids) {
            try {
                presenter.get().repository.deleteOutdatedNewsEntries();
                return new VoidAsyncTaskResult();
            } catch(final DbException e) {
                return new VoidAsyncTaskResult(e);
            }
        }
    }
}
