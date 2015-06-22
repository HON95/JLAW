#include <jni.h>
#include <string>
#include "JlawUtil.h"

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
	const jchar *javaChars = env->GetStringChars(javaString, NULL);
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
