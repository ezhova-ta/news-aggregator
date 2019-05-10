package com.example.newsaggregator.presenter;

public class VoidAsyncTaskResult {
    private Exception exception;

    public VoidAsyncTaskResult() {}

    public VoidAsyncTaskResult(final Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
