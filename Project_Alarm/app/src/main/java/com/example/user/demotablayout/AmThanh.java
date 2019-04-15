package com.example.user.demotablayout;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

public class AmThanh extends Service {

    MediaPlayer mediaPlayer;
    int id;

    private NotificationCompat.Builder notBuilder;
    private static final int MY_NOTIFICATION_ID = 12345;
    private static final int MY_REQUEST_CODE = 100;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.notBuilder = new NotificationCompat.Builder(this);
        this.notBuilder.setAutoCancel(true); // Thông báo sẽ hủy khi click vào Panel


        String nhankey = intent.getExtras().getString("extra");

        if (nhankey.equals("on")){
            id=1;
        } else if (nhankey.equals("off")){
            id=0;
        }
        if (id == 1){
            mediaPlayer = MediaPlayer.create(this,R.raw.chayngaydi);
            mediaPlayer.start();
            id=0;
            shownoti();
        }
        else if (id == 0){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        return START_NOT_STICKY;

    }

    public void shownoti(){

        this.notBuilder.setSmallIcon(R.mipmap.alarm);

        // Sét đặt thời điểm sự kiện xẩy ra.
        // Các thông báo trên Panel được sắp xếp bởi thời gian này.
        this.notBuilder.setWhen(System.currentTimeMillis()+ 10* 1000);
        this.notBuilder.setContentTitle("BÁO THỨC");
        this.notBuilder.setContentText("ĐẾN GIỜ ĐI NGỦ RỒI");

        Intent intent = new Intent(this, MainActivity.class);

        // PendingIntent.getActivity(..) sẽ start mới một Activity và trả về
        // đối tượng PendingIntent.
        // Nó cũng tương đương với gọi Context.startActivity(Intent).
        PendingIntent pendingIntent = PendingIntent.getActivity(this, MY_REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);


        this.notBuilder.setContentIntent(pendingIntent);

        // Lấy ra dịch vụ thông báo (Một dịch vụ có sẵn của hệ thống).
        NotificationManager notificationService  =
                (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        // Xây dựng thông báo và gửi nó lên hệ thống.

        Notification notification =  notBuilder.build();
        notificationService.notify(MY_NOTIFICATION_ID, notification);

    }


}
