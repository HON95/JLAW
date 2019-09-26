#pragma comment(lib, "LogitechLEDLib.lib")

#include <jni.h>
#include <string>
#include <LogitechLEDLib.h>
#include "JlawUtil.h"
#include "JlawLed.h"

// logiLedInit //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedInit
(JNIEnv *env, jclass clazz){
	return LogiLedInit();
}

// logiLedGetSdkVersion //
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

// logiLedSetTargetDevice //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetTargetDevice
(JNIEnv *env, jclass clazz, jint targetDevice){
	return LogiLedSetTargetDevice(targetDevice);
}

// logiLedSaveCurrentLighting //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSaveCurrentLighting
(JNIEnv *env, jclass clazz){
	return LogiLedSaveCurrentLighting();
}

// logiLedSetLighting //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetLighting
(JNIEnv *env, jclass clazz, jint redPercentage, jint greenPercentage, jint bluePercentage){
	return LogiLedSetLighting(redPercentage, greenPercentage, bluePercentage);
}

// logiLedRestoreLighting
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedRestoreLighting
(JNIEnv *env, jclass clazz){
	return LogiLedRestoreLighting();
}

// logiLedFlashLighting //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedFlashLighting
(JNIEnv *env, jclass clazz, jint redPercentage, jint greenPercentage, jint bluePercentage, jint milliSecondsDuration, jint milliSecondsInterval){
	return LogiLedFlashLighting(redPercentage, greenPercentage, bluePercentage, milliSecondsDuration, milliSecondsInterval);
}

// logiLedPulseLighting //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedPulseLighting
(JNIEnv *env, jclass clazz, jint redPercentage, jint greenPercentage, jint bluePercentage, jint milliSecondsDuration, jint milliSecondsInterval){
	return LogiLedPulseLighting(redPercentage, greenPercentage, bluePercentage, milliSecondsDuration, milliSecondsInterval);
}

// logiLedStopEffects //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedStopEffects
(JNIEnv *env, jclass clazz){
	return LogiLedStopEffects();
}

// logiLedSetLightingFromBitmap //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetLightingFromBitmap
(JNIEnv *env, jclass clazz, jbyteArray bitmap){
	jbyte *byteBitmap = env->GetByteArrayElements(bitmap, NULL);
	bool result = LogiLedSetLightingFromBitmap(reinterpret_cast<BYTE*>(byteBitmap));
	env->ReleaseByteArrayElements(bitmap, byteBitmap, JNI_ABORT);
	return result;
}

// logiLedSetLightingForKeyWithScanCode //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetLightingForKeyWithScanCode
(JNIEnv *env, jclass clazz, jint keyCode, jint redPercentage, jint greenPercentage, jint bluePercentage){
	return LogiLedSetLightingForKeyWithScanCode(keyCode, redPercentage, greenPercentage, bluePercentage);
}

// logiLedSetLightingForKeyWithHidCode //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetLightingForKeyWithHidCode
(JNIEnv *env, jclass clazz, jint keyCode, jint redPercentage, jint greenPercentage, jint bluePercentage){
	return LogiLedSetLightingForKeyWithHidCode(keyCode, redPercentage, greenPercentage, bluePercentage);
}

// logiLedSetLightingForKeyWithQuartzCode //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetLightingForKeyWithQuartzCode
(JNIEnv *env, jclass clazz, jint keyCode, jint redPercentage, jint greenPercentage, jint bluePercentage){
	return LogiLedSetLightingForKeyWithQuartzCode(keyCode, redPercentage, greenPercentage, bluePercentage);
}

// logiLedSetLightingForKeyWithKeyName //
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedSetLightingForKeyWithKeyName
(JNIEnv *env, jclass clazz, jint keyName, jint redPercentage, jint greenPercentage, jint bluePercentage){
	return LogiLedSetLightingForKeyWithKeyName(static_cast<LogiLed::KeyName>(keyName), redPercentage, greenPercentage, bluePercentage);
}

// logiLedShutdown //
JNIEXPORT void JNICALL Java_ninja_hon95_jlaw_JlawLed_nLogiLedShutdown
(JNIEnv *env, jclass clazz){
	LogiLedShutdown();
}
