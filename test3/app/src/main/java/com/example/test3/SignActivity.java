package com.example.test3;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
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
        inst = this;
        login_button = findViewById(R.id.login_button);
        cancel_button = findViewById(R.id.cancel_button);
        user_name = findViewById(R.id.user_name_edittext);
        context = this;
        setHandler();
        login();

        receive_thread();
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

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String name = user_name.getText().toString();
                        String ret = LinkHelper.sendName(name);
                        Message message = new Message();
                        String[] ret_list = ret.split("\n");

                        if(ret_list.length == 2 && ret_list[0].equals("NAME")) {
                            message.what = Date.SEND_NAME_SUCCESS;
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
                        Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case Date.CONNECT_FAILED:
                    case Date.SEND_NAME_FAILED:
                    case Date.RECEIVE_SUCCESS:
                    case Date.RECEIVE_FAILED:
                        break;
                    case Date.CANCEL_SUCCESS:
                    case Date.CANCEL_FAILED:
                        Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        exitAPP();
                        break;
                    case Date.SEND_NAME_SUCCESS:
                        Log.e("send_name_success", "ss");
                        Intent intent = new Intent(inst, MainActivity.class);
                        startActivity(intent);
                        inst.finish();
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

    void receive_thread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    String ret = LinkHelper.receive();
                    Log.e("receive", ret);
                    Message message = new Message();
                    String[] ret_list = ret.split("\n");
                    if(ret_list.length == 2 && ret_list[0].equals("MESSAGE")) {
                        message.what = Date.RECEIVE_SUCCESS;
                    }else {
                        message.what = Date.RECEIVE_FAILED;
                    }
                    message.obj = ret;
                    handler.sendMessage(message);
                }
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

