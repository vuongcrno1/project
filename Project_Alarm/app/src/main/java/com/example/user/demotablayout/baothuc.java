package com.example.user.demotablayout;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user.demotablayout.adapter.CustomAdapter;
import com.example.user.demotablayout.data.DBManager;
import com.example.user.demotablayout.model.ThoiGian;

import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class baothuc extends Fragment {
    Button btndatgio;
    Button btndunglai;
    TimePicker timepicker;
    TextView textView1;
    EditText editText1;
    Calendar calendar;
    ListView viewtime;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    private Ringtone ringtone;
    private DBManager dbManager;
    private CustomAdapter customAdapter;
    private List<ThoiGian> thoigianlist;
    Button btnchonnhac;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_baothuc, container, false);


        btndatgio = view.findViewById(R.id.btndatgio);
        btndunglai = view.findViewById(R.id.btndunglai);
        timepicker = view.findViewById(R.id.timepicker);
        textView1 = view.findViewById(R.id.textView1);
        calendar = Calendar.getInstance();
        viewtime = view.findViewById(R.id.viewtime);
        editText1 = view.findViewById(R.id.editText1);
        btnchonnhac = view.findViewById(R.id.btnchonnhac);


        dbManager = new DBManager(getContext());

        thoigianlist = dbManager.getAllThoiGian();
        setAdapter();

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        // Cho phép truy cập vào hệ thống báo động của máy
        final Intent intent = new Intent(getContext(),AlarmReceiver.class);
        // Truyền dữ liệu từ Main sang AlarmReceiver
        timepicker.setIs24HourView(true);

        btndatgio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY,timepicker.getCurrentHour()); // set kiểu giờ
                calendar.set(Calendar.MINUTE,timepicker.getCurrentMinute());    // set kiểu phút


                int gio = timepicker.getCurrentHour(); // Lấy giờ từ timepicker
                int phut = timepicker.getCurrentMinute(); // Lấy phút từ timepicker

                String string_gio = String.valueOf(gio);        // Đổi thành String
                String string_phut = String.valueOf(phut);      // Đổi thành String


                long timeInMillis = calendar.getTimeInMillis();
                if(timeInMillis-System.currentTimeMillis()<0){
                    //Nếu thời gian chọn trước tg hiện tại thì + 1 day
                    timeInMillis += 86400000;
                }

                if(phut < 10) {

                    string_phut = "0" + String.valueOf(phut);
                }

                intent.putExtra("extra","on");

                pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                // Tồn tại khi thoát ứng dụng
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);

                textView1.setText("BÁO THỨC LÚC " + string_gio + ":"+ string_phut);

                ThoiGian thoiGian = createThoiGian();
                if (thoiGian != null){
                    dbManager.addThoiGian(thoiGian);
                }
                thoigianlist.clear();
                thoigianlist.addAll(dbManager.getAllThoiGian());
                setAdapter();

            }
        });

        btndunglai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView1.setText("DỪNG LẠI");
                intent.putExtra("extra","off");
                getActivity().sendBroadcast(intent);
            }
        });
        viewtime.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ThoiGian thoiGian = thoigianlist.get(position);
                int result = dbManager.deletetg(thoiGian.getmID());
                if (result>0){
                    Toast.makeText(getContext(),"Xóa thành công", Toast.LENGTH_SHORT).show();
                    updatetg();

                } else {
                    Toast.makeText(getContext(),"Xóa thất bại", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });
        btnchonnhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);

                startActivityForResult(intent , 1);
            }
        });

        return view;


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    ringtone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                    ringtone = RingtoneManager.getRingtone(getActivity(),alert);

                    break;

                default:
                    break;
            }
        }
    }
    private ThoiGian createThoiGian(){

        String gio = timepicker.getCurrentHour().toString();
        String phut = timepicker.getCurrentMinute().toString();
        String tenbaothuc = editText1.getText().toString();

        ThoiGian thoiGian = new ThoiGian(gio,phut,tenbaothuc);
        return thoiGian;
    }
    private void setAdapter(){
        if (customAdapter==null){
            customAdapter = new CustomAdapter(getContext(),R.layout.item_listview_thoigian,thoigianlist);
            viewtime.setAdapter(customAdapter);
        } else {
            customAdapter.notifyDataSetChanged();
            viewtime.setSelection(customAdapter.getCount()-1);
        }


    }
    public void updatetg(){
        thoigianlist.clear();
        thoigianlist.addAll(dbManager.getAllThoiGian());
        customAdapter.notifyDataSetChanged();
    }



}
