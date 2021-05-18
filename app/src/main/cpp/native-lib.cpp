//
// Created by Adrian on 17/05/2021.
//
#include <jni.h>
#include <string>

/**
 * My Movie Memoir backend API url
 */
extern "C"
jstring
Java_com_example_mymoviemenoir_neworkconnection_NetworkConnection_mmmBaseUrl(
        JNIEnv* env,
        jclass clazz) {
    std::string baseURL = "http://10.0.2.2:15321/MyMovieMemoir-Backend/webresources/";
    return env->NewStringUTF(baseURL.c_str());
}

/**
 * OMDB API url and key
 */
extern "C"
jstring
Java_com_example_mymoviemenoir_neworkconnection_SearchOMDbAPI_omdbBaseUrl(
        JNIEnv* env,
        jclass){
    std::string baseURL = "http://www.omdbapi.com/?apikey=";
    std::string apiKey = "4526d397";
    return env->NewStringUTF((baseURL + apiKey + "&").c_str());
}


/**
 * Google API key
 */
extern "C"
jstring
Java_com_example_mymoviemenoir_neworkconnection_GoogleAPI_googleAPIKey(
        JNIEnv *env,
        jclass clazz) {
    std::string apiKey="";
    return env->NewStringUTF(apiKey.c_str());
}