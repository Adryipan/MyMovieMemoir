package com.example.mymoviemenoir.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.entity.Cinema;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;
import com.example.mymoviemenoir.neworkconnection.SearchGoogleMapAPI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private NetworkConnection networkConnection = null;
    private View view = null;
    private GoogleMap googleMap;
    private MapView mapView;
    private LatLng currentLocation;
    private LocationManager locationManager;
    private ArrayList<Cinema> cinemas;

    public MapsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_maps, container, false);
        //The map
        mapView = view.findViewById(R.id.mapView);

        //Get all cinema Latlng
        //Get all cinema
        cinemas = new ArrayList<Cinema>();
        networkConnection = new NetworkConnection();
        GetAllCinemaTask getAllCinemaTask = new GetAllCinemaTask();
        getAllCinemaTask.execute();




        //The location manager
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            requestLocationPermission();
        }


        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        //Pin my location
        if(currentLocation != null) {
            this.googleMap.addMarker(new MarkerOptions().position(currentLocation).title("My location"));
            float zoomLevel = 12f;
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoomLevel));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
            new AlertDialog.Builder(getActivity())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to show cinema location nearby.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
            .create().show();
        } else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

//    private void getGeoCode(){
//        GetGeocodeTask getGeocodeTask = new GetGeocodeTask();
//        for(Cinema thisCinema : cinemas){
//            getGeocodeTask.execute(thisCinema.getSuburb());
//        }
//
//    }

    private class GetAllCinemaTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            return networkConnection.getAllCinema();
        }

        @Override
        protected void onPostExecute(String result) {
            //Add to cinemas ArrayList
            try {
                JSONArray jsonArray = new JSONArray(result);
                int numberOfItems = jsonArray.length();
                if(numberOfItems > 0){
                    for(int i = 0; i < numberOfItems; i++) {
                        JSONObject thisJSONObject = jsonArray.getJSONObject(i);
                        cinemas.add(new Cinema(thisJSONObject.getString("cinemaId"),
                                               thisJSONObject.getString("cinemaName"),
                                               thisJSONObject.getString("suburb")));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            getGeoCode();
        }
    }

//    private class GetGeocodeTask extends AsyncTask<String, Void, String>{
//
//        @Override
//        protected String doInBackground(String... strings) {
//            return SearchGoogleMapAPI.getGeocode(strings[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            //Set the geocode back to corresponding cinema
//            LatLng thisCinemaLocation = SearchGoogleMapAPI.getLatLng(result);
//            for(Cinema thisCinema: cinemas){
//                if(thisCinema.getSuburb().toUpperCase().equals(SearchGoogleMapAPI.getSuburb(result))){
//                    thisCinema.setGeocode(thisCinemaLocation);
//                }
//            }
//
//            //Pin cinema location
//            if(cinemas.size() > 0){
//                for(Cinema thisCinema : cinemas) {
//                    googleMap.addMarker(new MarkerOptions()
//                            .position(thisCinema.getGeocode())
//                            .icon(BitmapDescriptorFactory
//                                    .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
//                            .title(thisCinema.getCinemaName()));
//                }
//            }
//        }
//    }
}
