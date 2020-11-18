package com.example.test3;

public class LinkHelper {
    static {
        System.loadLibrary("native-lib");
    }
    public native String linkTest1();
    public native String sendTest1(String content);
    public native String linkTest2();
    public native String sendTest2(String content);
    public native String close();
    public native String sendName(String name);
    public native String receive();
}
