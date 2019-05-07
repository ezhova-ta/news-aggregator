package com.example.newsaggregator.model;

import com.example.newsaggregator.model.entity.RssChannel;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RssChannelListRepositoryImpl implements RssChannelListRepository {
    @Override
    public List<RssChannel> getRssChannelList() {
        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        final Callable<List<RssChannel>> rssChannelListLoader = new RssChannelListDBLoader();
        final Future<List<RssChannel>> future = executorService.submit(rssChannelListLoader);
        try {
            return future.get();
        } catch (final ExecutionException | InterruptedException e) {
            /*
            TODO Правильно обработать исключения
             */
            e.printStackTrace(System.err);
            return null;
        }
    }

    @Override
    public void addRssChannel(final String link) {
        final Thread rssChannelSaver = new Thread(new RssChannelSaver(link));
        rssChannelSaver.start();
    }
}
