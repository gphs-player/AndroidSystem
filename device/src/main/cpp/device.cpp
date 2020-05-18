//
// Created by 李华 on 2020/5/14.
//

#include <jni.h>
#include <android/log.h>

#include <string.h>
#include <stdio.h>

#include "device.h"

static int DEBUG = 1;
#define  LOG_TAG    "RootCheck"
#define  LOGD(...)  if (DEBUG) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__);
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__);

int exists(const char *fName) {
    FILE *file;
    if ((file = fopen(fName, "r"))) {
        LOGD("su程序查找: %s 存在!!!", fName);
        fclose(file);
        return 1;
    }
    LOGD("su程序查找: %s 不存在!!!", fName);
    return 0;
}

extern "C" {


JNIEXPORT jint JNICALL Java_com_leo_device_root_RootNative_checkForRoot
        (JNIEnv *env, jobject obj, jobjectArray args) {

    int isBinaryFound = 0;
    int stringCount = env->GetArrayLength(args);
    for (int index = 0; index < stringCount; ++index) {
        jstring str = (jstring) (env->GetObjectArrayElement(args, index));
        const char *path = env->GetStringUTFChars(str, 0);
        isBinaryFound += exists(path);
    }

    return isBinaryFound > 0;
}

JNIEXPORT void JNICALL Java_com_leo_device_root_RootNative_setLogDebugMessages
        (JNIEnv *env, jobject obj, jboolean debug) {
    DEBUG = debug ? 1 : 0;
}

}

