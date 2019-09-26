#ifndef _JLAW_BACK_UTIL_H
#define _JLAW_BACK_UTIL_H

#include <jni.h>
#include <string>

jstring ToJavaStringFromWideString(std::wstring &wideString, JNIEnv *env);

std::wstring ToWideStringFromJavaString(jstring &javaString, JNIEnv *env);

#endif // _JLAW_BACK_UTIL_H
