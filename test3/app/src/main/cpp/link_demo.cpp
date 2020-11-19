#include "link_demo.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_linkTest(JNIEnv *env, jobject clazz) {
    socklen_t serv_addr_len;

    clientfd = socket(AF_INET, SOCK_STREAM, 0);
    if(clientfd < 0) {
        return env->NewStringUTF("socket failed.");
    }
    LOGI("socket successful");
    memset(&serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(SERV_PORT);
    inet_pton(AF_INET, SERV_IP, &serv_addr.sin_addr.s_addr);
    LOGI("connecting!");
    int ret = connect(clientfd, (sockaddr *)&serv_addr, sizeof(serv_addr));
    if(ret < 0) {
        LOGI("connect failed.");
        return env->NewStringUTF("连接失败");
    }
    LOGI("connect successful");

    return env->NewStringUTF("连接成功");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_sendTest(JNIEnv *env, jobject clazz, jstring content) {
    char * tmp = (char*) env->GetStringUTFChars(content, JNI_FALSE);

    strcpy(buf1, tmp);

    env->ReleaseStringUTFChars(content, tmp);

    write(clientfd, buf1, 8);

    bzero(buf1, 1024);

   // read(clientfd1, buf1, sizeof(buf1));
    return env->NewStringUTF(buf1);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_close(JNIEnv *env, jobject clazz) {
    // TODO: implement close()
    int ret = close(clientfd);
    if(ret < 0) {
        return env->NewStringUTF("关闭失败");
    }
    return env->NewStringUTF("关闭成功");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_sendName(JNIEnv *env, jobject clazz, jstring name) {
    char * tmp = (char*) env->GetStringUTFChars(name, JNI_FALSE);

    sprintf(buf1, "NAME##%s", tmp);

    env->ReleaseStringUTFChars(name, tmp);

    int ret = write(clientfd, buf1, sizeof(buf1));

    if(ret < 0) {
        return env->NewStringUTF("send error");
    }
    return env->NewStringUTF(buf1);
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_receive(JNIEnv *env, jobject clazz) {
    bzero(buf1, 0);
    int ret = read(clientfd, buf1, sizeof(buf1));

    sprintf(buf2, "MESSAGE##%s", buf1);
    sprintf(buf1, "%s", buf2);

    if(ret < 0) {
        return env->NewStringUTF("receive error");
    }
    return env->NewStringUTF(buf1);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_joinRoom(JNIEnv *env, jobject thiz, jstring id) {
    // TODO: implement joinRoom()

}