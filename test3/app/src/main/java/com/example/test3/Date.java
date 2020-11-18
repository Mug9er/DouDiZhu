package com.example.test3;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class Date extends Application {

    public String  name = null;

    public static final int CONNECT_SUCCESS = 1; // 连接成功

    public static final int CONNECT_FAILED = 2; //连接失败

    public static final int CANCEL_SUCCESS = 3; // 断开连接成功

    public static final int CANCEL_FAILED = 4; //断开连接失败

    public static final int RECEIVE_SUCCESS = 5; //接收信息成功

    public static final int RECEIVE_FAILED = 6; // 接收信息失败

    public static final int SEND_NAME_SUCCESS = 7; //发送NAME成功

    public static final int SEND_NAME_FAILED = 8; // 发送NAME失败

    private mHandler mhandler = null;

    private LinkHelper mLinkHelper = null;

    public void setMHandler(mHandler handler) {
        mhandler = handler;
    }

    public mHandler getMHandler() {
        return mhandler;
    }

    public void setLinkHelper(LinkHelper linkHelper) {
        mLinkHelper = linkHelper;
    }

    public LinkHelper getLinkHelper() {
        return mLinkHelper;
    }


    public class mHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = null;
            switch (msg.what) {
                case Date.CONNECT_SUCCESS:
                    Log.e("SignActivity.mHander.connect_success", msg.obj.toString());
                    intent = new Intent();
                    intent.setAction("android.intent.action.test");
                    intent.putExtra("VALUE", Date.CONNECT_SUCCESS);
                    sendBroadcast(intent);
                    break;
                case Date.CONNECT_FAILED:
                    intent = new Intent();
                    intent.setAction("android.intent.action.test");
                    intent.putExtra("VALUE", Date.CONNECT_FAILED);
                    sendBroadcast(intent);
                    break;
                case Date.SEND_NAME_FAILED:
                    intent = new Intent();
                    intent.setAction("android.intent.action.test");
                    intent.putExtra("VALUE", Date.SEND_NAME_FAILED);
                    sendBroadcast(intent);
                case Date.RECEIVE_SUCCESS:
                    Log.e("SignActivity.mHander.receiver_success", msg.obj.toString());

                case Date.RECEIVE_FAILED:
                    break;
                case Date.CANCEL_SUCCESS:
                case Date.CANCEL_FAILED:
                    Log.e("SignActivity.mHander.cancel_failed", msg.obj.toString());
                    break;
                case Date.SEND_NAME_SUCCESS:
                    Log.e("SignActivity.mHander.send_name_success", "ss");

                    intent = new Intent();
                    intent.setAction("android.intent.action.test");
                    intent.putExtra("VALUE", Date.SEND_NAME_SUCCESS);
                    sendBroadcast(intent);
                    break;
            }
        }

    }


}

