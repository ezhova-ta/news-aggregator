package com.example.newsaggregator.presenter.channel.list;

import android.os.AsyncTask;

import com.example.newsaggregator.model.DbException;
import com.example.newsaggregator.model.RssChannelListService;
import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.model.repository.RssChannelListRepository;
import com.example.newsaggregator.presenter.AsyncTaskResult;
import com.example.newsaggregator.presenter.VoidAsyncTaskResult;
import com.example.newsaggregator.view.channel.list.OnRssChannelListItemClickListener;
import com.example.newsaggregator.view.channel.list.RssChannelListView;

import java.lang.ref.WeakReference;
import java.util.List;

public class RssChannelListPresenterImpl implements RssChannelListPresenter, OnRssChannelListItemClickListener {
    private static final int MILLISECONDS_PER_MINUTE = 60_000;
    private static final int MILLISECONDS_PER_HOUR = 3_600_000;

    private final RssChannelListView rssChannelListView;
    private final RssChannelListRepository repository;

    public RssChannelListPresenterImpl(final RssChannelListView rssChannelListView,
                                       final RssChannelListRepository repository) {
        this.rssChannelListView = rssChannelListView;
        this.repository = repository;
    }

    @Override
    public void onResume() {
        if(rssChannelListView.getEnabledNotificationsValue()) {
            rssChannelListView.hideEnableUpdatingNotificationsButton();
            rssChannelListView.hideRepeatingUpdateAlarmIntervalEditText();
            rssChannelListView.hideRepeatingIntervalUnitSpinner();
            rssChannelListView.showDisableUpdatingNotificationsButton();
        } else {
            rssChannelListView.hideDisableUpdatingNotificationsButton();
            rssChannelListView.showEnableUpdatingNotificationsButton();
            rssChannelListView.showRepeatingIntervalUnitSpinner();
            rssChannelListView.showRepeatingUpdateAlarmIntervalEditText();
        }

        final ShowRssChannelListTask task = new ShowRssChannelListTask(this);
        task.execute();
    }

    @Override
    public void onAddRssChannelButtonClick() {
        final String rssChannelLink = rssChannelListView.getAddRssChannelEditTextValue();
        if(!rssChannelLink.isEmpty()) {
            final AddRssChannelTask task = new AddRssChannelTask(this);
            task.execute(new RssChannel(rssChannelLink));
        }
    }

    @Override
    public void onDeleteRssChannelsButtonClick() {
        rssChannelListView.startActivityToDeleteRssChannels();
    }

    @Override
    public void onEnableUpdatingNotificationsButtonClick() {
        final int spinnerPositionMinutes = 0;
        final int spinnerPositionHours = 1;
        final String repeatingUpdateAlarmInterval =
                rssChannelListView.getRepeatingUpdateAlarmIntervalEditTextValue();
        final int spinnerSelectedItemPosition =
                rssChannelListView.getRepeatingIntervalUnitSpinnerSelectedItemPosition();
        final long intervalMillis;

        if(!repeatingUpdateAlarmInterval.isEmpty()) {
            try {
                if(spinnerPositionMinutes == spinnerSelectedItemPosition) {
                    intervalMillis = Long.parseLong(repeatingUpdateAlarmInterval) * MILLISECONDS_PER_MINUTE;
                } else if(spinnerPositionHours == spinnerSelectedItemPosition) {
                    intervalMillis = Long.parseLong(repeatingUpdateAlarmInterval) * MILLISECONDS_PER_HOUR;
                } else {
                    return;
                }

                rssChannelListView.hideEnableUpdatingNotificationsButton();
                rssChannelListView.hideRepeatingUpdateAlarmIntervalEditText();
                rssChannelListView.hideRepeatingIntervalUnitSpinner();
                rssChannelListView.showDisableUpdatingNotificationsButton();
                rssChannelListView.setEnabledNotificationsValue(true);
                rssChannelListView.showEnableUpdatingNotificationsMessage();

                rssChannelListView.startAlarmManagerToUpdateNewsEntryLists(intervalMillis);
            } catch(final NumberFormatException e) {
                rssChannelListView.showInvalidReepatingUpdateAlarmIntervalMessage();
            }
        } else {
            rssChannelListView.showEmptyReepatingUpdateAlarmIntervalMessage();
        }
    }

    @Override
    public void onDisableUpdatingNotificationsButtonClick() {
        rssChannelListView.hideDisableUpdatingNotificationsButton();
        rssChannelListView.showEnableUpdatingNotificationsButton();
        rssChannelListView.showRepeatingIntervalUnitSpinner();
        rssChannelListView.clearRepeatingUpdateAlarmIntervalEditText();
        rssChannelListView.showRepeatingUpdateAlarmIntervalEditText();
        rssChannelListView.setEnabledNotificationsValue(false);
        rssChannelListView.showDisableUpdatingNotificationsMessage();
        rssChannelListView.stopAlarmManagerToUpdateNewsEntryLists();
    }

    @Override
    public void onReceiveBroadcastMessage(final int requestResult) {
        if(requestResult == RssChannelListService.UPDATING_NEWS_ENTRY_LISTS_RESULT_FRESH_NEWS_ENTRIES) {
            final ShowRssChannelListTask task = new ShowRssChannelListTask(this);
            task.execute();
        }
    }

    @Override
    public void onReceiveExternalRssChannel(final String rssChannelLink) {
        final AddRssChannelTask task = new AddRssChannelTask(this);
        task.execute(new RssChannel(rssChannelLink));
        rssChannelListView.startActivityToDisplayNewsEntryList(
                RssChannelListView.RSS_CHANNEL_LINK_EXTRA_KEY,
                rssChannelLink
        );

        try {
            repository.setRssChannelReaded(rssChannelLink, true);
        } catch (final DbException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void onRssChannelListItemClick(final RssChannel rssChannel) {
        rssChannelListView.startActivityToDisplayNewsEntryList(
                RssChannelListView.RSS_CHANNEL_LINK_EXTRA_KEY,
                rssChannel.getLink()
        );

        try {
            repository.setRssChannelReaded(rssChannel.getLink(), true);
        } catch (final DbException e) {
            e.printStackTrace(System.err);
        }
    }

    private static final class ShowRssChannelListTask extends
            AsyncTask<Void, Void, AsyncTaskResult<List<RssChannel>>> {
        private WeakReference<RssChannelListPresenterImpl> presenter;

        private ShowRssChannelListTask(final RssChannelListPresenterImpl presenter) {
            this.presenter = new WeakReference<>(presenter);
        }

        @Override
        protected void onPreExecute() {
            presenter.get().rssChannelListView.showProgressBar();
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
            presenter.get().rssChannelListView.hideProgressBar();

            if(result.getException() != null) {
                presenter.get().rssChannelListView.showRssChannelsLoadingErrorMessage();
            } else {
                if(result.getResult().isEmpty()) {
                    presenter.get().rssChannelListView.showEmptyRssChannelListMessage();
                    presenter.get().rssChannelListView.hideDeleteRssChannelsButton();
                } else {
                    presenter.get().rssChannelListView.showDeleteRssChannelsButton();
                }
                presenter.get().rssChannelListView.showRssChannelList(result.getResult());
            }
        }
    }

    private static final class AddRssChannelTask extends AsyncTask<RssChannel, Void, VoidAsyncTaskResult> {
        private WeakReference<RssChannelListPresenterImpl> presenter;

        private AddRssChannelTask(final RssChannelListPresenterImpl presenter) {
            this.presenter = new WeakReference<>(presenter);
        }

        @Override
        protected VoidAsyncTaskResult doInBackground(final RssChannel... rssChannels) {
            try {
                for(final RssChannel rssChannel : rssChannels) {
                    presenter.get().repository.addRssChannel(rssChannel);
                }
                return new VoidAsyncTaskResult();
            } catch(final DbException e) {
                return new VoidAsyncTaskResult(e);
            }
        }

        @Override
        protected void onPostExecute(final VoidAsyncTaskResult result) {
            if(result.getException() != null) {
                presenter.get().rssChannelListView.showRssChannelsAddingErrorMessage();
            } else {
                presenter.get().rssChannelListView.clearAddRssChannelEditText();
                final ShowRssChannelListTask task = new ShowRssChannelListTask(presenter.get());
                task.execute();
                presenter.get().rssChannelListView.showRssChannelsAddingSuccessMessage();
            }
        }
    }
}
