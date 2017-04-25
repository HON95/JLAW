#include <jni.h>
#include <string>
#include "JlawUtil.h"

jstring ToJavaStringFromCharString(const char *charString, JNIEnv *env) {
	return env->NewStringUTF(charString);
}

char* ToCharStringFromJavaString(jstring &javaString, JNIEnv *env) {
	const char *chars = env->GetStringUTFChars(javaString, JNI_FALSE);
	jsize length = env->GetStringLength(javaString);
	char *copyChars = new char[length + 1];
	for (int i = 0; i < length; i++)
		copyChars[i] = chars[i];
	copyChars[length] = '\0';
	env->ReleaseStringUTFChars(javaString, chars);
	return copyChars;
}

jstring ToJavaStringFromWideString(std::wstring &wideString, JNIEnv *env) {
	jstring javaString;
	size_t length = wideString.length();
	jchar *javaChars = new jchar[length];
	for (size_t i = 0; i < length; i++)
		javaChars[i] = (wideString)[i];
	javaString = env->NewString(javaChars, static_cast<jchar>(length));
	delete javaChars;
	return javaString;
}

std::wstring ToWideStringFromJavaString(jstring &javaString, JNIEnv *env) {
	std::wstring wideString;
	const jchar *javaChars = env->GetStringChars(javaString, JNI_FALSE);
	jsize length = env->GetStringLength(javaString);
	wchar_t *tmpWideString = new wchar_t[length + 1];
	for (int i = 0; i < length; i++)
		tmpWideString[i] = javaChars[i];
	tmpWideString[length] = '\0';
	wideString = tmpWideString;
	env->ReleaseStringChars(javaString, javaChars);
	delete tmpWideString;
	return wideString;
}
