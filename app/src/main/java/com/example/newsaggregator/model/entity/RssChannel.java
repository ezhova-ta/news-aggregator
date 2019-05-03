package com.example.newsaggregator.model.entity;

public class RssChannel {
    private String link;

    public RssChannel() {}

    public RssChannel(final String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
    }
}
