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

    strcpy(buf_write, tmp);

    env->ReleaseStringUTFChars(content, tmp);

    write(clientfd, buf_write, 8);

    bzero(buf_write, 1024);

   // read(clientfd1, buf1, sizeof(buf1));
    return env->NewStringUTF(buf_write);
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
Java_com_example_test3_LinkHelper_receive(JNIEnv *env, jobject clazz) {

    bzero(buf_read, 0);

    int ret = read(clientfd, buf_read, sizeof(buf_read));
    LOGI("link_demo.receive: %s", buf_read);
    if(ret < 0) {
        return env->NewStringUTF("receive error");
    }
    return env->NewStringUTF(buf_read);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_joinRoom(JNIEnv *env, jobject thiz, jstring id) {
    // TODO: implement joinRoom()

}extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_test3_LinkHelper_sendMessage(JNIEnv *env, jobject thiz, jstring type, jstring msg) {
    char * tmp1 = (char*) env->GetStringUTFChars(type, JNI_FALSE);
    char * tmp2 = (char*) env->GetStringUTFChars(msg, JNI_FALSE);

    sprintf(buf_write, "%s##%s", tmp1, tmp2);

    env->ReleaseStringUTFChars(type, tmp1);
    env->ReleaseStringUTFChars(msg, tmp2);

    int ret = write(clientfd, buf_write, sizeof(buf_write));

    if(ret < 0) {
        return env->NewStringUTF("send error");
    }
    return env->NewStringUTF(buf_write);
}