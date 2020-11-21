package com.example.test3;

public class LinkHelper {
    static {
        System.loadLibrary("native-lib");
    }
    public native String linkTest();
    public native String sendTest(String content);
    public native String close();
    public native String receive();
    public native String joinRoom(String id);
    public native String sendMessage(String type, String msg);
}
