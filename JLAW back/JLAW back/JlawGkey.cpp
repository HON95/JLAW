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

// logiGkeyInit //
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

// logiGkeyInitWithoutCallback //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyInitWithoutCallback
(JNIEnv *env, jclass clazz) {
	return LogiGkeyInitWithoutCallback();
}

// logiGkeyIsMouseButtonPressed //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyIsMouseButtonPressed
(JNIEnv *env, jclass clazz, jint buttonNumber) {
	return LogiGkeyIsMouseButtonPressed(buttonNumber);
}

// logiGkeyGetMouseButtonString //
JNIEXPORT jstring JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyGetMouseButtonString
(JNIEnv *env, jclass clazz, jint buttonNumber) {
	std::wstring wideString = LogiGkeyGetMouseButtonString(buttonNumber);
	jstring javaString = ToJavaStringFromWideString(wideString, env);
	return javaString;
}

// logiGkeyIsKeyboardGkeyPressed //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyIsKeyboardGkeyPressed
(JNIEnv *env, jclass clazz, jint gkeyNumber, jint modeNumber) {
	return LogiGkeyIsKeyboardGkeyPressed(gkeyNumber, modeNumber);
}

// logiGkeyGetKeyboardGkeyString //
JNIEXPORT jstring JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyGetKeyboardGkeyString
(JNIEnv *env, jclass clazz, jint gkeyNumber, jint modeNumber) {
	std::wstring wideString = LogiGkeyGetKeyboardGkeyString(gkeyNumber, modeNumber);
	jstring javaString = ToJavaStringFromWideString(wideString, env);
	return javaString;
}

// logiGkeyShutdown //
JNIEXPORT void JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyShutdown
(JNIEnv *env, jclass clazz) {
	LogiGkeyShutdown();
}
