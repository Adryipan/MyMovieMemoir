package com.example.mymoviemenoir.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;

public class MemoirFragment extends Fragment {

    NetworkConnection networkConnection = null;
    View view = null;

    public MemoirFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_memoir, container, false);

        return view;
    }
}
