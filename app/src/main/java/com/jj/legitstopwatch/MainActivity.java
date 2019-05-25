package com.jj.legitstopwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "StopwatchMainActivity";
    boolean active = false;
    double time = 0;
    int overhang = 10;
    int seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((SeekBar) findViewById(R.id.seekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                seekbar=progress;
                overhang = seekbar * 10;
                calculatetime(time,overhang);
            }
        });
    }

    public void buttonclick(View view) {
        if(active) {
            active = false;
            Log.i(TAG, "buttonclick: new state: deactivated");
            return;
        }
        active = true;
        Log.i(TAG, "buttonclick: new state: activated");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(active) {
                    //time in seconds
                    time = time + 0.01;
                    calculatetime(time, overhang);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    void calculatetime(double time, int overhang) {
        //inputs: double time in seconds
        //        int overhang in seconds
        if (overhang>60) overhang = 60;
        int h = (int) Math.floor(time / 60 / 60);
        int m = (int) Math.floor(time / 60) - (h*60);
        int s = (int) Math.floor(time) - (m*60) - (h*60*60);
        int ms = (int) ((time - (h*60*60) - (m*60) - s) * 100);
        //final String timetext2 = String.format();
        final String timetext2 = String.format("%02d", h)+":"+String.format("%02d", m)+":"+String.format("%02d", s)+"."+String.format("%02d", ms);
        runOnUiThread(new Runnable() {
            public void run() {
                ((TextView)findViewById(R.id.timeView2)).setText(timetext2);
            }
        });
        if (h>0 & m<overhang) {
            m = m+60;
            if(h>=60) h--;
        }
        if (m>0 & s<overhang) {
            s = s+60;
            if(s>=60) m--;
        }
        //final String timetext = h+":"+m+":"+s+"."+ms;
        final String timetext = String.format("%02d", h)+":"+String.format("%02d", m)+":"+String.format("%02d", s)+"."+String.format("%02d", ms);
        runOnUiThread(new Runnable() {
            public void run() {
                ((TextView)findViewById(R.id.timeView)).setText(timetext);
            }
        });
    }

}
