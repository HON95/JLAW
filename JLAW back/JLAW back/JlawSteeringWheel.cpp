#pragma comment(lib, "LogitechSteeringWheelLib.lib")

#include <jni.h>
#include <string>
#include <LogitechSteeringWheelLib.h>
#include "JlawUtil.h"
#include "JlawSteeringWheel.h"

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiSteeringInitializeWithWindow
* Signature: (ZLjava/lang/String;)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiSteeringInitializeWithWindow
(JNIEnv *env, jclass clazz, jboolean ignoreXInputControllers, jstring windowTitle) {
	std::wstring wideWindowTitle = ToWideStringFromJavaString(windowTitle, env);
	HWND hwnd = FindWindow(NULL, wideWindowTitle.c_str());
	if (hwnd == NULL) {
		printf("Error: Window not found.\n");
		return false;
	}
	return LogiSteeringInitializeWithWindow(ignoreXInputControllers != 0, hwnd);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiSteeringInitialize
* Signature: (Z)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiSteeringInitialize
(JNIEnv *env, jclass clazz, jboolean ignoreXInputControllers) {
	return LogiSteeringInitialize(ignoreXInputControllers != 0);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiSteeringGetSdkVersion
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiSteeringGetSdkVersion
(JNIEnv *env, jclass clazz) {
	int major, minor, build;
	if (LogiSteeringGetSdkVersion(&major, &minor, &build)) {
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
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiUpdate
* Signature: ()Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiUpdate
(JNIEnv *env, jclass clazz) {
	return LogiUpdate();
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiGetState
* Signature: (I)Lninja/hon95/jlaw/JlawSteeringWheel/DIJoystick2;
*/
JNIEXPORT jobject JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiGetState
(JNIEnv *env, jclass clazz, jint index) {
	DIJOYSTATE2 *states = LogiGetState(index);

	jclass containerClass = env->FindClass("ninja/hon95/jlaw/JlawSteeringWheel/DIJoystick2");
	if (containerClass == NULL) {
		printf("Error: Class not found.\n");
		return NULL;
	}
	jmethodID constructor = env->GetMethodID(containerClass, "<init>", "()V");
	if (constructor == NULL) {
		printf("Error: Constructor not found.\n");
		return NULL;
	}
	jobject container = env->NewObject(containerClass, constructor);

	env->SetIntField(container, env->GetFieldID(containerClass, "positionX", "I"), states->lX);
	env->SetIntField(container, env->GetFieldID(containerClass, "positionY", "I"), states->lY);
	env->SetIntField(container, env->GetFieldID(containerClass, "positionZ", "I"), states->lZ);
	env->SetIntField(container, env->GetFieldID(containerClass, "rotationX", "I"), states->lRx);
	env->SetIntField(container, env->GetFieldID(containerClass, "rotationY", "I"), states->lRy);
	env->SetIntField(container, env->GetFieldID(containerClass, "rotationZ", "I"), states->lRz);
	{
		LONG *cArray = states->rglSlider;
		int arrayLength = sizeof(cArray) / sizeof(cArray[0]);
		jintArray jArray = env->NewIntArray(arrayLength);
		env->SetIntArrayRegion(jArray, 0, arrayLength, cArray);
		env->SetObjectField(container, env->GetFieldID(containerClass, "positionExtras", "[I"), jArray);
	}
	{
		DWORD *dArray = states->rgdwPOV;
		int arrayLength = sizeof(dArray) / sizeof(dArray[0]);
		LONG *cArray = new LONG[arrayLength];
		for (int i = 0; i < arrayLength; i++) {
			cArray[i] = dArray[i];
		}
		jintArray jArray = env->NewIntArray(arrayLength);
		env->SetIntArrayRegion(jArray, 0, arrayLength, cArray);
		env->SetObjectField(container, env->GetFieldID(containerClass, "povDirections", "[I"), jArray);
	}
	{
		BYTE *cArray = states->rgbButtons;
		int arrayLength = sizeof(cArray) / sizeof(cArray[0]);
		BYTE *bArray = new BYTE[arrayLength];
		for (int i = 0; i < arrayLength; i++) {
			bArray[i] = cArray[i] != 0;
		}
		jbooleanArray jArray = env->NewBooleanArray(arrayLength);
		env->SetBooleanArrayRegion(jArray, 0, arrayLength, bArray);
		env->SetObjectField(container, env->GetFieldID(containerClass, "buttonStates", "[Z"), jArray);
	}
	env->SetIntField(container, env->GetFieldID(containerClass, "velocityX", "I"), states->lVX);
	env->SetIntField(container, env->GetFieldID(containerClass, "velocityY", "I"), states->lVY);
	env->SetIntField(container, env->GetFieldID(containerClass, "velocityZ", "I"), states->lVZ);
	env->SetIntField(container, env->GetFieldID(containerClass, "angularVelocityX", "I"), states->lVRx);
	env->SetIntField(container, env->GetFieldID(containerClass, "angularVelocityY", "I"), states->lVRy);
	env->SetIntField(container, env->GetFieldID(containerClass, "angularVelocityZ", "I"), states->lVRz);
	{
		LONG *cArray = states->rglVSlider;
		int arrayLength = sizeof(cArray) / sizeof(cArray[0]);
		jintArray jArray = env->NewIntArray(arrayLength);
		env->SetIntArrayRegion(jArray, 0, arrayLength, cArray);
		env->SetObjectField(container, env->GetFieldID(containerClass, "velocityExtras", "[I"), jArray);
	}
	env->SetIntField(container, env->GetFieldID(containerClass, "accelerationX", "I"), states->lAX);
	env->SetIntField(container, env->GetFieldID(containerClass, "accelerationY", "I"), states->lAY);
	env->SetIntField(container, env->GetFieldID(containerClass, "accelerationZ", "I"), states->lAZ);
	env->SetIntField(container, env->GetFieldID(containerClass, "angularAccelerationX", "I"), states->lARx);
	env->SetIntField(container, env->GetFieldID(containerClass, "angularAccelerationY", "I"), states->lARy);
	env->SetIntField(container, env->GetFieldID(containerClass, "angularAccelerationZ", "I"), states->lARz);
	{
		LONG *cArray = states->rglASlider;
		int arrayLength = sizeof(cArray) / sizeof(cArray[0]);
		jintArray jArray = env->NewIntArray(arrayLength);
		env->SetIntArrayRegion(jArray, 0, arrayLength, cArray);
		env->SetObjectField(container, env->GetFieldID(containerClass, "accelerationExtras", "[I"), jArray);
	}
	env->SetIntField(container, env->GetFieldID(containerClass, "forceX", "I"), states->lFX);
	env->SetIntField(container, env->GetFieldID(containerClass, "forceY", "I"), states->lFY);
	env->SetIntField(container, env->GetFieldID(containerClass, "forceZ", "I"), states->lFZ);
	env->SetIntField(container, env->GetFieldID(containerClass, "torqueX", "I"), states->lFRx);
	env->SetIntField(container, env->GetFieldID(containerClass, "torqueY", "I"), states->lFRy);
	env->SetIntField(container, env->GetFieldID(containerClass, "torqueZ", "I"), states->lFRz);
	{
		LONG *cArray = states->rglFSlider;
		int arrayLength = sizeof(cArray) / sizeof(cArray[0]);
		jintArray jArray = env->NewIntArray(arrayLength);
		env->SetIntArrayRegion(jArray, 0, arrayLength, cArray);
		env->SetObjectField(container, env->GetFieldID(containerClass, "forceExtras", "[I"), jArray);
	}

	return container;
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiGetDevicePath
* Signature: (I)Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiGetDevicePath
(JNIEnv *env, jclass clazz, jint index) {
	const int bufferLength = 256;
	wchar_t *wideString = new wchar_t[bufferLength];
	LogiGetDevicePath(index, wideString, bufferLength);
	jstring javaString = ToJavaStringFromWideString(std::wstring(wideString), env);
	delete wideString;
	return javaString;
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiGetFriendlyProductName
* Signature: (I)Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiGetFriendlyProductName
(JNIEnv *env, jclass clazz, jint index) {
	const int bufferLength = 256;
	wchar_t *wideString = new wchar_t[bufferLength];
	LogiGetFriendlyProductName(index, wideString, bufferLength);
	jstring javaString = ToJavaStringFromWideString(std::wstring(wideString), env);
	delete wideString;
	return javaString;
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiIsConnected
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiIsConnected
(JNIEnv *env, jclass clazz, jint index) {
	return LogiIsConnected(index);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiIsDeviceConnected
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiIsDeviceConnected
(JNIEnv *env, jclass clazz, jint index, jint deviceType) {
	return LogiIsDeviceConnected(index, deviceType);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiIsManufacturerConnected
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiIsManufacturerConnected
(JNIEnv *env, jclass clazz, jint index, jint manufacturerName) {
	return LogiIsManufacturerConnected(index, manufacturerName);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiIsModelConnected
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiIsModelConnected
(JNIEnv *env, jclass clazz, jint index, jint modelName) {
	return LogiIsModelConnected(index, modelName);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiButtonTriggered
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiButtonTriggered
(JNIEnv *env, jclass clazz, jint index, jint buttonNumber) {
	return LogiButtonTriggered(index, buttonNumber);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiButtonReleased
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiButtonReleased
(JNIEnv *env, jclass clazz, jint index, jint buttonNumber) {
	return LogiButtonReleased(index, buttonNumber);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiButtonIsPressed
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiButtonIsPressed
(JNIEnv *env, jclass clazz, jint index, jint buttonNumber) {
	return LogiButtonIsPressed(index, buttonNumber);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiGenerateNonLinearValues
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiGenerateNonLinearValues
(JNIEnv *env, jclass clazz, jint index, jint nonLinCoeff) {
	return LogiGenerateNonLinearValues(index, nonLinCoeff);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiGetNonLinearValue
* Signature: (II)I
*/
JNIEXPORT jint JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiGetNonLinearValue
(JNIEnv *env, jclass clazz, jint index, jint inputValue) {
	return LogiGetNonLinearValue(index, inputValue);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiHasForceFeedback
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiHasForceFeedback
(JNIEnv *env, jclass clazz, jint index) {
	return LogiHasForceFeedback(index);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiIsPlaying
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiIsPlaying
(JNIEnv *env, jclass clazz, jint index, jint forceType) {
	return LogiIsPlaying(index, forceType);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiPlaySpringForce
* Signature: (IIII)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiPlaySpringForce
(JNIEnv *env, jclass clazz, jint index, jint offsetPercentage, jint saturationPercentage, jint coefficientPercentage) {
	return LogiPlaySpringForce(index, offsetPercentage, saturationPercentage, coefficientPercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiStopSpringForce
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiStopSpringForce
(JNIEnv *env, jclass clazz, jint index) {
	return LogiStopSpringForce(index);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiPlayConstantForce
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiPlayConstantForce
(JNIEnv *env, jclass clazz, jint index, jint magnitudePercentage) {
	return LogiPlayConstantForce(index, magnitudePercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiStopConstantForce
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiStopConstantForce
(JNIEnv *env, jclass clazz, jint index) {
	return LogiStopConstantForce(index);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiPlayDamperForce
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiPlayDamperForce
(JNIEnv *env, jclass clazz, jint index, jint coefficientPercentage) {
	return LogiPlayDamperForce(index, coefficientPercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiStopDamperForce
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiStopDamperForce
(JNIEnv *env, jclass clazz, jint index) {
	return LogiStopDamperForce(index);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiPlaySideCollisionForce
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiPlaySideCollisionForce
(JNIEnv *env, jclass clazz, jint index, jint magnitudePercentage) {
	return LogiPlaySideCollisionForce(index, magnitudePercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiPlayFrontalCollisionForce
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiPlayFrontalCollisionForce
(JNIEnv *env, jclass clazz, jint index, jint magnitudePercentage) {
	return LogiPlayFrontalCollisionForce(index, magnitudePercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiPlayDirtRoadEffect
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiPlayDirtRoadEffect
(JNIEnv *env, jclass clazz, jint index, jint magnitudePercentage) {
	return LogiPlayDirtRoadEffect(index, magnitudePercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiStopDirtRoadEffect
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiStopDirtRoadEffect
(JNIEnv *env, jclass clazz, jint index) {
	return LogiStopDirtRoadEffect(index);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiPlayBumpyRoadEffect
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiPlayBumpyRoadEffect
(JNIEnv *env, jclass clazz, jint index, jint magnitudePercentage) {
	return LogiPlayBumpyRoadEffect(index, magnitudePercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiStopBumpyRoadEffect
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiStopBumpyRoadEffect
(JNIEnv *env, jclass clazz, jint index) {
	return LogiStopBumpyRoadEffect(index);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiPlaySlipperyRoadEffect
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiPlaySlipperyRoadEffect
(JNIEnv *env, jclass clazz, jint index, jint magnitudePercentage) {
	return LogiPlaySlipperyRoadEffect(index, magnitudePercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiStopSlipperyRoadEffect
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiStopSlipperyRoadEffect
(JNIEnv *env, jclass clazz, jint index) {
	return LogiStopSlipperyRoadEffect(index);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiPlaySurfaceEffect
* Signature: (IIII)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiPlaySurfaceEffect
(JNIEnv *env, jclass clazz, jint index, jint type, jint magnitudePercentage, jint period) {
	return LogiPlaySurfaceEffect(index, type, magnitudePercentage, period);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiStopSurfaceEffect
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiStopSurfaceEffect
(JNIEnv *env, jclass clazz, jint index) {
	return LogiStopSurfaceEffect(index);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiPlayCarAirborne
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiPlayCarAirborne
(JNIEnv *env, jclass clazz, jint index) {
	return LogiPlayCarAirborne(index);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiStopCarAirborne
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiStopCarAirborne
(JNIEnv *env, jclass clazz, jint index) {
	return LogiStopCarAirborne(index);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiPlaySoftstopForce
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiPlaySoftstopForce
(JNIEnv *env, jclass clazz, jint index, jint usableRangePercentage) {
	return LogiPlaySoftstopForce(index, usableRangePercentage);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiStopSoftstopForce
* Signature: (I)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiStopSoftstopForce
(JNIEnv *env, jclass clazz, jint index) {
	return LogiStopSoftstopForce(index);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiSetPreferredControllerProperties
* Signature: (Lninja/hon95/jlaw/JlawSteeringWheel/LogiControllerPropertiesData;)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiSetPreferredControllerProperties
(JNIEnv *env, jclass clazz, jobject) {
	// TODO
	return NULL;
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiGetCurrentControllerProperties
* Signature: (I)Lninja/hon95/jlaw/JlawSteeringWheel/LogiControllerPropertiesData;
*/
JNIEXPORT jobject JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiGetCurrentControllerProperties
(JNIEnv *env, jclass clazz, jint index) {
	// TODO
	return NULL;
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiGetShifterMode
* Signature: (I)I
*/
JNIEXPORT jint JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiGetShifterMode
(JNIEnv *env, jclass clazz, jint index) {
	return LogiGetShifterMode(index);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiSetOperatingRange
* Signature: (II)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiSetOperatingRange
(JNIEnv *env, jclass clazz, jint index, jint range) {
	return LogiSetOperatingRange(index, range);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiGetOperatingRange
* Signature: (I)I
*/
JNIEXPORT jint JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiGetOperatingRange
(JNIEnv *env, jclass clazz, jint index) {
	int range = 0;
	LogiGetOperatingRange(index, range);
	return range;
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiPlayLeds
* Signature: (IFFF)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiPlayLeds
(JNIEnv *env, jclass clazz, jint index, jfloat currentRpm, jfloat rpmFIrstLedTurnsOn, jfloat rpmRedLine) {
	return LogiPlayLeds(index, currentRpm, rpmFIrstLedTurnsOn, rpmRedLine);
}

/*
* Class:     ninja_hon95_jlaw_JlawSteeringWheel
* Method:    nLogiSteeringShutdown
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_ninja_hon95_jlaw_JlawSteeringWheel_nLogiSteeringShutdown
(JNIEnv *env, jclass clazz) {
	LogiSteeringShutdown();
}
