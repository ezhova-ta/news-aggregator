package com.example.newsaggregator.presenter.news_entry_list;

import android.os.AsyncTask;

import com.example.newsaggregator.model.NewsEntryListService;
import com.example.newsaggregator.model.entity.NewsEntry;
import com.example.newsaggregator.model.repository.NewsEntryListRepository;
import com.example.newsaggregator.view.news_entry_list.NewsEntryListView;

import java.lang.ref.WeakReference;
import java.util.List;

public class NewsEntryListPresenter {
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
            /*
            TODO Сообщить пользователю об ошибке обновления данных
             */
        }
    }

    private void showNewsEntryList(final String rssChannelLink) {
        final ShowNewsEntryListTask task = new ShowNewsEntryListTask(this);
        task.execute(rssChannelLink);
    }

    private static final class ShowNewsEntryListTask extends AsyncTask<String, Void, List<NewsEntry>> {
        private final WeakReference<NewsEntryListPresenter> presenterWeakReference;

        private ShowNewsEntryListTask(final NewsEntryListPresenter presenter) {
            presenterWeakReference = new WeakReference<>(presenter);
        }

        @Override
        protected List<NewsEntry> doInBackground(final String... rssChannelLinks) {
            return presenterWeakReference.get().repository.getNewsEntryList(rssChannelLinks[0]);
        }

        @Override
        protected void onPostExecute(final List<NewsEntry> newsEntryList) {
            if(!newsEntryList.isEmpty()) {
                presenterWeakReference.get().newsEntryListView.showNewsEntryList(newsEntryList);
            }
        }
    }
}
