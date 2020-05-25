package com.example.mymoviemenoir.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.SearchGoogleAPI;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;

public class SearchFragment extends Fragment {

    NetworkConnection networkConnection = null;
    View view = null;
    Button searchButton;
    TextView resultTV;
    EditText searchET;

    public SearchFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_search, container, false);

        resultTV = view.findViewById(R.id.resultTV);
        searchET = view.findViewById(R.id.searchET);
        searchButton = view.findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String keyword = searchET.getText().toString();
                //Anonymous AsyncTask
                new AsyncTask<String, Void, String>(){
                    @Override
                    protected String doInBackground(String... strings) {
                        return SearchGoogleAPI.search(keyword, new String[]{"num"}, new String[]{"3"});
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        resultTV.setText(SearchGoogleAPI.getSnippet(result));
                    }
                }.execute();
            }
        });

        return view;
    }
}
