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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.entity.Cinema;
import com.example.mymoviemenoir.entity.MapCinema;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


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
        mapView.onResume();
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        GetAllCinemaTask getAllCinemaTask = new GetAllCinemaTask();
        getAllCinemaTask.execute();
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


    private class GetAllCinemaTask extends AsyncTask<Void, Void, ArrayList<MapCinema>>{

        @Override
        protected ArrayList<MapCinema> doInBackground(Void... voids) {
            return networkConnection.getAllCinemaWithGeoCode();
        }

        @Override
        protected void onPostExecute(ArrayList<MapCinema> mapCinemas) {
            cinemas = mapCinemas;
            for(MapCinema thisCinema : cinemas) {
                //Pin the cinemas
                if (thisCinema.getGeoCode().latitude != 999 & thisCinema.getGeoCode().longitude != 999) {
                    googleMap.addMarker(new MarkerOptions().position(thisCinema.getGeoCode()).title(thisCinema.getCinemaName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                            //.setPosition(thisCinema.getGeoCode());
                }
            }
        }
    }

}
