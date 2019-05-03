package com.example.newsaggregator.model;

public abstract class DbConstants {
    public static final String DB_NAME = "rss_news_reader";
    public static final String RSS_CHANNELS_TABLE_NAME = "rss_channels";
    public static final String NEWS_ENTRIES_TABLE_NAME = "news_entries";

    public static final String RSS_CHANNEL_ID_FIELD = "id";
    public static final String RSS_CHANNEL_LINK_FIELD = "link";
    public static final String NEWS_ENTRY_ID_FIELD = "id";
    public static final String NEWS_ENTRY_TITLE_FIELD = "title";
    public static final String NEWS_ENTRY_LINK_FIELD = "link";
    public static final String NEWS_ENTRY_DESCRIPTION_FIELD = "description";
    public static final String NEWS_ENTRY_PUB_DATE_FIELD = "pub_date";
    public static final String NEWS_ENTRY_RSS_CHANNEL_ID_FIELD = "rss_channel_id";
}
