package com.example.test3;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class mHandler extends Handler {

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case Date.CONNECT_SUCCESS:
                Log.e("connect_success", msg.obj.toString());
                break;
            case Date.CONNECT_FAILED:
            case Date.SEND_NAME_FAILED:
            case Date.RECEIVE_SUCCESS:
                Log.e("receiver_success", msg.obj.toString());
            case Date.RECEIVE_FAILED:
                break;
            case Date.CANCEL_SUCCESS:
            case Date.CANCEL_FAILED:
                Log.e("cancel_failed", msg.obj.toString());

                break;
            case Date.SEND_NAME_SUCCESS:
                Log.e("send_name_success", "ss");
        }
    }

}
