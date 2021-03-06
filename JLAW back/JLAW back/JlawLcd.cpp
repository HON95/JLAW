#pragma comment(lib, "LogitechLCDLib.lib")

#include <jni.h>
#include <string>
#include <LogitechLCDLib.h>
#include "JlawUtil.h"
#include "JlawLcd.h"


// logiLcdInit //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLcd_nLogiLcdInit
(JNIEnv *env, jclass clazz, jstring friendlyName, jint lcdType) {
	std::wstring wideString = ToWideStringFromJavaString(friendlyName, env);
	return LogiLcdInit(&wideString[0], lcdType);
}

// logiLcdIsConnected //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLcd_nLogiLcdIsConnected
(JNIEnv *env, jclass clazz, jint lcdType) {
	return LogiLcdIsConnected(lcdType);
}

// logiLcdIsButtonPressed //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLcd_nLogiLcdIsButtonPressed
(JNIEnv *env, jclass clazz, jint button) {
	return LogiLcdIsButtonPressed(button);
}

// logiLcdUpdate //
JNIEXPORT void JNICALL Java_ninja_hon95_jlaw_JlawLcd_nLogiLcdUpdate
(JNIEnv *env, jclass clazz) {
	LogiLcdUpdate();
}

// logiLcdShutdown //
JNIEXPORT void JNICALL Java_ninja_hon95_jlaw_JlawLcd_nLogiLcdShutdown
(JNIEnv *env, jclass clazz) {
	LogiLcdShutdown();
}

// logiLcdMonoSetBackground //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLcd_nLogiLcdMonoSetBackground
(JNIEnv *env, jclass clazz, jbyteArray monoBitmap) {
	jbyte *byteMonoBitmap = env->GetByteArrayElements(monoBitmap, NULL);
	bool result = LogiLcdMonoSetBackground(reinterpret_cast<BYTE*>(byteMonoBitmap));
	env->ReleaseByteArrayElements(monoBitmap, byteMonoBitmap, JNI_ABORT);
	return result;
}

// logiLcdMonoSetText //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLcd_nLogiLcdMonoSetText
(JNIEnv *env, jclass clazz, jint lineNumber, jstring text) {
	std::wstring wideString = ToWideStringFromJavaString(text, env);
	return LogiLcdMonoSetText(lineNumber, &wideString[0]);
}

// logiLcdColorSetBackground //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLcd_nLogiLcdColorSetBackground
(JNIEnv *env, jclass clazz, jbyteArray colorBitmap) {
	jbyte *byteColorBitmap = env->GetByteArrayElements(colorBitmap, NULL);
	bool result = LogiLcdColorSetBackground(reinterpret_cast<BYTE*>(byteColorBitmap));
	env->ReleaseByteArrayElements(colorBitmap, byteColorBitmap, JNI_ABORT);
	return result;
}

// logiLcdColorSetTitle //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLcd_nLogiLcdColorSetTitle
(JNIEnv *env, jclass clazz, jstring text, jint red, jint green, jint blue) {
	std::wstring wideString = ToWideStringFromJavaString(text, env);
	return LogiLcdColorSetTitle(&wideString[0], red, green, blue);
}

// logiLcdColorSetText //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLcd_nLogiLcdColorSetText
(JNIEnv *env, jclass clazz, jint lineNumber, jstring text, jint red, jint green, jint blue) {
	std::wstring wideString = ToWideStringFromJavaString(text, env);
	return LogiLcdColorSetText(lineNumber, &wideString[0], red, green, blue);
}
