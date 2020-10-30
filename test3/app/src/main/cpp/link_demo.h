#ifndef 连接服务器TEST3_LINK_DEMO_H
#define 连接服务器TEST3_LINK_DEMO_H

#include <jni.h>
#include <string>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <ctype.h>
#include <android/log.h>

#define  LOG_TAG    "mysocket"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

#define SERV_IP "123.56.252.111"
#define SERV_PORT 6666

char buf1[1024], buf2[1024];
int clientfd1, clientfd2;
struct sockaddr_in serv_addr1, serv_addr2;

#endif //连接服务器TEST3_LINK_DEMO_H
