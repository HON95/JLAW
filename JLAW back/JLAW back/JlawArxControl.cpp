#pragma comment(lib, "LogitechLEDLib.lib")

#include <jni.h>
#include <string>
#include <LogitechGArxControlLib.h>
#include "JlawUtil.h"
#include "JlawArxControl.h"

namespace jlaw_arx_control {
	const char *callbackMethodName = "call";
	const char *callbackMethodSignature = "(IILjava/lang/String;)V";
	char *threadName = "logi-arx-callback-thread";
	JavaVM *storedJvm = NULL;
	jmethodID callbackMethodID = NULL;
	jobject currentCallback = NULL;

	void __cdecl Callback(unsigned __int32 eventType, unsigned __int32 eventValue, wchar_t *eventArg, void *context) {
		JNIEnv *env;
		JavaVMAttachArgs args;
		args.version = JNI_VERSION_1_6; // JNI version
		args.name = threadName; // Thread name
		args.group = NULL; // Thread group
		storedJvm->AttachCurrentThread((void**)&env, &args);

		jmethodID methodId = env->GetMethodID(env->GetObjectClass(currentCallback), callbackMethodName, callbackMethodSignature);
		std::wstring wideEventArg = eventArg;
		jstring javaEventArg = ToJavaStringFromWideString(wideEventArg, env);
		env->CallVoidMethod(currentCallback, callbackMethodID, eventType, eventValue, javaEventArg);
		storedJvm->DetachCurrentThread();
	}
}

using namespace jlaw_arx_control;

/*
* Class:     ninja_hon95_jlaw_JlawArxControl
* Method:    nLogiArxInit
* Signature: (Ljava/lang/String;Ljava/lang/String;Lninja/hon95/jlaw/JlawArxControl/JlawArxControlCallback;)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawArxControl_nLogiArxInit
(JNIEnv *env, jclass clazz, jstring identifier, jstring friendlyName, jobject callback){
	if (storedJvm == NULL)
		env->GetJavaVM(&storedJvm);
	if (callbackMethodID == NULL)
		callbackMethodID = env->GetMethodID(env->GetObjectClass(callback), callbackMethodName, callbackMethodSignature);
	if (currentCallback != NULL)
		env->DeleteGlobalRef(currentCallback);
	currentCallback = env->NewGlobalRef(callback);

	std::wstring wideIdentifier = ToWideStringFromJavaString(identifier, env);
	std::wstring wideFriendlyName = ToWideStringFromJavaString(friendlyName, env);
	logiArxCbContext context;
	context.arxCallBack = (logiArxCb)Callback;
	context.arxContext = NULL;
	return LogiArxInit(&wideIdentifier[0], &wideFriendlyName[0], &context);
}

/*
* Class:     ninja_hon95_jlaw_JlawArxControl
* Method:    nLogiArxAddFileAs
* Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawArxControl_nLogiArxAddFileAs
(JNIEnv *env, jclass clazz, jstring filePath, jstring fileName, jstring mimeType){
	std::wstring wideFilePath = ToWideStringFromJavaString(filePath, env);
	std::wstring wideFileName = ToWideStringFromJavaString(fileName, env);
	std::wstring wideMimeType = ToWideStringFromJavaString(mimeType, env);
	return LogiArxAddFileAs(&wideFilePath[0], &wideFileName[0], &wideMimeType[0]);
}

/*
* Class:     ninja_hon95_jlaw_JlawArxControl
* Method:    nLogiArxAddContentAs
* Signature: ([BLjava/lang/String;Ljava/lang/String;)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawArxControl_nLogiArxAddContentAs
(JNIEnv *env, jclass clazz, jbyteArray content, jstring fileName, jstring mimeType){
	std::wstring wideFileName = ToWideStringFromJavaString(fileName, env);
	std::wstring wideMimeType = ToWideStringFromJavaString(mimeType, env);
	jbyte *byteContent = env->GetByteArrayElements(content, NULL);
	jsize size = env->GetArrayLength(content);
	bool result = LogiArxAddContentAs(byteContent, size, &wideFileName[0], &wideMimeType[0]);
	env->ReleaseByteArrayElements(content, byteContent, JNI_ABORT);
	return result;
}

/*
* Class:     ninja_hon95_jlaw_JlawArxControl
* Method:    nLogiArxAddUTF8StringAs
* Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawArxControl_nLogiArxAddUTF8StringAs
(JNIEnv *env, jclass clazz, jstring stringContent, jstring fileName, jstring mimeType){
	std::wstring wideStringContent = ToWideStringFromJavaString(stringContent, env);
	std::wstring wideFileName = ToWideStringFromJavaString(fileName, env);
	std::wstring wideMimeType = ToWideStringFromJavaString(mimeType, env);
	return LogiArxAddUTF8StringAs(&wideStringContent[0], &wideFileName[0], &wideMimeType[0]);
}

/*
* Class:     ninja_hon95_jlaw_JlawArxControl
* Method:    nLogiArxAddImageFromBitmap
* Signature: ([BIILjava/lang/String;)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawArxControl_nLogiArxAddImageFromBitmap
(JNIEnv *env, jclass clazz, jbyteArray bitmap, jint width, jint height, jstring fileName){
	std::wstring wideFileName = ToWideStringFromJavaString(fileName, env);
	jbyte *byteBitMap = env->GetByteArrayElements(bitmap, NULL);
	bool result = LogiArxAddImageFromBitmap(reinterpret_cast<unsigned char *>(byteBitMap), width, height, &wideFileName[0]);
	env->ReleaseByteArrayElements(bitmap, byteBitMap, JNI_ABORT);
	return result;
}

/*
* Class:     ninja_hon95_jlaw_JlawArxControl
* Method:    nLogiArxSetIndex
* Signature: (Ljava/lang/String;)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawArxControl_nLogiArxSetIndex
(JNIEnv *env, jclass clazz, jstring fileName){
	std::wstring wideFileName = ToWideStringFromJavaString(fileName, env);
	return LogiArxSetIndex(&wideFileName[0]);
}

/*
* Class:     ninja_hon95_jlaw_JlawArxControl
* Method:    nLogiArxSetTagPropertyById
* Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawArxControl_nLogiArxSetTagPropertyById
(JNIEnv *env, jclass clazz, jstring tagId, jstring prop, jstring newValue){
	std::wstring wideTagId = ToWideStringFromJavaString(tagId, env);
	std::wstring wideProp = ToWideStringFromJavaString(prop, env);
	std::wstring wideNewValue = ToWideStringFromJavaString(newValue, env);
	return LogiArxSetTagPropertyById(&wideTagId[0], &wideProp[0], &wideNewValue[0]);
}

/*
* Class:     ninja_hon95_jlaw_JlawArxControl
* Method:    nLogiArxSetTagsPropertyByClass
* Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawArxControl_nLogiArxSetTagsPropertyByClass
(JNIEnv *env, jclass clazz, jstring tagsClass, jstring prop, jstring newValue){
	std::wstring wideTagsClass = ToWideStringFromJavaString(tagsClass, env);
	std::wstring wideProp = ToWideStringFromJavaString(prop, env);
	std::wstring wideNewValue = ToWideStringFromJavaString(newValue, env);
	return LogiArxSetTagsPropertyByClass(&wideTagsClass[0], &wideProp[0], &wideNewValue[0]);
}

/*
* Class:     ninja_hon95_jlaw_JlawArxControl
* Method:    nLogiArxSetTagContentById
* Signature: (Ljava/lang/String;Ljava/lang/String;)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawArxControl_nLogiArxSetTagContentById
(JNIEnv *env, jclass clazz, jstring tagId, jstring newContent){
	std::wstring wideTagId = ToWideStringFromJavaString(tagId, env);
	std::wstring wideNewContent = ToWideStringFromJavaString(newContent, env);
	return LogiArxSetTagContentById(&wideTagId[0], &wideNewContent[0]);
}

/*
* Class:     ninja_hon95_jlaw_JlawArxControl
* Method:    nLogiArxSetTagsContentByClass
* Signature: (Ljava/lang/String;Ljava/lang/String;)Z
*/
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawArxControl_nLogiArxSetTagsContentByClass
(JNIEnv *env, jclass clazz, jstring tagsClass, jstring newContent){
	std::wstring wideTagsClass = ToWideStringFromJavaString(tagsClass, env);
	std::wstring wideNewContent = ToWideStringFromJavaString(newContent, env);
	return LogiArxSetTagsContentByClass(&wideTagsClass[0], &wideNewContent[0]);
}

/*
* Class:     ninja_hon95_jlaw_JlawArxControl
* Method:    nLogiArxGetLastError
* Signature: ()I
*/
JNIEXPORT jint JNICALL Java_ninja_hon95_jlaw_JlawArxControl_nLogiArxGetLastError
(JNIEnv *env, jclass clazz){
	return LogiArxGetLastError();
}

/*
* Class:     ninja_hon95_jlaw_JlawArxControl
* Method:    nLogiArxShutdown
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_ninja_hon95_jlaw_JlawArxControl_nLogiArxShutdown
(JNIEnv *env, jclass clazz){
	LogiArxShutdown();
}
