package com.example.newsaggregator.model;

public final class DbConstants {
    private DbConstants() {}

    public static final String DB_NAME = "rss_news_reader";
    public static final String RSS_CHANNELS_TABLE_NAME = "rss_channels";
    public static final String NEWS_ENTRIES_TABLE_NAME = "news_entries";

    public static final String RSS_CHANNEL_ID_FIELD = "id";
    public static final String RSS_CHANNEL_LINK_FIELD = "rss_channel_link";
    public static final String RSS_CHANNEL_READED_FIELD = "readed";
    public static final String NEWS_ENTRY_ID_FIELD = "id";
    public static final String NEWS_ENTRY_TITLE_FIELD = "title";
    public static final String NEWS_ENTRY_LINK_FIELD = "news_entry_link";
    public static final String NEWS_ENTRY_DESCRIPTION_FIELD = "description";
    public static final String NEWS_ENTRY_PUB_DATE_FIELD = "pub_date";
    public static final String NEWS_ENTRY_RSS_CHANNEL_ID_FIELD = "rss_channel_id";

    public static final long MILLISECONDS_PER_DAY = 86_400_000;
    public static final long NEWS_ENTRY_STORAGE_PERIOD_IN_MILLIS = MILLISECONDS_PER_DAY * 3;
    public static final long NEWS_ENTRIES_CHECK_PERIODICITY = MILLISECONDS_PER_DAY;

    public static final int DB_VERSION = 1;
}
