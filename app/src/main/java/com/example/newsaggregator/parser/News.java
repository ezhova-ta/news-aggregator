package com.example.newsaggregator.parser;

public class News {
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private long channelId;

    public News() {}

    public News(String title, String link, String description, String pubDate, long channelId) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.channelId = channelId;
    }

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

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", channelId=" + channelId +
                '}';
    }
}
