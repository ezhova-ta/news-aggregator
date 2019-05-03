package com.example.newsaggregator.model.entity;

public class NewsEntry {
    private String title;
    private String link;
    private String description;
    private String pubDate;

    public NewsEntry() {}

    public NewsEntry(final String title, final String link, final String description,
                final String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setPubDate(final String pubDate) {
        this.pubDate = pubDate;
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
}
