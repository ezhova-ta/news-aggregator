package com.example.newsaggregator.presenter;

public class AsyncTaskResult<T> {
    private T result;
    private Exception exception;

    public AsyncTaskResult(final T result) {
        this.result = result;
    }

    public AsyncTaskResult(final Exception exception) {
        this.exception = exception;
    }

    public T getResult() {
        return result;
    }

    public Exception getException() {
        return exception;
    }
}
