package com.example.test3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReadyRoomActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    Button master_play, player_ready;
    TextView room_master_id, play1_id, play2_id, room_id;
    MyReceiver receiver = null;
    IntentFilter filter = null;
    LinkHelper mLinkHelper = null;
    Date mDate = null;
    Date.mHandler mHandler = null;
    ReadyRoomActivity inst = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.create_room);
        init();
        setOnClicked();

    }

    void init() {
        mDate = (Date)getApplication();
        mHandler = mDate.getMHandler();
        mLinkHelper = mDate.getLinkHelper();

        receiver = new MyReceiver();
        filter = new IntentFilter();
        filter.addAction("android.intent.action.CreateRoom");
        registerReceiver(receiver, filter);
        Log.e("CreateRoom.register", "注册");

        room_id = findViewById(R.id.room_id);
        master_play = findViewById(R.id.room_master_play);
        player_ready = findViewById(R.id.player_ready);
        room_master_id = findViewById(R.id.room_master_id);
        play1_id = findViewById(R.id.play1_id);
        play2_id = findViewById(R.id.play2_id);

        inst = this;
    }

    void setOnClicked() {
        master_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String edit_room_id = room_id.getText().toString();
                        String ret = mLinkHelper.joinRoom(edit_room_id);

                    }
                }).start();

            }
        });

        player_ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
