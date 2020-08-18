package com.example.mymoviemenoir.neworkconnection;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchGoogleMapAPI {

    private static final String API_KEY = "";
    private static final String BASE_URL_subfix = "&key=" + API_KEY;
    private static final String BASE_URL =
            "https://maps.googleapis.com/maps/api/geocode/json?address=";

    public static String search(String location){
        String results = "";
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        location = location.replace(" ", "+");
        builder.url(BASE_URL + location + BASE_URL_subfix);
        Request request = builder.build();
        try{
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static LatLng getLatLng(String results){
       LatLng latLng = null;
        try{
            JSONObject jsonObject= new JSONObject(results);
            JSONObject geometry = jsonObject.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry");
            latLng = new LatLng(geometry.getJSONObject("location").getDouble("lat")
                            , geometry.getJSONObject("location").getDouble("lng"));

        } catch (JSONException e) {
            latLng = new LatLng(999,999);
            e.printStackTrace();
        }
        return latLng;
    }

    public static String getSuburb(String results){
        String suburb = "";
        try{
            JSONObject jsonObject = new JSONObject(results);
            JSONArray addressComponents = jsonObject.getJSONArray("result")
                    .getJSONObject(0)
                    .getJSONArray("address_components");
            suburb = addressComponents.getJSONObject(0).getString("long_name");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return suburb;
    }
}
