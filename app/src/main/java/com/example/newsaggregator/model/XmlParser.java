package com.example.newsaggregator.model;

import com.example.newsaggregator.model.entity.NewsEntry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XmlParser implements Parser<List<NewsEntry>> {
    private static final String ITEM_TAG_NAME = "item";
    private static final String TITLE_TAG_NAME = "title";
    private static final String LINK_TAG_NAME = "link";
    private static final String DESCRIPTION_TAG_NAME = "description";
    private static final String PUB_DATE_TAG_NAME = "pubDate";

    private XmlPullParser getXmlParser(final URL url) throws IOException, XmlPullParserException {
        final XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
        final XmlPullParser parser = parserFactory.newPullParser();
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        parser.setInput(connection.getInputStream(), null);
        return parser;
    }

    @Override
    public List<NewsEntry> parse(final String url) throws XmlPullParserException, IOException {
        final XmlPullParser parser = getXmlParser(new URL(url));
        NewsEntry newsEntry = new NewsEntry();
        final List<NewsEntry> newsEntryList = new ArrayList<>(10);
        boolean isItem = false;

        while(parser.getEventType() != XmlPullParser.END_DOCUMENT) {
            if(parser.getEventType() == XmlPullParser.START_TAG) {
                if(ITEM_TAG_NAME.equals(parser.getName())) {
                    isItem = true;
                    parser.next();
                } else if(isItem && TITLE_TAG_NAME.equals(parser.getName())) {
                    parser.next();
                    newsEntry.setTitle(parser.getText());
                } else if(isItem && LINK_TAG_NAME.equals(parser.getName())) {
                    parser.next();
                    newsEntry.setLink(parser.getText());
                } else if(isItem && DESCRIPTION_TAG_NAME.equals(parser.getName())) {
                    parser.next();
                    newsEntry.setDescription(parser.getText());
                } else if(isItem && PUB_DATE_TAG_NAME.equals(parser.getName())) {
                    parser.next();
                    newsEntry.setPubDate(parser.getText());
                } else {
                    parser.next();
                }
            } else {
                if(parser.getEventType() == XmlPullParser.END_TAG && ITEM_TAG_NAME.equals(parser.getName())) {
                    newsEntryList.add(newsEntry);
                    isItem = false;
                    newsEntry = new NewsEntry();
                }
                parser.next();
            }
        }

        return newsEntryList;
    }
}
