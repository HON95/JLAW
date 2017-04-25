#pragma comment(lib, "LogitechLEDLib.lib")

#include <jni.h>
#include <string>
#include <LogitechLEDLib.h>
#include "JlawUtil.h"
#include "JlawLed.h"

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedInit
* Signature: ()Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedInit
(JNIEnv *env, jclass clazz){
	return LogiLedInit();
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedGetSdkVersion
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedGetSdkVersion
(JNIEnv *env, jclass clazz){
	int major, minor, build;
	if (LogiLedGetSdkVersion(&major, &minor, &build)) {
		std::wstring wideString;
		wideString += std::to_wstring(major);
		wideString += L".";
		wideString += std::to_wstring(minor);
		wideString += L".";
		wideString += std::to_wstring(build);
		jstring javaString = ToJavaStringFromWideString(wideString, env);
		return javaString;
	}
	return NULL;
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedSetTargetDevice
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetTargetDevice
(JNIEnv *env, jclass clazz, jint targetDevice){
	return LogiLedSetTargetDevice(targetDevice);
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedSaveCurrentLighting
* Signature: ()Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSaveCurrentLighting
(JNIEnv *env, jclass clazz){
	return LogiLedSaveCurrentLighting();
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedSetLighting
* Signature: (III)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetLighting
(JNIEnv *env, jclass clazz, jint redPercentage, jint greenPercentage, jint bluePercentage){
	return LogiLedSetLighting(redPercentage, greenPercentage, bluePercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedRestoreLighting
* Signature: ()Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedRestoreLighting
(JNIEnv *env, jclass clazz){
	return LogiLedRestoreLighting();
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedFlashLighting
* Signature: (IIIII)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedFlashLighting
(JNIEnv *env, jclass clazz, jint redPercentage, jint greenPercentage, jint bluePercentage, jint milliSecondsDuration, jint milliSecondsInterval){
	return LogiLedFlashLighting(redPercentage, greenPercentage, bluePercentage, milliSecondsDuration, milliSecondsInterval);
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedPulseLighting
* Signature: (IIIII)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedPulseLighting
(JNIEnv *env, jclass clazz, jint redPercentage, jint greenPercentage, jint bluePercentage, jint milliSecondsDuration, jint milliSecondsInterval){
	return LogiLedPulseLighting(redPercentage, greenPercentage, bluePercentage, milliSecondsDuration, milliSecondsInterval);
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedStopEffects
* Signature: ()Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedStopEffects
(JNIEnv *env, jclass clazz){
	return LogiLedStopEffects();
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedSetLightingFromBitmap
* Signature: ([B)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetLightingFromBitmap
(JNIEnv *env, jclass clazz, jbyteArray bitmap){
	jbyte *byteBitmap = env->GetByteArrayElements(bitmap, NULL);
	bool result = LogiLedSetLightingFromBitmap(reinterpret_cast<unsigned char *>(byteBitmap));
	env->ReleaseByteArrayElements(bitmap, byteBitmap, JNI_ABORT);
	return result;
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedSetLightingForKeyWithScanCode
* Signature: (IIII)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetLightingForKeyWithScanCode
(JNIEnv *env, jclass clazz, jint keyCode, jint redPercentage, jint greenPercentage, jint bluePercentage){
	return LogiLedSetLightingForKeyWithScanCode(keyCode, redPercentage, greenPercentage, bluePercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedSetLightingForKeyWithHidCode
* Signature: (IIII)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetLightingForKeyWithHidCode
(JNIEnv *env, jclass clazz, jint keyCode, jint redPercentage, jint greenPercentage, jint bluePercentage){
	return LogiLedSetLightingForKeyWithHidCode(keyCode, redPercentage, greenPercentage, bluePercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedSetLightingForKeyWithQuartzCode
* Signature: (IIII)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetLightingForKeyWithQuartzCode
(JNIEnv *env, jclass clazz, jint keyCode, jint redPercentage, jint greenPercentage, jint bluePercentage){
	return LogiLedSetLightingForKeyWithQuartzCode(keyCode, redPercentage, greenPercentage, bluePercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedSetLightingForKeyWithKeyName
* Signature: (IIII)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetLightingForKeyWithKeyName
(JNIEnv *env, jclass clazz, jint keyName, jint redPercentage, jint greenPercentage, jint bluePercentage){
	return LogiLedSetLightingForKeyWithKeyName(static_cast<LogiLed::KeyName>(keyName), redPercentage, greenPercentage, bluePercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawLed
* Method:    nLogiLedShutdown
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedShutdown
(JNIEnv *env, jclass clazz){
	LogiLedShutdown();
}
