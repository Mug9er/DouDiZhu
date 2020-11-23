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
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

public class CreateRoomActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    EditText room_id;
    Button join_room, create_room;
    TextView textView_nameid;
    Intent intent = null;
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
        filter.addAction("android.intent.action.CreateRoomActivity");
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
                       Log.e("CreateRoomActivity.join_room", edit_room_id);
                       String ret = mLinkHelper.sendMessage("JoinRoom", edit_room_id);
                       Log.e("CreateRoomActivity.join_room", ret);
                   }
               }).start();

           }
       });

       create_room.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               intent = new Intent();
               intent.setClass(inst, ReadyRoomActivity.class);
               intent.putExtra("PLAYER_TYPE", "master");
               Bundle bundle = new Bundle();
               bundle.putString("ROOM_ID", mDate.id);
               bundle.putString("MASTER_NAME", mDate.name);
               bundle.putString("MASTER_ID", mDate.id);
               bundle.putString("PLAYER1_NAME", "等待加入");
               bundle.putString("PLAYER1_ID", "0");
               bundle.putString("PLAYER2_NAME", "等待加入");
               bundle.putString("PLAYER2_ID", "0");
               intent.putExtras(bundle);
               startActivity(intent);
               inst.finish();
           }
       });
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getIntExtra("TYPE", 0);
            String value = intent.getStringExtra("VALUE");
            switch(type){
                case Date.JOIN_ROOM_FAILED:
                    Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                    break;
                case Date.JOIN_ROOM_SUCCESS:
                    Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                    intent.setClass(inst, ReadyRoomActivity.class);
                    intent.putExtra("PLAYER_TYPE", "player");
                    startActivity(intent);
                    inst.finish();
                    break;
            }
        }
    }

}
