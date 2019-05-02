package com.example.newsaggregator.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    private URL url;

    public XmlParser(final String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    private XmlPullParser getXmlParser() throws IOException, XmlPullParserException {
        final XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
        final XmlPullParser parser = parserFactory.newPullParser();
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        parser.setInput(connection.getInputStream(), null);
        return parser;
    }

    public List<NewsEntry> parseXml() throws XmlPullParserException, IOException {
        final XmlPullParser parser = getXmlParser();
        NewsEntry newsEntry = new NewsEntry();
        final List<NewsEntry> newsEntryList = new ArrayList<>(10);
        boolean isItem = false;

        while(parser.getEventType() != XmlPullParser.END_DOCUMENT) {
            if(parser.getEventType() == XmlPullParser.START_TAG) {
                if("item".equals(parser.getName())) {
                    isItem = true;
                    parser.next();
                } else if(isItem && "title".equals(parser.getName())) {
                    parser.next();
                    newsEntry.setTitle(parser.getText());
                } else if(isItem && "link".equals(parser.getName())) {
                    parser.next();
                    newsEntry.setLink(parser.getText());
                } else if(isItem && "description".equals(parser.getName())) {
                    parser.next();
                    newsEntry.setDescription(parser.getText());
                } else if(isItem && "pubDate".equals(parser.getName())) {
                    parser.next();
                    newsEntry.setPubDate(parser.getText());
                } else {
                    parser.next();
                }
            } else {
                if(parser.getEventType() == XmlPullParser.END_TAG && "item".equals(parser.getName())) {
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
