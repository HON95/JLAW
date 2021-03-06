/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class ninja_hon95_jlaw_JlawGkey */

#ifndef _Included_ninja_hon95_jlaw_JlawGkey
#define _Included_ninja_hon95_jlaw_JlawGkey
#ifdef __cplusplus
extern "C" {
#endif
#undef ninja_hon95_jlaw_JlawGkey_LOGI_MAX_MOUSE_BUTTONS
#define ninja_hon95_jlaw_JlawGkey_LOGI_MAX_MOUSE_BUTTONS 20L
#undef ninja_hon95_jlaw_JlawGkey_LOGI_MAX_GKEYS
#define ninja_hon95_jlaw_JlawGkey_LOGI_MAX_GKEYS 29L
#undef ninja_hon95_jlaw_JlawGkey_LOGI_MAX_M_STATES
#define ninja_hon95_jlaw_JlawGkey_LOGI_MAX_M_STATES 3L
/*
 * Class:     ninja_hon95_jlaw_JlawGkey
 * Method:    nLogiGkeyInit
 * Signature: (Lninja/hon95/jlaw/JlawGkey/JlawGkeyCallback;)Z
 */
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyInit
  (JNIEnv *, jclass, jobject);

/*
 * Class:     ninja_hon95_jlaw_JlawGkey
 * Method:    nLogiGkeyInitWithoutCallback
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyInitWithoutCallback
  (JNIEnv *, jclass);

/*
 * Class:     ninja_hon95_jlaw_JlawGkey
 * Method:    nLogiGkeyIsMouseButtonPressed
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyIsMouseButtonPressed
  (JNIEnv *, jclass, jint);

/*
 * Class:     ninja_hon95_jlaw_JlawGkey
 * Method:    nLogiGkeyGetMouseButtonString
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyGetMouseButtonString
  (JNIEnv *, jclass, jint);

/*
 * Class:     ninja_hon95_jlaw_JlawGkey
 * Method:    nLogiGkeyIsKeyboardGkeyPressed
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyIsKeyboardGkeyPressed
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     ninja_hon95_jlaw_JlawGkey
 * Method:    nLogiGkeyGetKeyboardGkeyString
 * Signature: (II)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyGetKeyboardGkeyString
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     ninja_hon95_jlaw_JlawGkey
 * Method:    nLogiGkeyShutdown
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ninja_hon95_jlaw_JlawGkey_nLogiGkeyShutdown
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
/* Header for class ninja_hon95_jlaw_JlawGkey_JlawGkeyCallback */

#ifndef _Included_ninja_hon95_jlaw_JlawGkey_JlawGkeyCallback
#define _Included_ninja_hon95_jlaw_JlawGkey_JlawGkeyCallback
#ifdef __cplusplus
extern "C" {
#endif
#ifdef __cplusplus
}
#endif
#endif
