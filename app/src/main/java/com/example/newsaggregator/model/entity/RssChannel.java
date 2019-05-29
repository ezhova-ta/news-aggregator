package com.example.newsaggregator.model.entity;

public class RssChannel {
    private String link;
    private boolean readed;

    public RssChannel() {}

    public RssChannel(final String link) {
        this.link = link;
    }

    public RssChannel(final String link, final boolean readed) {
        this.link = link;
        this.readed = readed;
    }

    public String getLink() {
        return link;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public void setReaded(final boolean readed) {
        this.readed = readed;
    }
}
