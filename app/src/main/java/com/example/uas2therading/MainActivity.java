package com.example.uas2therading;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Button button1;
    public TextView text1;
    public TextView text2;
    int downloadPercent = 0;
    double randAds;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        text1 = findViewById(R.id.text1);
        button1.setOnClickListener(this);
    }

    public void startThread (){
        showAds ads = new showAds();
        new Thread(ads).start();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button1) {
            button1.setEnabled(false);
            calculateSubDownload download = new calculateSubDownload();
            startThread();
            download.start();
        }
    }

    //with extends Thread
    //Thread showing download percentage
    class calculateSubDownload extends Thread {
        TextView text2 = findViewById(R.id.text2);

        @Override
        public void run() {
            for (int i = 0; i <= 10; ++i) {
                downloadPercent = i;
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                handler.post(new Runnable() {
                    public void run() {
                        text2.setText("Downloading Percent : " + (downloadPercent * 10) + "%");
                        if(downloadPercent == 10) {
                            text2.setText("Download finished!");
                            button1.setEnabled(true);
                        }
                    }
                });
            }
        }
    }

    //with implements Runnable
    //Thread showing randomized Ad
    class showAds implements Runnable{
        TextView text1 = findViewById(R.id.text1);

        Handler handler = new Handler();
        @Override
        public void run() {
            for (int i = 0; i<=10;++i){
                handler.post(new Runnable() {
                    public void run() {
                        randAds = Math.random()*10.0;
                        text1.setText("Showing Ad : " +(int)randAds +" of 10");
                    }
                });

                try{
                    Thread.sleep(2000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
