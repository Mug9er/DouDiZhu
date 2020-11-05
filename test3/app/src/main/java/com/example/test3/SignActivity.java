package com.example.test3;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
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

    Button login_button, cancel_button;
    Handler handler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sign_layout);

        init();



    }

    void init() {
        login_button = findViewById(R.id.login_button);
        cancel_button = findViewById(R.id.cancel_button);
        context = this;
        setHandler();
        login();

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String ret = LinkHelper.close();
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

    }

    @SuppressLint("HandlerLeak")
    void setHandler() {

        handler = new Handler(){
            @SuppressLint("ShowToast")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //获得刚才发送的Message对象，然后在这里进行UI操作
                switch (msg.what) {
                    case Date.CONNECT_SUCCESS:
                    case Date.CONNECT_FAILED:
                        Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case Date.CANCEL_SUCCESS:
                    case Date.CANCEL_FAILED:
                        Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        exitAPP();
                        break;
                }
            }
        };
    }

    void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ret = LinkHelper.linkTest1();
                Message message = new Message();
                if(ret.equals("连接失败")) {
                    message.what = Date.CONNECT_SUCCESS;
                }else {
                    message.what = Date.CONNECT_FAILED;
                }
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


}

