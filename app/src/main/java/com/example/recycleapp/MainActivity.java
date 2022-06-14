package com.example.recycleapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
 //   BroadCastBattery broadCastBattery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((ProgressBar) findViewById(R.id.progressBar)).setActivated(true);
        Thread mSplashThread = new Thread() {
            @Override
            public void run() {

                synchronized (this) {
                    try {

                        wait(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                finish();
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        };
        mSplashThread.start();
        Animation animation=AnimationUtils.loadAnimation(this,R.anim.fade);
        ((ImageView)findViewById(R.id.ItemImg)).startAnimation(animation);
    }
    // BroadCast receiver - Battery
  /*  private class BroadCastBattery extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            int battery = intent.getIntExtra("level", 0);
            if (battery < 30)
            {
                Toast.makeText(MainActivity.this, "Your battery is low please charge your phone", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadCastBattery);
    }
*/
}
