package com.example.newsaggregator.db;

public final class DbConstants {
    public static final String DB_NAME = "rss_news_reader";
    public static final String RSS_CHANNELS_TABLE_NAME = "rss_channels";
    public static final String NEWS_TABLE_NAME = "news";

    public static final String CHANNEL_ID_FIELD = "id";
    public static final String CHANNEL_LINK_FIELD = "link";
    public static final String NEWS_ID_FIELD = "id";
    public static final String NEWS_TITLE_FIELD = "title";
    public static final String NEWS_LINK_FIELD = "link";
    public static final String NEWS_DESCRIPTION_FIELD = "description";
    public static final String NEWS_PUB_DATE_FIELD = "pub_date";
    public static final String NEWS_RSS_CHANNEL_ID_FIELS = "rss_channel_id";

    private DbConstants() {}
}
