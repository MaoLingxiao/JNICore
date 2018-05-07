#include "com_bind_Bind.h"

//#define RaiseValueError(msg) \
//    { \
//        PyErr_SetString(PyExc_ValueError, msg); \
//        return NULL; \
//    }

JNIEXPORT void JNICALL Java_com_bind_Bind_c_1coreStart
  (JNIEnv * a, jobject b, jobject c, jobject d)
{
    printf("Hello Jni,this is a project from C.\n");
    return;
}