//
// Created by Adrian on 17/05/2021.
//
#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_example_mymoviemenoir_neworkconnection_NetworkConnection_mmmBaseUrl(
        JNIEnv* env,
        jclass clazz) {
    std::string baseURL = "http://10.0.2.2:15321/MyMovieMemoir-Backend/webresources/";
    return env->NewStringUTF(baseURL.c_str());
}