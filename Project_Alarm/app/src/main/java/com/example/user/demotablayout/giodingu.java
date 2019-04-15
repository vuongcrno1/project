package com.example.user.demotablayout;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class giodingu extends Fragment {

    Button btnstart;
    Button btnstop;
    Button btnhuongdan;
    TimePicker timepicker1;
    TextView textview3;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Calendar calendar1;
    private Ringtone ringtone;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.activity_giodingu, container, false);



        btnstart = view.findViewById(R.id.btnstart);
        btnstop = view.findViewById(R.id.btnstop);
        btnhuongdan = view.findViewById(R.id.btnhuongdan);
        timepicker1 = view.findViewById(R.id.timepicker1);
        textview3 = view.findViewById(R.id.textview3);
        calendar1 = Calendar.getInstance();

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        // Cho phép truy cập vào hệ thống báo động của máy
        final Intent intent = new Intent(getContext(),AlarmReceiver.class);
        // Truyền dữ liệu từ Main sang AlarmReceiver
        timepicker1.setIs24HourView(true);

        btnhuongdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });


        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar1.set(Calendar.HOUR_OF_DAY,timepicker1.getCurrentHour()-7); // set kiểu giờ
                calendar1.set(Calendar.MINUTE,timepicker1.getCurrentMinute());    // set kiểu phút

                int gio = timepicker1.getCurrentHour(); // Lấy giờ từ timepicker
                int phut = timepicker1.getCurrentMinute(); // Lấy phút từ timepicker

                gio = gio - 7;

                if (gio < 0 ) {
                    gio = gio + 24;
                }

                String string_gio = String.valueOf(gio);        // Đổi thành String
                String string_phut = String.valueOf(phut);      // Đổi thành String

                long timeInMillis = calendar1.getTimeInMillis();
                if(timeInMillis-System.currentTimeMillis()<0){
                    //Nếu thời gian chọn trước tg hiện tại thì + 1 day
                    timeInMillis += 86400000;
                }

                if(phut < 10) {

                    string_phut = "0" + String.valueOf(phut);
                }


                intent.putExtra("extra","on");
                ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();

                pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                // Tồn tại khi thoát ứng dụng
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);

                textview3.setText("TÍNH NĂNG SẼ BÁO ĐI NGỦ LÚC " + string_gio + ":"+ string_phut);



            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview3.setText("DỪNG LẠI");
                intent.putExtra("extra","off");

                getActivity().sendBroadcast(intent);
            }
        });

        return view;

    }
    public void showAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("THÔNG BÁO");
        builder.setMessage("THEO NGHIÊN CỨU CỦA CÁC NHÀ KHOA HỌC, THÌ BẠN PHẢI NGỦ 7 TIẾNG ĐỂ ĐẢM BẢO TỐT SỨC KHỎE CỦA MÌNH." +
                " CHO NÊN KHI BẠN CHỌN GIỜ MUỐN THỨC DẬY VÀ NHẤT START THÌ TÍNH NĂNG SẼ BÁO THỨC TRƯỚC LÚC THỨC DẬY 7 TIẾNG. ");

        builder.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    public void showAlertDialog2(){
        calendar1.set(Calendar.HOUR_OF_DAY,timepicker1.getCurrentHour()-7); // set kiểu giờ
        calendar1.set(Calendar.MINUTE,timepicker1.getCurrentMinute());    // set kiểu phút

        int gio = timepicker1.getCurrentHour(); // Lấy giờ từ timepicker
        int phut = timepicker1.getCurrentMinute(); // Lấy phút từ timepicker

        gio = gio - 7;
        if (gio < 0 ) {
            gio = gio + 24;
        }


        String string_gio = String.valueOf(gio);        // Đổi thành String
        String string_phut = String.valueOf(phut);      // Đổi thành String
        if(phut < 10) {

            string_phut = "0" + String.valueOf(phut);
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("KHUYẾN NGHỊ");
        builder.setMessage("SAU KHI BẠN CHỌN GIỜ MUỐN THỨC DẬY. TÍNH NĂNG SẼ BÁO THỨC CHO BẠN ĐI NGỦ TRƯỚC 7 TIẾNG ");

        builder.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


}
