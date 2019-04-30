package com.example.newsaggregator.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private static final String LOADING_DATA_ERROR = "Sorry! Loading data error occurred.";
    private BroadcastReceiver receiver;
    private NewsAdapter adapter;
    private RecyclerView recyclerView;
    private TextView loadingDataErrorTextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.newsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadingDataErrorTextView = findViewById(R.id.loadingDataError);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                final int requestResult = intent.getIntExtra("request_result", 0);
                if(requestResult == MainService.FETCHING_NEWS_RESULT_OK) {
                    final List<News> newsList = loadNewsFromDB();
                    if(newsList != null) {
                        adapter = new NewsAdapter(newsList);
                        recyclerView.setAdapter(adapter);
                    } else {
                        loadingDataErrorTextView.setText(LOADING_DATA_ERROR);
                    }
                } else if(requestResult == MainService.FETCHING_NEWS_RESULT_FAILING) {
                    loadingDataErrorTextView.setText(LOADING_DATA_ERROR);
                }
            }
        };

        final IntentFilter intentFilter = new IntentFilter(MainService.ACTION_FETCH_NEWS);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
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
        } catch (final ExecutionException | InterruptedException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    private static class NewsHolder extends RecyclerView.ViewHolder {
        private TextView newsTitleTextView;
        private TextView newsLinkTextView;
        private TextView newsDescriptionTextView;
        private TextView newsPubDateTextView;
        private News news;

        NewsHolder(@NonNull final View itemView) {
            super(itemView);
            newsTitleTextView = itemView.findViewById(R.id.newsTitle);
            newsLinkTextView = itemView.findViewById(R.id.newsLink);
            newsDescriptionTextView = itemView.findViewById(R.id.newsDescription);
            newsPubDateTextView = itemView.findViewById(R.id.newsPubDate);
        }

        public void bindView(final News news) {
            this.news = news;
            newsTitleTextView.setText(news.getTitle());
            newsLinkTextView.setText(news.getLink());
            newsDescriptionTextView.setText(news.getDescription());
            newsPubDateTextView.setText(news.getPubDate());
        }
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
        private List<News> newsList;

        NewsAdapter(final List<News> news) {
            newsList = news;
        }

        @NonNull
        @Override
        public NewsHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
            final LayoutInflater inflater = getLayoutInflater();
            final View view = inflater.inflate(R.layout.news_list_item_view, viewGroup, false);
            return new NewsHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final NewsHolder newsHolder, final int i) {
            final News news = newsList.get(i);
            newsHolder.bindView(news);
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }
    }
}
