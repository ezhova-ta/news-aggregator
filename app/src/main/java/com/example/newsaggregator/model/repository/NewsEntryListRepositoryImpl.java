package com.example.newsaggregator.model.repository;

import com.example.newsaggregator.model.NewsEntryListDBLoader;
import com.example.newsaggregator.model.entity.NewsEntry;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NewsEntryListRepositoryImpl implements NewsEntryListRepository {
    @Override
    public List<NewsEntry> getNewsEntryList(final String channelLink) {
        /*
        TODO Получить актуальный список новостей из сети и понять, что с ним делать :)
         */
        try {
            return getNewsEntryListFromDB(channelLink);
        } catch (final ExecutionException | InterruptedException e) {
            e.printStackTrace(System.err);
            return null;
            /*
            TODO Правильно обработать исключения
             */
        }
    }

    private List<NewsEntry> getNewsEntryListFromDB(final String channelLink) throws
            ExecutionException, InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        final Callable<List<NewsEntry>> newsEntryListDBLoader = new NewsEntryListDBLoader(channelLink);
        final Future<List<NewsEntry>> future = executorService.submit(newsEntryListDBLoader);
        return future.get();
    }
}
