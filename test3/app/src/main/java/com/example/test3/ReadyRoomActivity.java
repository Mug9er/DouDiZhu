package com.example.test3;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

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
    Intent intent = null;
    Date mDate = null;
    Date.mHandler mHandler = null;
    ReadyRoomActivity inst = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ready_room);
        init();
        setOnClicked();
        set();

    }

    void init() {
        mDate = (Date)getApplication();
        mHandler = mDate.getMHandler();
        mLinkHelper = mDate.getLinkHelper();

        receiver = new MyReceiver();
        filter = new IntentFilter();
        filter.addAction("android.intent.action.ReadyRoomActivity");
        registerReceiver(receiver, filter);
        Log.e("ReadyRoom.register", "注册");

        room_id = findViewById(R.id.ready_room_id);
        master_play = findViewById(R.id.room_master_play);
        player_ready = findViewById(R.id.player_ready);
        room_master_id = findViewById(R.id.room_master_id);
        play1_id = findViewById(R.id.play1_id);
        play2_id = findViewById(R.id.play2_id);

        inst = this;
        intent = getIntent();
    }

    @SuppressLint("SetTextI18n")
    void set() {
        room_id.setText("房间号： " + mDate.id);
        room_master_id.setText(mDate.name + " #" + mDate.id);
        String msg = intent.getStringExtra("PLAYER_TYPE");
        Log.e("ReadyRoomActivity.set", msg);
        if(msg.equals("master")) player_ready.setVisibility(View.INVISIBLE);
        else master_play.setVisibility(View.INVISIBLE);
        Bundle bundle = intent.getExtras();
        room_id.setText("房间号： " + bundle.getString("ROOM_ID"));
        room_master_id.setText(bundle.getString("MASTER_NAME") + "  #" + bundle.getString("MASTER_ID"));
        Log.e("ReadyRoomActivity.set", bundle.getString("PLAYER1_NAME") == null ? "NULL" : bundle.getString("PLAYER1_NAME"));
        if(bundle.getString("PLAYER1_NAME") != null) {
            play1_id.setText(bundle.getString("PLAYER1_NAME") + "  #" + bundle.getString("PLAYER1_ID"));
        }
        if(bundle.getString("PLAYER2_NAME") != null) {
            play2_id.setText(bundle.getString("PLAYER2_NAME") + "  #" + bundle.getString("PLAYER2_ID"));
        }
    }

    void setOnClicked() {
        master_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

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
            int type = intent.getIntExtra("TYPE", 0);
            String value = intent.getStringExtra("VALUE");
            switch(type){
                case Date.PLAYER_JOIN:
                    Log.e("ReadyRoomActivity.onReceive", "update");
                    Bundle bundle = intent.getExtras();
                    room_id.setText("房间号： " + bundle.getString("ROOM_ID"));
                    room_master_id.setText(bundle.getString("MASTER_NAME") + "  #" + bundle.getString("MASTER_ID"));
                    Log.e("ReadyRoomActivity.set", bundle.getString("PLAYER1_NAME") == null ? "NULL" : bundle.getString("PLAYER1_NAME"));
                    if(bundle.getString("PLAYER1_NAME") != null) {
                        play1_id.setText(bundle.getString("PLAYER1_NAME") + "  #" + bundle.getString("PLAYER1_ID"));
                    }else play1_id.setText("");
                    if(bundle.getString("PLAYER2_NAME") != null) {
                        play2_id.setText(bundle.getString("PLAYER2_NAME") + "  #" + bundle.getString("PLAYER2_ID"));
                    }else play2_id.setText("");

                    break;
            }
        }
    }

}
