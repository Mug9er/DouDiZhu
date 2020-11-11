package com.example.test3;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.logging.Handler;

public class Date extends Application {
    public static final int CONNECT_SUCCESS = 1; // 连接成功

    public static final int CONNECT_FAILED = 2; //连接失败

    public static final int CANCEL_SUCCESS = 3; // 断开连接成功

    public static final int CANCEL_FAILED = 4; //断开连接失败

    public static final int RECEIVE_SUCCESS = 5; //接收信息成功

    public static final int RECEIVE_FAILED = 6; // 接收信息失败

    public static final int SEND_NAME_SUCCESS = 7; //发送NAME成功

    public static final int SEND_NAME_FAILED = 8; // 发送NAME失败

    private mHandler mhandler = null;

    public void setMHandler(mHandler handler) {
        mhandler = handler;
    }

    public mHandler getMHandler() {
        return mhandler;
    }

}

