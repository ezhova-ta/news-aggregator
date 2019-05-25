package com.example.newsaggregator.view.channel.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdatingNewsEntryListsAlarmReceiver extends BroadcastReceiver {
    private static AlarmReceiverListener listener;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        notifyListener();
    }

    public static void subscribeOnAlarmReceiverInvocation(final AlarmReceiverListener listener) {
        UpdatingNewsEntryListsAlarmReceiver.listener = listener;
    }

    public static void unsubscribeOnAlarmReceiverInvocation(final AlarmReceiverListener listener) {
        UpdatingNewsEntryListsAlarmReceiver.listener = null;
    }

    private static void notifyListener() {
        listener.onAlarmReceiverInvoked();
    }
}
