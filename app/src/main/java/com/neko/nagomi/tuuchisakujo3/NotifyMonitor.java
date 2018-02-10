package com.neko.nagomi.tuuchisakujo3;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.text.SimpleDateFormat;

/**
 * Created by nagomi on 2017/06/18.
 */

public class NotifyMonitor extends NotificationListenerService {
	public static final String ACTION_CONTROL = "com.neko.nagomi.tuuchisakujo3";
	public MyBroadcastReceiver mReceiver;

	@Override
	public void onCreate() {
		super.onCreate();
		log("Call onCreate()");
//		IntentFilter filter = new IntentFilter();
//		filter.addAction(ACTION_CONTROL);
//		mReceiver = new MyBroadcastReceiver();
//		registerReceiver(mReceiver, filter);
//		mMonitorHandler.sendMessage(mMonitorHandler.obtainMessage(EVENT_UPDATE_CURRENT_NOS));
	}
	@Override
	public void onNotificationPosted(StatusBarNotification sbn) {
		super.onNotificationPosted(sbn);
		StatusBarNotification[] array = getActiveNotifications();
		for(int i=0; i < array.length; i++){
			removeTelHistory(array[i]);
//			showLog(array[i]);
		}
	}
	private void removeTelHistory( StatusBarNotification sbn ){
		int id = sbn.getId();
		if(id != 1)return;
		String key = sbn.getKey();
		cancelNotification(key);
	}
	private void showLog( StatusBarNotification sbn ){
		int id = sbn.getId();
		String name = sbn.getPackageName();
		long time = sbn.getPostTime();
		boolean clearable = sbn.isClearable();
		boolean playing = sbn.isOngoing();
		CharSequence text = sbn.getNotification().tickerText;
		String category = sbn.getNotification().category;
		String group = sbn.getNotification().getGroup();
		String toString = sbn.getNotification().toString();
		String key = sbn.getKey();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd日 hh時mm分ss秒");
		
		
		Log.i("NM4","id:" + id + " name:" + name + " date:" +sdf.format(time).toString());
		Log.i("NM4","isClearable:" + clearable + " isOngoing:" + playing + " tickerText:" +text);
		Log.i("NM4","category:" + category + " group:" + group + " key:" + key);
	}
	@Override
	public void onNotificationRemoved(StatusBarNotification sbn) {
		super.onNotificationRemoved(sbn);
		log("Call onNotificationRemoved");
	}

	@Override
	public void onListenerConnected() {
		super.onListenerConnected();
		log("Call onListenerConnected");
	}

	@Override
	public void onNotificationRankingUpdate(RankingMap rankingMap) {
		super.onNotificationRankingUpdate(rankingMap);
		log("Call onNotificationRankingUpdate");
	}

	@Override
	public void onListenerDisconnected() {
		super.onListenerDisconnected();
		log("Call onListenerDisconnected");
	}

	@Override
	public IBinder onBind(Intent intent) {
		log("Call onBind");
		return super.onBind(intent);
	}

	@Override
	public void onDestroy() {
		log("Call onDestroy");
		super.onDestroy();
	}
	public static void log(String text){
		Log.i("NM2", text);
	}
}

