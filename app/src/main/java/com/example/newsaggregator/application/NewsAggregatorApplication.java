package com.example.newsaggregator.application;

import android.app.Application;
import android.content.Context;

public class NewsAggregatorApplication extends Application {
    private static NewsAggregatorApplication instance;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
    }

    public static NewsAggregatorApplication getInstance() {
        return instance;
    }

    public Context getContext() {
        return context;
    }
}
