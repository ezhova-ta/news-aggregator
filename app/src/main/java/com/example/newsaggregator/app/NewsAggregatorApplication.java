package com.example.newsaggregator.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.newsaggregator.R;

public class NewsAggregatorApplication extends Application {
    private static NewsAggregatorApplication instance;
    private DependencyInjectionFactory diFactory;

    public static final String NOTIFICATION_CHANNEL_ID = "com.example.newsaggregator";
    public static final String NOTIFICATION_CHANNEL_NAME = "newsAggregator";
    private String NOTIFICATION_CHANNEL_DESCRIPTION;

    public static NewsAggregatorApplication getInstance() {
        return instance;
    }

    public DependencyInjectionFactory getDiFactory() {
        return diFactory;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        diFactory = new DependencyInjectionFactory(getApplicationContext());
        NOTIFICATION_CHANNEL_DESCRIPTION =
                getInstance()
                .getResources()
                .getText(R.string.notification_channel_description)
                .toString();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION);
            final NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
