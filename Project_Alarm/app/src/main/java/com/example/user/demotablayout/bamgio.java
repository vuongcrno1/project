package com.example.user.demotablayout;

import android.os.CountDownTimer;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class bamgio extends Fragment {

    Button btnset;
    Button btnstart;
    Button btnreset;
    EditText edittextinput;
    TextView textviewtg;
    private CountDownTimer mCountDownTimer;
    Calendar calendar;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_bamgio, container, false);

        btnset = view.findViewById(R.id.btnset);
        btnstart = view.findViewById(R.id.btnstart);
        btnreset = view.findViewById(R.id.btnreset);
        edittextinput = view.findViewById(R.id.edittextinput);
        textviewtg = view.findViewById(R.id.textviewtg);
        calendar = Calendar.getInstance();


        btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = edittextinput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(getActivity(), "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(getActivity(), "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                calendar.setTimeInMillis(millisInput);
                edittextinput.setText("");


            }
        });
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    bamgio.super.onPause();
                } else {
                    bamgio.super.onStart();
                }
            }
        });

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return view;
    }
}
