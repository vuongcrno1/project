package com.example.user.demotablayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlarmReceiver extends BroadcastReceiver {      // Nhận intent từ hệ thống
    @Override
    public void onReceive(Context context, Intent intent) {
        String chuoi_string = intent.getStringExtra("extra");
        Intent myIntent = new Intent(context,AmThanh.class);
        myIntent.putExtra("extra",chuoi_string);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(myIntent);
        }
        else {
            context.startService(myIntent);
        }
    }
}
