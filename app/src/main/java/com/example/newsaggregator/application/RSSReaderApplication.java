package com.example.newsaggregator.application;

import android.app.Application;

import com.example.newsaggregator.db.DBWriter;

public class RSSReaderApplication extends Application {
    private static RSSReaderApplication instance;
    private DBWriter dbWriter;

    public static RSSReaderApplication getInstance() {
        return instance;
    }

    public DBWriter getDbWriter() {
        return dbWriter;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dbWriter = new DBWriter(this);
    }

    /*
    TODO Когда вызывать dbWriter.close()?
     */
}
