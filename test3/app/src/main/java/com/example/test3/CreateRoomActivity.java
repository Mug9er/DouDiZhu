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

public class CreateRoomActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    EditText room_id;
    Button join_room, create_room;
    TextView textView_nameid;
    MyReceiver receiver = null;
    IntentFilter filter = null;
    LinkHelper mLinkHelper = null;
    Date mDate = null;
    Date.mHandler mHandler = null;
    CreateRoomActivity inst = null;

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

        textView_nameid = findViewById(R.id.create_room_name_id);
        room_id = findViewById(R.id.room_id);
        join_room = findViewById(R.id.join_room);
        create_room = findViewById(R.id.create_room);

        inst = this;
        textView_nameid.setText(mDate.name + "  #" + mDate.id);
    }

    void setOnClicked() {
       join_room.setOnClickListener(new View.OnClickListener() {
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

       create_room.setOnClickListener(new View.OnClickListener() {
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
