package com.example.newsaggregator.view.channel.list;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.newsaggregator.R;
import com.example.newsaggregator.app.DependencyInjectionFactory;
import com.example.newsaggregator.app.NewsAggregatorApplication;
import com.example.newsaggregator.model.RssChannelListService;
import com.example.newsaggregator.model.entity.RssChannel;
import com.example.newsaggregator.presenter.channel.list.RssChannelListPresenter;
import com.example.newsaggregator.view.channel.list.deleting.DeletingRssChannelListActivity;
import com.example.newsaggregator.view.news.entry.list.NewsEntryListActivity;

import java.util.List;

public class RssChannelListActivity extends AppCompatActivity implements RssChannelListView {
    private static final String NOTIFICATIONS_PREFERENCES_NAME = "notifications";
    private static final String NOTIFICATIONS_PREFERENCES_KEY = "enabledNotifications";
    private static final String UNREAD_RSS_CHANNELS_PREFERENCES_NAME = "rssChannels";
    private static final String UNREAD_RSS_CHANNELS_PREFERENCES_KEY = "unreadRssChannelsSet";
    private static final int UPDATING_NEWS_ENTRY_LIST_DEFAULT_RESULT = 0;
    private static final int ALARM_MANAGER_REQUEST_CODE = 0;
    private static final int UPDATE_NOTIFICATION_ID = 513;

    private DependencyInjectionFactory diFactory;
    private BroadcastReceiver receiver;
    private EditText addRssChannelEditText;
    private EditText repeatingUpdateAlarmIntervalEditText;
    private Spinner repeatingIntervalUnitSpinner;
    private Button deleteRssChannelsButton;
    private Button enableUpdatingNotificationsButton;
    private Button disableUpdatingNotificationsButton;
    private RssChannelListPresenter presenter;
    private OnRssChannelListItemClickListener onRssChannelListItemClickListener;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_channel_list);
        initViewElement();

        final String action = getIntent().getAction();
        final String data = getIntent().getDataString();

        if(Intent.ACTION_VIEW.equals(action) && data != null) {
            onReceiveExternalRssChannel(data);
        }

        diFactory = NewsAggregatorApplication.getInstance().getDiFactory();
        onRssChannelListItemClickListener = diFactory.provideOnRssChannelListItemClickListener(this);
        presenter = diFactory.provideRssChannelListPresenter(this);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                final int requestResult = intent.getIntExtra(RssChannelListService.EXTRA_PARAM_REQUEST_RESULT,
                        UPDATING_NEWS_ENTRY_LIST_DEFAULT_RESULT);
                presenter.onReceiveBroadcastMessage(requestResult);
            }
        };

        final IntentFilter intentFilter = new IntentFilter(RssChannelListService.ACTION_UPDATE_NEWS_ENTRY_LISTS);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);

        final String action = intent.getAction();
        final String data = intent.getDataString();

        if(Intent.ACTION_VIEW.equals(action) && data != null) {
            onReceiveExternalRssChannel(data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void initViewElement() {
        addRssChannelEditText = findViewById(R.id.addRssChannelEditText);
        repeatingUpdateAlarmIntervalEditText = findViewById(R.id.repeatingUpdateAlarmIntervalEditText);
        recyclerView = findViewById(R.id.rssChannelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.rssChannelsLoadingProgress);
        repeatingIntervalUnitSpinner = (Spinner) findViewById(R.id.repeatingIntervalUnitSpinner);
        deleteRssChannelsButton = findViewById(R.id.deleteRssChannelsButton);
        enableUpdatingNotificationsButton = findViewById(R.id.enableUpdatingNotificationsButton);
        disableUpdatingNotificationsButton = findViewById(R.id.disableUpdatingNotificationsButton);
    }

    public void onAddRssChannelButtonClick(final View view) {
        presenter.onAddRssChannelButtonClick();
    }

    public void onDeleteRssChannelsButtonClick(final View view) {
        presenter.onDeleteRssChannelsButtonClick();
    }

    public void onEnableUpdatingNotificationsButtonClick(final View view) {
        presenter.onEnableUpdatingNotificationsButtonClick();
    }

    public void onDisableUpdatingNotificationsButtonClick(final View view) {
        presenter.onDisableUpdatingNotificationsButtonClick();
    }

    private void onReceiveExternalRssChannel(final String rssChannelLink) {
        presenter.onReceiveExternalRssChannel(rssChannelLink);
    }

    @Override
    public void showRssChannelList(final List<RssChannel> rssChannelList) {
        final RssChannelAdapter adapter = new RssChannelAdapter(this, rssChannelList);
        recyclerView.setAdapter(adapter);
        adapter.subscribeOnRssChannelListItemClick(onRssChannelListItemClickListener);
    }

    @Override
    public String getAddRssChannelEditTextValue() {
        return addRssChannelEditText.getText().toString();
    }

    @Override
    public String getRepeatingUpdateAlarmIntervalEditTextValue() {
        return repeatingUpdateAlarmIntervalEditText.getText().toString();
    }

    @Override
    public int getRepeatingIntervalUnitSpinnerSelectedItemPosition() {
        return repeatingIntervalUnitSpinner.getSelectedItemPosition();
    }

    @Override
    public void clearAddRssChannelEditText() {
        addRssChannelEditText.setText("");
    }

    @Override
    public void clearRepeatingUpdateAlarmIntervalEditText() {
        repeatingUpdateAlarmIntervalEditText.setText("");
    }

    @Override
    public void startActivityToDisplayNewsEntryList(final String rssChannelLinkExtraKey,
                                                    final String rssChannelLinkExtraValue) {
        final Intent intent = new Intent(this, NewsEntryListActivity.class);
        intent.putExtra(rssChannelLinkExtraKey, rssChannelLinkExtraValue);
        startActivity(intent);
    }

    @Override
    public void startActivityToDeleteRssChannels() {
        final Intent intent = new Intent(this, DeletingRssChannelListActivity.class);
        startActivity(intent);
    }

    @Override
    public void startAlarmManagerToUpdateNewsEntryLists(final long intervalMillis) {
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                ALARM_MANAGER_REQUEST_CODE,
                new Intent(this, UpdatingNewsEntryListsAlarmReceiver.class),
                PendingIntent.FLAG_CANCEL_CURRENT
        );
        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                intervalMillis,
                pendingIntent
        );
    }

    @Override
    public void stopAlarmManagerToUpdateNewsEntryLists() {
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                ALARM_MANAGER_REQUEST_CODE,
                new Intent(this, UpdatingNewsEntryListsAlarmReceiver.class),
                PendingIntent.FLAG_CANCEL_CURRENT
        );
        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void startServiceToUpdateNewsEntryLists() {
        final Intent intent = new Intent(this, RssChannelListService.class);
        intent.setAction(RssChannelListService.ACTION_UPDATE_NEWS_ENTRY_LISTS);
        startService(intent);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void showRssChannelsLoadingErrorMessage() {
        showPopupMessage(getResources().getText(R.string.rss_channels_loading_error_message));
    }

    @Override
    public void showRssChannelsAddingErrorMessage() {
        showPopupMessage(getResources().getText(R.string.rss_channels_adding_error_message));
    }

    @Override
    public void showRssChannelsAddingSuccessMessage() {
        showPopupMessage(getResources().getText(R.string.rss_channels_adding_success_message));
    }

    @Override
    public void showEmptyRssChannelListMessage() {
        showPopupMessage(getResources().getText(R.string.empty_rss_channel_list_message));
    }

    @Override
    public void showEnableUpdatingNotificationsMessage() {
        showPopupMessage(getResources().getText(R.string.enable_updating_notifications_message));
    }

    @Override
    public void showDisableUpdatingNotificationsMessage() {
        showPopupMessage(getResources().getText(R.string.disable_updating_notifications_message));
    }

    @Override
    public void showInvalidReepatingUpdateAlarmIntervalMessage() {
        showPopupMessage(getResources().getText(R.string.invalid_reepating_update_alarm_interval_message));
    }

    @Override
    public void showEmptyReepatingUpdateAlarmIntervalMessage() {
        showPopupMessage(getResources().getText(R.string.empty_reepating_update_alarm_interval_message));
    }

    @Override
    public void hideDeleteRssChannelsButton() {
        deleteRssChannelsButton.setVisibility(View.GONE);
    }

    @Override
    public void hideEnableUpdatingNotificationsButton() {
        enableUpdatingNotificationsButton.setVisibility(View.GONE);
    }

    @Override
    public void hideDisableUpdatingNotificationsButton() {
        disableUpdatingNotificationsButton.setVisibility(View.GONE);
    }

    @Override
    public void hideRepeatingUpdateAlarmIntervalEditText() {
        repeatingUpdateAlarmIntervalEditText.setVisibility(View.GONE);
    }

    @Override
    public void hideRepeatingIntervalUnitSpinner() {
        repeatingIntervalUnitSpinner.setVisibility(View.GONE);
    }

    @Override
    public void showEnableUpdatingNotificationsButton() {
        enableUpdatingNotificationsButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDisableUpdatingNotificationsButton() {
        disableUpdatingNotificationsButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDeleteRssChannelsButton() {
        deleteRssChannelsButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRepeatingUpdateAlarmIntervalEditText() {
        repeatingUpdateAlarmIntervalEditText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRepeatingIntervalUnitSpinner() {
        repeatingIntervalUnitSpinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void setEnabledNotificationsValue(final boolean isEnable) {
        getSharedPreferences(NOTIFICATIONS_PREFERENCES_NAME, MODE_PRIVATE)
                .edit()
                .putBoolean(NOTIFICATIONS_PREFERENCES_KEY, isEnable)
                .apply();
    }

    @Override
    public boolean getEnabledNotificationsValue() {
        return   getSharedPreferences(NOTIFICATIONS_PREFERENCES_NAME, MODE_PRIVATE)
                .getBoolean(NOTIFICATIONS_PREFERENCES_KEY, false);
    }

    private void showPopupMessage(final CharSequence text) {
        final Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
