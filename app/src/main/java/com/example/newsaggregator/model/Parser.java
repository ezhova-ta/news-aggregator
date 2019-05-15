package com.example.newsaggregator.model;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public interface Parser<T> {
    T parse(final String url) throws XmlPullParserException, IOException;
}
