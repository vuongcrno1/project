package com.example.user.demotablayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Switch;
import android.widget.TextView;

import com.example.user.demotablayout.R;
import com.example.user.demotablayout.model.ThoiGian;

import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private List<ThoiGian> ListthoiGian;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<ThoiGian> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.ListthoiGian = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_thoigian,parent,false);
                viewHolder = new ViewHolder();

                viewHolder.tvid = (TextView)convertView.findViewById(R.id.tvid);
                viewHolder.tvtime = (TextView)convertView.findViewById(R.id.tvtime);
                viewHolder.tvtenbaothuc = (TextView)convertView.findViewById(R.id.tvtenbaothuc);
                viewHolder.sw1 = (Switch)convertView.findViewById(R.id.sw1);

                convertView.setTag(viewHolder);


            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            ThoiGian thoiGian = ListthoiGian.get(position);

            viewHolder.tvid.setText(String.valueOf(thoiGian.getmID()));
            viewHolder.tvtime.setText(String.valueOf(thoiGian.getMgio())+":"+thoiGian.getMphut());
            viewHolder.tvtenbaothuc.setText(String.valueOf(thoiGian.getMtenbaothuc()));


        return convertView;
    }

    public class ViewHolder {
            private TextView tvid;
            private TextView tvtime;
            private TextView tvtenbaothuc;
            private Switch sw1;
    }
}
