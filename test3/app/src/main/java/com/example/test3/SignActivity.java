package com.example.test3;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class SignActivity extends AppCompatActivity{

    public SignActivity inst = null;
    Button login_button, cancel_button;
    EditText user_name;
    Date.mHandler handler = null;
    LinkHelper linkHelper = null;
    Date mDate = null;
    IntentFilter filter = null;
    MyReceiver receiver = null;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sign_layout);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    void init() {
        receiver = new MyReceiver();
        filter = new IntentFilter();
        filter.addAction("android.intent.action.SignActivity");
        registerReceiver(receiver, filter);
        Log.e("register", "注册");

        inst = this;
        mDate = (Date)getApplication();
        handler = mDate. new mHandler();
        linkHelper = new LinkHelper();
        mDate.setLinkHelper(linkHelper);
        mDate.setMHandler(handler);
        login_button = findViewById(R.id.login_button);
        cancel_button = findViewById(R.id.cancel_button);
        user_name = findViewById(R.id.user_name_edittext);
        context = this;
        login();

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String ret = linkHelper.close();
                        Message message = new Message();
                        if(ret.equals("关闭成功")) {
                            message.what = Date.CANCEL_SUCCESS;
                        }else {
                            message.what = Date.CANCEL_FAILED;
                        }
                        message.obj = ret;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String name = user_name.getText().toString();
                        String ret = linkHelper.sendMessage("NAME", name);
                        Message message = new Message();
                        String[] ret_list = ret.split("##");

                        if(ret_list.length == 2 && ret_list[0].equals("NAME")) {
                            message.what = Date.SEND_NAME_SUCCESS;
                            mDate.name = name;
                        }else {
                            message.what = Date.SEND_NAME_FAILED;
                        }
                        message.obj = ret;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ret = linkHelper.linkTest();
                Message message = new Message();
                if(ret.equals("连接失败")) {
                    message.what = Date.CONNECT_FAILED;
                }else {
                    message.what = Date.CONNECT_SUCCESS;
                }
                Log.e("SignActivity.login()", "连接成功");
                message.obj = ret;
                handler.sendMessage(message);
            }
        }).start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void exitAPP() {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();
        for (ActivityManager.AppTask appTask : appTaskList) {
            appTask.finishAndRemoveTask();
        }
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int value = intent.getIntExtra("VALUE", 0);
            Log.e("value", ""+value);
            switch (value) {
                case Date.CONNECT_SUCCESS:
                    Log.e("SignActivity.MyReceiver.onReceive", "Connect_Success");
                    Toast.makeText(getApplicationContext(), "Connect_Success", Toast.LENGTH_SHORT).show();
                    mDate.receive_thread();
                    break;
                case Date.CONNECT_FAILED:
                    Log.e("SignActivity.MyReceiver.onReceive", "Connect_failed");
                    Toast.makeText(getApplicationContext(), "Connect_Failed", Toast.LENGTH_SHORT).show();
                    break;
                case Date.SEND_NAME_SUCCESS:
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.setClass(inst, CreateRoomActivity.class);
                    startActivity(intent);
                    inst.finish();
                    break;
                case Date.SEND_NAME_FAILED:
                    Toast.makeText(getApplicationContext(), "名字已存在", Toast.LENGTH_SHORT).show();

            }
        }

    }



}

