#include "link_demo.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_linkTest1(JNIEnv *env, jclass clazz) {
    socklen_t serv_addr_len;

    clientfd1 = socket(AF_INET, SOCK_STREAM, 0);
    if(clientfd1 < 0) {
        return env->NewStringUTF("socket failed.");
    }
    LOGI("socket successful");
    memset(&serv_addr1, 0, sizeof(serv_addr1));
    serv_addr1.sin_family = AF_INET;
    serv_addr1.sin_port = htons(SERV_PORT);
    inet_pton(AF_INET, SERV_IP, &serv_addr1.sin_addr.s_addr);
    LOGI("connecting!");
    int ret = connect(clientfd1, (sockaddr *)&serv_addr1, sizeof(serv_addr1));
    if(ret < 0) {
        LOGI("connect failed.");
        return env->NewStringUTF("连接失败");
    }
    LOGI("connect successful");

    return env->NewStringUTF("连接成功");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_sendTest1(JNIEnv *env, jclass clazz, jstring content) {
    char * tmp = (char*) env->GetStringUTFChars(content, JNI_FALSE);

    strcpy(buf1, tmp);

    env->ReleaseStringUTFChars(content, tmp);

    write(clientfd1, buf1, 8);

    bzero(buf1, 1024);

   // read(clientfd1, buf1, sizeof(buf1));
    return env->NewStringUTF(buf1);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_linkTest2(JNIEnv *env, jclass clazz) {
    socklen_t serv_addr_len;

    clientfd2 = socket(AF_INET, SOCK_STREAM, 0);
    if(clientfd2 < 0) {
        return env->NewStringUTF("socket failed.");
    }
    LOGI("socket successful");
    memset(&serv_addr2, 0, sizeof(serv_addr2));
    serv_addr2.sin_family = AF_INET;
    serv_addr2.sin_port = htons(SERV_PORT);
    inet_pton(AF_INET, SERV_IP, &serv_addr2.sin_addr.s_addr);
    LOGI("connecting!");
    int ret = connect(clientfd2, (sockaddr *)&serv_addr2, sizeof(serv_addr2));
    if(ret < 0) {
        LOGI("connect failed.");
        return env->NewStringUTF("连接失败");
    }
    LOGI("connect successful");

    return env->NewStringUTF(buf2);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_sendTest2(JNIEnv *env, jclass clazz, jstring content) {
    char * tmp = (char*) env->GetStringUTFChars(content, JNI_FALSE);

    strcpy(buf2, tmp);

    sprintf(buf2, "%s", tmp);

    env->ReleaseStringUTFChars(content, tmp);

  //  sprintf(buf2,  "%s,%d", buf2, sizeof(buf2));

    write(clientfd2, buf2, sizeof(buf2));

    bzero(buf2, 1024);

    read(clientfd2, buf2, sizeof(buf2));

    return env->NewStringUTF(buf2);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_close(JNIEnv *env, jclass clazz) {
    // TODO: implement close()
    int ret = close(clientfd1);
    if(ret < 0) {
        return env->NewStringUTF("关闭失败");
    }
    return env->NewStringUTF("关闭成功");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_sendName(JNIEnv *env, jclass clazz, jstring name) {
    char * tmp = (char*) env->GetStringUTFChars(name, JNI_FALSE);

    sprintf(buf1, "NAME\n%s", tmp);

    env->ReleaseStringUTFChars(name, tmp);

    int ret = write(clientfd1, buf1, sizeof(buf1));

    if(ret < 0) {
        return env->NewStringUTF("send error");
    }
    return env->NewStringUTF(buf1);
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_receive(JNIEnv *env, jclass clazz) {
    int ret = read(clientfd1, buf1, sizeof(buf1));

    sprintf(buf2, "MESSAGE##%s", buf1);
    sprintf(buf1, "%s", buf2);

    if(ret < 0) {
        return env->NewStringUTF("receive error");
    }
    return env->NewStringUTF(buf1);
}