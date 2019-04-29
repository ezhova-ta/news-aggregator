package com.example.newsaggregator.application;

import android.app.Application;

public class RSSReaderApplication extends Application {
    private static RSSReaderApplication instance;
    private DBWriter dbWriter;
    private DBReader dbReader;

    public static RSSReaderApplication getInstance() {
        return instance;
    }

    public DBWriter getDbWriter() {
        return dbWriter;
    }

    public DBReader getDbReader() {
        return dbReader;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dbWriter = DBWriter.getInstance(this);
        dbReader = DBReader.getInstance(this);
    }

    /*
    TODO Когда вызывать dbWriter.close()?
     */
}
