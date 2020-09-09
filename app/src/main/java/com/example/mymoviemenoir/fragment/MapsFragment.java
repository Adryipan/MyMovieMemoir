package com.example.mymoviemenoir.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.entity.MapCinema;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;
import com.example.mymoviemenoir.neworkconnection.SearchGoogleMapAPI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private NetworkConnection networkConnection = null;
    private View view = null;
    private GoogleMap googleMap;
    private MapView mapView;
    private LatLng currentLocation;
    private LocationManager locationManager;
    private ArrayList<MapCinema> cinemas;

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
        cinemas = new ArrayList<MapCinema>();
        networkConnection = new NetworkConnection();


        //The location manager
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            requestLocationPermission();
        }


        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        GetAllCinemaTask getAllCinemaTask = new GetAllCinemaTask();
        getAllCinemaTask.execute();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        GetUserInfoTask getUserInfoTask = new GetUserInfoTask();
        getUserInfoTask.execute(sharedPreferences.getString("USERID", "0"));

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

    private class GetUserInfoTask extends AsyncTask<String, Void, LatLng>{

        @Override
        protected LatLng doInBackground(String... strings) {
            LatLng userLocation = null;
            String userInfo = networkConnection.getUserByID(strings[0]);
            try {
                JSONObject user = new JSONObject(userInfo);
                String address = user.getString("streetAddress") + " " + user.getString("stateCode") + " " + user.getString("postcode");
                address = SearchGoogleMapAPI.search(address);
                userLocation = SearchGoogleMapAPI.getLatLng(address);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return userLocation;
        }

        @Override
        protected void onPostExecute(LatLng userLocation) {
            if(userLocation.longitude != 999 && userLocation.latitude!= 999){
                googleMap.addMarker(new MarkerOptions().position(userLocation).title("Home"));
                float zoomLevel = 11f;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, zoomLevel));
            }else{
                //Pin my current location
                if(currentLocation != null) {
                    googleMap.addMarker(new MarkerOptions().position(currentLocation).title("My location"));
                    float zoomLevel = 11f;
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoomLevel));
                }
            }
        }
    }


    private class GetAllCinemaTask extends AsyncTask<Void, Void, ArrayList<MapCinema>>{

        @Override
        protected ArrayList<MapCinema> doInBackground(Void... voids) {
            String cinemaResult = networkConnection.getAllCinema();
            ArrayList<MapCinema> cinemaResultList = new ArrayList<>();
            try{
                // Convert the cinema data from the database to geocode with the google api
                JSONArray jsonArray = new JSONArray(cinemaResult);
                int numberOfItems = jsonArray.length();
                if(numberOfItems > 0){
                    for(int i = 0; i < numberOfItems; i++) {
                        JSONObject thisCinema = jsonArray.getJSONObject(i);
                        //Call SearchGoogleMap for geocode
                        String googleMapResult = SearchGoogleMapAPI.search(thisCinema.getString("suburb"));
                        LatLng geocode = SearchGoogleMapAPI.getLatLng(googleMapResult);

                        //Add this object to the list
                        cinemaResultList.add(new MapCinema(thisCinema.getInt("cinemaId"),
                                thisCinema.getString("cinemaName"), thisCinema.getString("suburb"),
                                geocode));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return cinemaResultList;
        }

        @Override
        protected void onPostExecute(ArrayList<MapCinema> mapCinemas) {
            mapView.onResume();
            cinemas = mapCinemas;
            for(MapCinema thisCinema : cinemas) {
                //Pin the cinemas
                if (thisCinema.getGeoCode().latitude != 999 & thisCinema.getGeoCode().longitude != 999) {
                    googleMap.addMarker(new MarkerOptions().position(thisCinema.getGeoCode()).title(thisCinema.getCinemaName() + " " + thisCinema.getSuburb())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                }
            }
        }
    }

}
