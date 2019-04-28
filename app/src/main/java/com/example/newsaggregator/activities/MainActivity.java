package com.example.newsaggregator.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.newsaggregator.R;
import com.example.newsaggregator.db.DBNewsLoader;
import com.example.newsaggregator.parser.News;
import com.example.newsaggregator.services.MainService;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    private TextView newsListView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsListView = findViewById(R.id.newsList);

        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                final int resultResult = intent.getIntExtra("request_result", 0);
                if(resultResult == MainService.FETCHING_NEWS_RESULT_OK) {
                    /*
                    TODO Получить данные из БД и отобразить
                     */
                    final List<News> newsList = loadNewsFromDB();
                    if(newsList != null) {
                        /*
                        TODO Реализовать вывод новостей в RecyclerView
                         */
                        newsListView.setText("");
                        for(final News news : newsList) {
                            newsListView.append(news.toString());
                        }
                    } else {
                        /*
                        TODO Сообщить пользователю о проблеме (данные не загружены из сети)
                         */
                    }
                } else if(resultResult == MainService.FETCHING_NEWS_RESULT_FAILING) {
                    /*
                    TODO Сообщить пользователю о проблеме (данные не загружены из сети)
                     */
                }
            }
        };

        final IntentFilter intentFilter = new IntentFilter(MainService.ACTION_FETCH_NEWS);
        registerReceiver(receiver, intentFilter);
    }

    public void onUpdateNewsButtonClick(final View view) {
        final Intent intent = new Intent(this, MainService.class);
        intent.setAction(MainService.ACTION_FETCH_NEWS);
        intent.putExtra(MainService.EXTRA_PARAM_CHANNEL_URL, "https://news.yandex.ru/travels.rss");
        startService(intent);
    }

    private List<News> loadNewsFromDB() {
        final ExecutorService executor = Executors.newFixedThreadPool(3);
        final Callable<List<News>> dbNewsLoader =
                new DBNewsLoader(MainActivity.this, "https://news.yandex.ru/travels.rss");
        final Future<List<News>> future = executor.submit(dbNewsLoader);
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
