package com.example.test3;

public class LinkHelper {
    static {
        System.loadLibrary("native-lib");
    }
    public static native String linkTest1();
    public static native String sendTest1(String content);
    public static native String linkTest2();
    public static native String sendTest2(String content);
}
