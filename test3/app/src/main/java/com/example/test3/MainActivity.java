package com.example.test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    public EditText editText1, editText2;
    Button send_link1, send_message1, send_link2, send_message2;
    MyReceiver receiver = null;
    IntentFilter filter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        receiver = new MyReceiver();
        filter = new IntentFilter();
        filter.addAction("android.intent.action.MainActivity");
        registerReceiver(receiver, filter);
        Log.e("MainActivity.register", "注册");

        send_link1 = findViewById(R.id.send_link1);
        editText1 = findViewById(R.id.editText1);
        send_message1 = findViewById(R.id.send_message1);
        send_link2 = findViewById(R.id.send_link2);
        editText2 = findViewById(R.id.editText2);
        send_message2 = findViewById(R.id.send_message2);

        send_link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String ret = LinkHelper.linkTest1();
                        Log.d("s", ret);
                    }
                }).start();
            }
        });
        send_message1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String content = editText1.getText().toString();
                        String ret = LinkHelper.sendTest1(content);
                        Log.d("s", ret);
                    }
                }).start();
            }
        });
        send_link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String ret = LinkHelper.linkTest2();
                        Log.d("s", ret);
                    }
                }).start();
            }
        });
        send_message2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String content = editText2.getText().toString();
                        String ret = LinkHelper.sendTest2(content);
                        Log.d("s", ret);
                    }
                }).start();
            }
        });
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("MainActivity", "jieshou");
        }
    }

}
