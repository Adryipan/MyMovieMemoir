package com.example.mymoviemenoir.neworkconnection;

public class GoogleAPI {

    public static native String googleAPIKey();
    static {
        System.loadLibrary("native-lib");
    }

    protected static final String API_KEY = googleAPIKey();

}
