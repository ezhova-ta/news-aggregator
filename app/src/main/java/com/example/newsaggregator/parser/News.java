package com.example.newsaggregator.parser;

public class News {
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private long channelId;

    public News() {}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public long getChannelId() {
        return channelId;
    }
}
