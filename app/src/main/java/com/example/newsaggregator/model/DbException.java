package com.example.newsaggregator.model;

public class DbException extends Exception {
    private final String message;

    public DbException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
