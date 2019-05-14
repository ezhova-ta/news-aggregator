package com.example.newsaggregator.model;

public class DbException extends Exception {
    private final String message;
    private final StackTraceElement[] stackTrace;

    public DbException(final String message, final StackTraceElement[] stackTrace) {
        this.message = message;
        this.stackTrace = stackTrace;
    }
}
