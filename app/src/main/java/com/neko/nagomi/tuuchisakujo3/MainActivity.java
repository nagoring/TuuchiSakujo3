package com.neko.nagomi.tuuchisakujo3;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
	private boolean isEnabled = false;
	private NotificationReceiver mNotifyReceiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		log("call MainActivity::onCreate");
		mNotifyReceiver = new NotificationReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(NotifyMonitor.ACTION_CONTROL);
		registerReceiver(mNotifyReceiver, filter);
	}

	public void clickButton(View view){
		switch(view.getId()){
			case R.id.createNotification:
				createNotification(this);
				break;
			case R.id.listNotification:
				log("----listNotification-------");
				Intent intent = new Intent(NotifyMonitor.ACTION_CONTROL);
				intent.putExtra("notification_msg", "Hello World!");
				sendBroadcast(intent);
				break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		log("call MainActivity::onResume");
		isEnabled = isEnabled();
		if(!isEnabled){
			openNotificationAccess();
		}
		//Emulator flat -> :com.google.android.apps.nexuslauncher/com.android.launcher3.notification.NotificationListener
		//flat : -> jp.co.punigram.notican/jp.co.punigram.notican.NotificationListener:com.coconuts.pastnotifications/com.coconuts.pastnotifications.ClsNotificationService:com.neko.nagomi.tuushikesu/com.neko.nagomi.tuushikesu.NotificationService

		log("isEnabled : " + isEnabled);

	}
	private boolean isEnabled() {
		String pkgName = getPackageName();
		final String flat = Settings.Secure.getString(getContentResolver(),
			"enabled_notification_listeners");
		if (!TextUtils.isEmpty(flat)) {
			final String[] names = flat.split(":");
			for (int i = 0; i < names.length; i++) {
				final ComponentName cn = ComponentName.unflattenFromString(names[i]);
				if (cn != null) {
					if (TextUtils.equals(pkgName, cn.getPackageName())) {
						return true;
					}
				}
			}
		}
		return false;
	}



	private void createNotification(Context context) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
		NotificationCompat.Builder ncBuilder = new NotificationCompat.Builder(context);
		ncBuilder.setContentTitle(sdf.format(date) + ":My Notification");

		ncBuilder.setContentText(sdf.format(date) + ":Notification Listener Service Example");
		ncBuilder.setTicker(sdf.format(date) + ":Notification Listener Service Example");
		ncBuilder.setSmallIcon(R.drawable.ic_launcher);
		ncBuilder.setAutoCancel(true);
		manager.notify((int)System.currentTimeMillis(),ncBuilder.build());
		log(sdf.format(date) + ":Notification Listener Service Example");
		
	}
	private void openNotificationAccess() {
		startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
	}

	public static void log(String text){
		Log.i("NM1", text);
	}
	
	class NotificationReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String msg = intent.getStringExtra("notification_msg") + "\n";
			log("onReceive :" + msg);
//			overlayView.addComment(msg);
//			overlayView.addStamp();
		}
	}
	
	
}
