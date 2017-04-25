#pragma once

#include <jni.h>
#include <string>

jstring ToJavaStringFromCharString(const char *charString, JNIEnv *env);

char* ToCharStringFromJavaString(jstring &javaString, JNIEnv *env);

jstring ToJavaStringFromWideString(std::wstring &wideString, JNIEnv *env);

std::wstring ToWideStringFromJavaString(jstring &javaString, JNIEnv *env);
