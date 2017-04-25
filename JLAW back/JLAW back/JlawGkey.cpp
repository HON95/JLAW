#pragma comment(lib, "LogitechGkeyLib.lib")

#include <jni.h>
#include <string>
#include <LogitechGkeyLib.h>
#include "JlawUtil.h"
#include "JlawGkey.h"

namespace jlaw_gkey {
	const char *callbackMethodName = "call";
	const char *callbackMethodSignature = "(IZIZLjava/lang/String;)V";
	char *threadName = "logi-gkey-callback-thread";
	JavaVM *storedJvm = NULL;
	jmethodID callbackMethodID = NULL;
	jobject currentCallback = NULL;

	void __cdecl Callback(GkeyCode gkeyCode, wchar_t *gkeyOrButtonString, void *context) {
		JNIEnv *env;
		JavaVMAttachArgs args;
		args.version = JNI_VERSION_1_6; // JNI version
		args.name = threadName; // Thread name
		args.group = NULL; // Thread group
		storedJvm->AttachCurrentThread((void**)&env, &args);

		jmethodID methodId = env->GetMethodID(env->GetObjectClass(currentCallback), callbackMethodName, callbackMethodSignature);
		std::wstring wideString = gkeyOrButtonString;
		jstring javaString = ToJavaStringFromWideString(wideString, env);
		env->CallVoidMethod(currentCallback, callbackMethodID, gkeyCode.keyIdx, gkeyCode.keyDown, gkeyCode.mState, gkeyCode.mouse, javaString);
		storedJvm->DetachCurrentThread();
	}
}

using namespace jlaw_gkey;

/*
* Class:     ninja_hon95_jlaw_JlawGkey
* Method:    nLogiGkeyInit
* Signature: (Lninja/hon95/jlaw/JlawGkey/JlawGkeyCallback;)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyInit
(JNIEnv *env, jclass clazz, jobject callback) {
	if (storedJvm == NULL)
		env->GetJavaVM(&storedJvm);
	if (callbackMethodID == NULL)
		callbackMethodID = env->GetMethodID(env->GetObjectClass(callback), callbackMethodName, callbackMethodSignature);
	if (currentCallback != NULL)
		env->DeleteGlobalRef(currentCallback);
	currentCallback = env->NewGlobalRef(callback);
	return LogiGkeyInitWithoutContext((logiGkeyCB)Callback);
}

/*
* Class:     ninja_hon95_jlaw_JlawGkey
* Method:    nLogiGkeyInitWithoutCallback
* Signature: ()Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyInitWithoutCallback
(JNIEnv *env, jclass clazz) {
	return LogiGkeyInitWithoutCallback();
}

/*
* Class:     ninja_hon95_jlaw_JlawGkey
* Method:    nLogiGkeyIsMouseButtonPressed
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyIsMouseButtonPressed
(JNIEnv *env, jclass clazz, jint buttonNumber) {
	return LogiGkeyIsMouseButtonPressed(buttonNumber);
}

/*
* Class:     ninja_hon95_jlaw_JlawGkey
* Method:    nLogiGkeyGetMouseButtonString
* Signature: (I)Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyGetMouseButtonString
(JNIEnv *env, jclass clazz, jint buttonNumber) {
	std::wstring wideString = LogiGkeyGetMouseButtonString(buttonNumber);
	jstring javaString = ToJavaStringFromWideString(wideString, env);
	return javaString;
}

/*
* Class:     ninja_hon95_jlaw_JlawGkey
* Method:    nLogiGkeyIsKeyboardGkeyPressed
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyIsKeyboardGkeyPressed
(JNIEnv *env, jclass clazz, jint gkeyNumber, jint modeNumber) {
	return LogiGkeyIsKeyboardGkeyPressed(gkeyNumber, modeNumber);
}

/*
* Class:     ninja_hon95_jlaw_JlawGkey
* Method:    nLogiGkeyGetKeyboardGkeyString
* Signature: (II)Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyGetKeyboardGkeyString
(JNIEnv *env, jclass clazz, jint gkeyNumber, jint modeNumber) {
	std::wstring wideString = LogiGkeyGetKeyboardGkeyString(gkeyNumber, modeNumber);
	jstring javaString = ToJavaStringFromWideString(wideString, env);
	return javaString;
}

/*
* Class:     ninja_hon95_jlaw_JlawGkey
* Method:    nLogiGkeyShutdown
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyShutdown
(JNIEnv *env, jclass clazz) {
	LogiGkeyShutdown();
}
