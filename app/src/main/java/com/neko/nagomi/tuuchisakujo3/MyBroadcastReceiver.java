package com.neko.nagomi.tuuchisakujo3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nagomi on 2017/06/18.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        log("call onReceive MyBloardcastReceiver");
        Bundle bundle = intent.getExtras();
        String message = bundle.getString("message");
        Toast.makeText(context, "onReceive!" + message, Toast.LENGTH_LONG).show();
    }
    public static void log(String text){
        Log.i("NM3", text);
    }
    
}
