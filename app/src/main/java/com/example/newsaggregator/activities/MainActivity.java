package com.example.newsaggregator.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.newsaggregator.R;
import com.example.newsaggregator.services.MainService;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final int resultResult = intent.getIntExtra("request_result", 0);
                if(resultResult == MainService.FETCHING_NEWS_RESULT_OK) {
                    /*
                    TODO Получить данные из БД и отобразить
                     */
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

    public void onUpdateNewsButtonClick(View view) {
        final Intent intent = new Intent(this, MainService.class);
        intent.setAction(MainService.ACTION_FETCH_NEWS);
        intent.putExtra(MainService.EXTRA_PARAM_CHANNEL_URL, "https://news.yandex.ru/travels.rss");
        startService(intent);
    }
}
