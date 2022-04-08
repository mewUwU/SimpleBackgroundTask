package com.example.simplebackgroundtask;
//package com.example.android.simplebackgroundtask;

//import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private Handler mHandler;
    private ProgressBar progressBar;
    private ProgressBar progressBar2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView1);
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        mHandler = new Handler();
        button = findViewById(R.id.button);
        //Chạy tren Main Thread, chay tren UI Thread
    }

//    public void startTask(View view) {
//        mTextView.setText("Loading...");
//        //Chạy tren Main Thread, chay tren UI Thread
//        progressBar.setVisibility(View.VISIBLE);
//        progressBar2.setVisibility(View.VISIBLE);
//
//        new Thread(() -> {
//            //Chạy tren BackGround Thread, chay tren Worker Thread
//            int n = new Random().nextInt(11);
//            int s = 3000 +  n * 400;   //1 5s   5000  = 100*50
//            try {
//                for(int i = 0; i<= 100;i++){
//                    Thread.sleep(s/100);
//                    int finalI = i;
//                    mHandler.post(() -> {
//                        progressBar2.setProgress(finalI);
//                    });
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            mHandler.post(() -> {
//                progressBar.setVisibility(View.INVISIBLE);
//                progressBar2.setVisibility(View.INVISIBLE);
//                mTextView.setText("Awake at last after sleeping for " + s + " milliseconds!");
//            });
//        }).start();
//
//
//    }

    public void startTask(View view) {
        mTextView.setText("Loading...");
        progressBar.setVisibility(View.VISIBLE);
        button.setVisibility(View.INVISIBLE);
        new Thread(() -> {
            try {
                URL url = new URL("https://gorest.co.in/public/v2/users");
                HttpsURLConnection httpsConnection = (HttpsURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(httpsConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                mHandler.post(() -> {
                    button.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mTextView.setText(response);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}