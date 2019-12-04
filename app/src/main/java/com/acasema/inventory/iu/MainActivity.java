package com.acasema.inventory.iu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.acasema.inventory.R;

public class MainActivity extends AppCompatActivity {

    private  static final long WAIT_TIME=1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //este objeto runnable ejecutas el codigo run fera del hilo de la IU
    @Override
    protected void onStart() {
        super.onStart();
        /*Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(WAIT_TIME);
                    initLogin();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();*/
        Handler handler =new Handler();
        Runnable runnable = new Runnable()
        {
            @Override
            public void run() {
                initLogin();
            }
        };
        handler.postDelayed(runnable, WAIT_TIME);

    }

    private void initLogin()
    {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
