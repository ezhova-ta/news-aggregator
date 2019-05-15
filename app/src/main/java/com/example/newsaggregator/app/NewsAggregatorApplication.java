package com.example.newsaggregator.app;

import android.app.Application;

public class NewsAggregatorApplication extends Application {
    private static NewsAggregatorApplication instance;
    private DIFactory diFactory;

    public static NewsAggregatorApplication getInstance() {
        return instance;
    }

    public DIFactory getDiFactory() {
        return diFactory;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        diFactory = new DIFactory(getApplicationContext());
    }
}
